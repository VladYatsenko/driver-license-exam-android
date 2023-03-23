package com.testdai.model

data class TopicModel constructor(
    val id: Int,
    val name: String
)

data class QuestionModel constructor(
    val id: Int,
    val text: String,
    val image: String?,
    val topic: TopicModel,
    val answers: List<AnswerModel>,
    val selected: Boolean = false
) {

    val hasImage: Boolean
        get() = !image.isNullOrBlank()

    val answered: Boolean
        get() = answers.any { it.answered }

    val correct: Boolean
        get() = answers.any { it.correct && it.answered }

    val incorrect: Boolean
        get() = answers.any { !it.correct && it.answered }

}

data class AnswerModel constructor(
    val id: Int,
    val text: String,
    val correct: Boolean,
    val answered: Boolean = false
)