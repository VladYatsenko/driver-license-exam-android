package com.android.testdai.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.testdai.managers.ConnectionManager
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.model.QuestionWithAnswers
import com.android.testdai.utils.db.DataRepository
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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
    var questions = MutableLiveData<List<QuestionWithAnswers>>()

    private val TIMER_VALUE = 1200L
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {

        dataRepository.loadQuestions(sharedPreferencesManager.categories?.filter { it.isSelected == true })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<QuestionWithAnswers>> {
                    override fun onSuccess(response: List<QuestionWithAnswers>) {
                        inProgress.value = false
                        questions.value = response
                        if (sharedPreferencesManager.isTimeLimit)
                            timer()
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