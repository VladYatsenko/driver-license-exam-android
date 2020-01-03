package com.android.testdai.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "question")
class QuestionEntity constructor(

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "_id")
        var id: Int?,

        @NonNull
        @ColumnInfo(name = "name_question")
        var text: String?,
        @ColumnInfo(name = "source_image")
        var image: String?,
//        @ColumnInfo(name = "bitmap_image")
//        var bitmap: String?,
        @ColumnInfo(name = "fk_topic")
        var topicId: Int?,

//        @ColumnInfo(name = "fk_topic")
//        var topicId: Int?,

//        @Relation(parentColumn = "_id", entityColumn = "fk_question", entity = AnswerEntity::class)
//        var answers: List<AnswerEntity>,
        @ColumnInfo(name = "isSelected")
        var isSelected: Boolean? = false,
        @ColumnInfo(name = "isAnswered")
        var isAnswered: Boolean? = false
)