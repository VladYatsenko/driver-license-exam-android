package com.testdai.ui.screen.exam.data

import com.testdai.core.database.AnswerEntity
import com.testdai.core.database.QuestionWithAnswers
import com.testdai.model.AnswerModel
import com.testdai.model.QuestionModel
import com.testdai.model.TopicModel

class ExamMapper {

    fun mapQuestions(list: List<QuestionWithAnswers?>): List<QuestionModel> {
        return list.mapNotNull(::mapQuestion)
    }

    private fun mapQuestion(questionWithAnswers: QuestionWithAnswers?): QuestionModel? {
        val question = questionWithAnswers?.question ?: return null
        val answers = questionWithAnswers.answers?.mapNotNull(::mapAnswer)?.takeIf { it.isNotEmpty() } ?: return null
        return QuestionModel(
            id = question.id,
            text = question.text,
            image = question.image,
            topic = TopicModel(question.topicId ?: -1, ""),
            answers = answers
        )
    }

    private fun mapAnswer(answerEntity: AnswerEntity?): AnswerModel? {
        val answer = answerEntity ?: return null
        return AnswerModel(
            id = answer.id,
            text = answer.text,
            correct = answer.correct
        )
    }
}