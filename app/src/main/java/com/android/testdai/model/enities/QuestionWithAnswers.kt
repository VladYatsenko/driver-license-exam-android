package com.android.testdai.model.enities

import androidx.room.Embedded
import androidx.room.Relation
import com.android.testdai.model.enities.AnswerEntity
import com.android.testdai.model.enities.QuestionEntity


class QuestionWithAnswers constructor(

        @Embedded
    var questionEntity: QuestionEntity? = null,

        @Relation(parentColumn = "_id", entityColumn = "fk_question")
    var answers: List<AnswerEntity>? = null
)