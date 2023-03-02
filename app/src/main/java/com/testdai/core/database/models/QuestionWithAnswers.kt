package com.testdai.core.database.models

import androidx.room.Embedded
import androidx.room.Relation


class QuestionWithAnswers constructor(

    @Embedded
    var questionEntity: QuestionEntity? = null,

    @Relation(parentColumn = "_id", entityColumn = "fk_question")
    var answers: List<AnswerEntity>? = null
)