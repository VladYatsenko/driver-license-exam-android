package com.android.testdai.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.testdai.managers.ConnectionManager
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.model.QuestionWithAnswers
import com.android.testdai.utils.db.DataRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestViewModel @Inject constructor(
        var dataRepository: DataRepository,
        var sharedPreferencesManager: SharedPreferencesManager,
        var connectionDetector: ConnectionManager
) : ViewModel() {

    var inProgress = MutableLiveData<Boolean>()
    var timerValue = MutableLiveData<Long>()
    var questions = MutableLiveData<ArrayList<QuestionWithAnswers>>()

    private val TIMER_VALUE = 1200L
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {

        val list: ArrayList<QuestionWithAnswers> = ArrayList()
        // передаю в Observable список topics id для запроса в бд
        Observable.just(dataRepository.getListOfTopics(sharedPreferencesManager.categories?.filter { it.isSelected == true }))
                .flatMap {
                    return@flatMap Observable.fromIterable(it)
                }
                .flatMap {
                    //для каждого topic id запрашиваю список вопросов
                    return@flatMap dataRepository.loadQuestion(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
//                .concatMap{
//                    return@concatMap dataRepository.loadQuestion(it)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                }
//                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<QuestionWithAnswers>> {
                    override fun onComplete() {
                        //TODO dont work
                        questions.value = list
                        inProgress.value = false
                        if (sharedPreferencesManager.isTimeLimit)
                            timer()
                    }

                    override fun onNext(questionsList: List<QuestionWithAnswers>) {
                        //добавляю случайный вопрос в список
                        list.add(questionsList.random())
                        if (list.size >= 20){
                            this.onComplete()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        inProgress.value = false
                    }
                })


    }

    private fun timer() {
        compositeDisposable.add(
                Observable.interval(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { timerValue.value = it*1000 }
                        .takeUntil { aLong -> aLong == TIMER_VALUE }
                        .doOnComplete {}
                        .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}