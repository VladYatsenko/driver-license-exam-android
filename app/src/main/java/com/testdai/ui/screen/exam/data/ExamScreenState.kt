package com.testdai.ui.screen.exam.data

import com.testdai.model.QuestionModel
import com.testdai.model.TopicModel

data class ExamScreenState(
    val toolbar: Toolbar = Toolbar.Exam,
    val questions: List<QuestionModel> = emptyList(),
    val question: QuestionModel = noopQuestion,
    val isActiveExam: Boolean = true
)

sealed class Toolbar {
    object Noop: Toolbar()
    object Exam: Toolbar()
    object Training: Toolbar()
    data class Topic(val name: String): Toolbar()
}

private val noopQuestion = QuestionModel(
    id = -1,
    text = "",
    image = null,
    topic = TopicModel(id = -1, name = ""),
    answers = emptyList(),
    selected = false
)