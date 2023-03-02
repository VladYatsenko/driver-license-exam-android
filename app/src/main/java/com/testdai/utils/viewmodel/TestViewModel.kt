package com.testdai.utils.viewmodel

import androidx.lifecycle.ViewModel
import com.testdai.core.database.DataRepository


class TestViewModel constructor(
    var dataRepository: DataRepository
    ) : ViewModel() {

//    var inProgress = MutableLiveData<Boolean>()
//    var timerValue = MutableLiveData<Long>()
//    var questions = MutableLiveData<ArrayList<QuestionWithAnswers>>()
//    var test = MutableLiveData<TestEntity>(TestEntity())
//
//    private val TIMER_VALUE = 1200L
//    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
//    private var timerDisposable: CompositeDisposable = CompositeDisposable()
//
//    init {
//        requestToDatabase()
//    }
//
//    private fun requestToDatabase() {
//
//        inProgress.value = true
//        compositeDisposable.add(
//                Single.just(dataRepository.getListOfTopics(sharedPreferencesManager.categories?.filter { it.isSelected == true }))
//                        .flatMap {
//                            val list: ArrayList<QuestionWithAnswers> = ArrayList()
//                            return@flatMap Observable.fromIterable(it)
//                                    .flatMapCompletable { id ->
//                                        return@flatMapCompletable dataRepository.loadQuestion(id)
//                                                .doOnSuccess {
//                                                    list.add(it.random())
//                                                }
//                                                .ignoreElement()
//                                    }
//                                    .andThen(Single.just(list))
//                        }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeBy(
//                                onSuccess = {
//                                    questions.value = it
//                                    inProgress.value = false
//                                    if (sharedPreferencesManager.isTimeLimit)
//                                        timer()
//                                },
//                                onError = {
//                                    inProgress.value = false
//                                }
//                        )
//        )
//
//    }
//
//    fun endTest(isNeedToShowResultDialog: Boolean = true) {
//        test.value = TestEntity(false, isNeedToShowResultDialog)
//        timerDisposable.dispose()
//    }
//
//    private fun timer() {
//        timerDisposable.add(
//                Observable.interval(1000, TimeUnit.MILLISECONDS)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnNext { timerValue.value = (TIMER_VALUE - it) * 1000 }
//                        .takeUntil { aLong -> aLong == TIMER_VALUE }
//                        .doOnComplete {
//                            if (test.value?.isTestAvailable == true) {
//                                endTest(test.value?.isTestAvailable != true)
//                            }
//                        }.subscribe()
//        )
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        if (!timerDisposable.isDisposed) {
//            timerDisposable.dispose()
//        }
//        if (!compositeDisposable.isDisposed) {
//            compositeDisposable.dispose()
//        }
//    }

}