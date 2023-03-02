package com.testdai.model

class TopicModel constructor(
    val id: Int,
    val name: String
)

class QuestionModel constructor(
    val id: Int,
    val text: String,
    val image: String?,
    val topicId: Int,
    val answers: List<AnswerModel>,
    val selected: Boolean? = false
) {

    val answered: Boolean
        get() = answers.any { it.answered }

    val correct: Boolean
        get() = answers.any { it.correct && it.answered }

}

class AnswerModel constructor(
    val id: Int,
    val text: String,
    val correct: Boolean,
    val selected: Boolean = false,
    val answered: Boolean = false
)