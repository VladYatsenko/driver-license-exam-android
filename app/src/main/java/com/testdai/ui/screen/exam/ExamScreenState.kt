package com.testdai.ui.screen.exam

import com.testdai.model.QuestionModel
import com.testdai.model.TopicModel

data class ExamScreenState(
    val questions: List<QuestionModel> = emptyList(),
    val question: QuestionModel = noopQuestion,
    val isActiveExam: Boolean = true
)

private val noopQuestion = QuestionModel(
    id = -1,
    text = "",
    image = null,
    topic = TopicModel(id = -1, name = ""),
    answers = emptyList(),
    selected = false
)