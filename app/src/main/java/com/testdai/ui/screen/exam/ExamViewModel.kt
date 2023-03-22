package com.testdai.ui.screen.exam

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.core.repository.ExamRepository
import com.testdai.core.repository.TopicRepository
import com.testdai.model.AnswerModel
import com.testdai.model.ExamMode
import com.testdai.model.QuestionModel
import com.testdai.model.State
import com.testdai.ui.bottom.result.ResultScreenState
import com.testdai.ui.screen.exam.data.ExamScreenState
import com.testdai.ui.screen.exam.data.Toolbar
import com.testdai.utils.CountdownTimer
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.*
import java.util.*

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

    private val topicRepository = TopicRepository(application)
    private val examRepository = ExamRepository(application)
    private val examPreferences = ExamPreferences(application)

    private val mode: ExamMode = ExamMode.valueOf(examPreferences.examMode)

    private val _toolbar = MutableLiveData<Toolbar>()
    val toolbar: LiveData<Toolbar> = _toolbar

    private var examScreenState = ExamScreenState()

    private val _exam = MutableLiveData<State<ExamScreenState>>(State.Loading())
    val exam: LiveData<State<ExamScreenState>> = _exam

    private val _examResult = MutableLiveData(ResultScreenState())
    val examResult: LiveData<ResultScreenState> = _examResult

    private val totalTime = if (mode.timeLimit) 20 * 60 * 1000L else -1
    val initialTime = totalTime.formatDuration()

    private val _timer = MutableLiveData(initialTime)
    val timer: LiveData<String> = _timer

    private val countdownTimer by lazy {
        CountdownTimer(totalTime) {
            _timer.value = it.formatDuration()
            if (it == 0L && examScreenState.isActiveExam) {
                examScreenState = examScreenState.copy(isActiveExam = false)
                _exam.value = State.Success(examScreenState)
                showExamResult()
            }
        }
    }

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            _toolbar.postValue(when (mode) {
                ExamMode.Exam -> Toolbar.Exam
                ExamMode.Training -> Toolbar.Training
                ExamMode.Topic -> {
                    val topic = topicRepository.loadTopicById(examPreferences.topicId)
                    Toolbar.Topic(topic.name)
                }
            })

            val questions = if (mode is ExamMode.Topic) {
                examRepository.loadTopicQuestions(examPreferences.topicId)
            } else {
                examRepository.loadExamQuestions()
            }

            if (questions.isEmpty()) {
                _exam.postValue(State.Error())
                return@launch
            }

            val question = questions.first()
            examScreenState = examScreenState.copy(
                questions = questions,
                question = question,
                isActiveExam = true
            )
            internalOnQuestionClick(question)
            withContext(Dispatchers.Main) {
                if (mode.timeLimit)
                    countdownTimer.start()
            }
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
                /*if (doubleClick) {
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
                }*/

                answer.copy(answered = it.id == answer.id)
            }

            val remappedQuestion = question.copy(answers = remappedAnswers)
            val remappedQuestions = examScreenState.questions.map {
                if (it.id == remappedQuestion.id) remappedQuestion else it
            }
            val isFinished = remappedQuestions.all { it.answered }
                    || (mode.errorLimit && remappedQuestions.filter { it.incorrect }.size >= 2)
            examScreenState = examScreenState.copy(
                questions = remappedQuestions,
                question = remappedQuestion,
                isActiveExam = !isFinished
            )
            if (isFinished) {
                withContext(Dispatchers.Main) {
                    if (mode.timeLimit)
                        countdownTimer.stop()
                }
                showExamResult()
            }
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

    private fun showExamResult() {
        val questions = examScreenState.questions
        val rightAnsweredCount = questions.count { it.correct }
        val totalCount = questions.size
        val isExamPassed = totalCount - rightAnsweredCount <= 2 || !examPreferences.errorLimit
        val result = ResultScreenState(
            rightAnsweredCount = rightAnsweredCount,
            totalCount = totalCount,
            isExamPassed = isExamPassed
        )
        _examResult.postValue(result)
    }

    private fun Long.formatDuration(): String {
        if (this == -1L) return ""

        var seconds = this / 1000
        val minutes = seconds / 60
        if (minutes >= 1) {
            seconds -= 60 * minutes
        }
        return when {
            minutes >= 1 -> String.format(Locale.US, "%02d:%02d", minutes, seconds)
            else -> String.format(Locale.US, "00:%02d", seconds)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (mode.timeLimit)
            countdownTimer.stop()
    }
}