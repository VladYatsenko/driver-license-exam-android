package com.testdai.core.database

import androidx.room.*
import com.testdai.core.database.dao.QuestionDao
import com.testdai.core.database.dao.TopicDao

@Database(
    entities = [
        QuestionEntity::class,
        AnswerEntity::class,
        TopicEntity::class,
        QuestionStatisticEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ExamDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun topicDao(): TopicDao

}

class DateConverter {

    @TypeConverter
    fun toBoolean(value: Int?) = value == 1

}