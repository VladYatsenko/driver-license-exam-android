package com.testdai.ui.screen.exam

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.core.repository.ExamRepository
import com.testdai.model.AnswerModel
import com.testdai.model.QuestionModel
import com.testdai.model.State
import com.testdai.model.TopicModel
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.*

class ExamViewModel private constructor(application: Application): BaseAndroidViewModel(application) {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ExamViewModel(application) as T
            }
        }
    }

    private val examRepository by lazy { ExamRepository(application) }
    private val examPreferences by lazy { ExamPreferences(context) }
    private val mapper by lazy { ExamMapper() }

    private val doubleClick by lazy { examPreferences.doubleClick }
    private val errorLimit by lazy { examPreferences.errorLimit }

    private var examScreenState = ExamScreenState()

    private val _exam = MutableLiveData<State<ExamScreenState>>()
    val exam: LiveData<State<ExamScreenState>> = _exam

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = examRepository.loadExamQuestions()
            val questions = mapper.mapQuestions(data)
            examScreenState = ExamScreenState(
                questions = questions,
                question = questions.first(),
                true
            )
            internalOnQuestionClick(questions.first())
        }
    }

    fun onQuestionClick(question: QuestionModel) {
        if (examScreenState.question.id == question.id)
            return

        internalOnQuestionClick(question)
    }

    private fun internalOnQuestionClick(question: QuestionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val remappedList = examScreenState.questions.map {
                if (it.selected || it.id == question.id)
                    it.copy(selected = it.id == question.id)
                else it
            }
            examScreenState = examScreenState.copy(questions = remappedList, question = question.copy(selected = true))
            _exam.postValue(State.Success(examScreenState))
        }
    }

    fun onAnswerClick(answer: AnswerModel) {
        val question = examScreenState.question
        if (!examScreenState.isActiveExam || question.answered)
            return
        viewModelScope.launch(Dispatchers.IO) {
            val remappedAnswers = question.answers.map {
                if (doubleClick) {
                    if (it.selected) {
                        if (it.id == answer.id) {
                            it.copy(selected = false, answered = true)
                        } else {
                            it.copy(selected = false)
                        }
                    } else {
                        if (it.id == answer.id) {
                            it.copy(selected = true)
                        } else {
                            it
                        }
                    }
                } else {
                    answer.copy(answered = it.id == answer.id)
                }
            }

            val remappedQuestion = question.copy(answers = remappedAnswers)
            val remappedQuestions = examScreenState.questions.map {
                if (it.id == remappedQuestion.id) remappedQuestion else it
            }
            val isFinished = remappedQuestions.all { it.answered }
                    || (errorLimit && remappedQuestions.filter { it.incorrect }.size >= 2)
            examScreenState = examScreenState.copy(
                questions = remappedQuestions,
                question = remappedQuestion,
                isActiveExam = !isFinished
            )
            if (remappedQuestion.answered && !isFinished) {
                findNextQuestion()?.let {
                    onQuestionClick(it)
                }
            } else {
                _exam.postValue(State.Success(examScreenState))
            }
        }
    }

    private fun findNextQuestion(): QuestionModel? {
        val question = examScreenState.question
        val questions = examScreenState.questions

        var position = questions.indexOfFirst { it.id == question.id }.takeIf { it >= 0 } ?: return null
        for (i in 0 until questions.size - 1) {

            if (position >= questions.size - 1)
                position = 0
            else
                position++

            val question = questions.getOrNull(position)
            if (question?.answered != true) {
                return question
            }
        }

        return null
    }

}