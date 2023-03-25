package com.testdai.core.database

import androidx.room.*

@Entity(tableName = "question")
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

@Entity(tableName = "answer")
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

@Entity(tableName = "statistic")
class QuestionStatisticEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "fk_question")
    var questionId: Int,
    @ColumnInfo(name = "display_count")
    var displayCount: Int,
    @ColumnInfo(name = "right_count")
    var rightCount: Int
)


class QuestionWithAnswers constructor(
    @Embedded
    var question: QuestionEntity?,
    @Relation(parentColumn = "_id", entityColumn = "fk_question")
    var answers: List<AnswerEntity>?
)