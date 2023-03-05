package com.testdai.core.database

import androidx.room.*

@Entity(
    tableName = "question"
)
class QuestionEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Int,

    @ColumnInfo(name = "name_question")
    var text: String,

    @ColumnInfo(name = "source_image")
    var image: String? = null,

    @ColumnInfo(name = "fk_topic")
    var topicId: Int?
)


@Entity(
    tableName = "answer"
)
class AnswerEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Int,

    @ColumnInfo(name = "name_answer")
    var text: String,

    @ColumnInfo(name = "correct")
    var correct: Boolean,

    @ColumnInfo(name = "fk_question")
    var questionId: Int
)

@Entity(tableName = "topic")
class TopicEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Int,

    @ColumnInfo(name = "name_topic")
    var name: String
)


class QuestionWithAnswers constructor(
    @Embedded
    var question: QuestionEntity?,

    @Relation(parentColumn = "_id", entityColumn = "fk_question")
    var answers: List<AnswerEntity>?
)