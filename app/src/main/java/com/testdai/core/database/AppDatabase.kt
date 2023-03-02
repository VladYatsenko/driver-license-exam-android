package com.testdai.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testdai.core.database.dao.QuestionDao
import com.testdai.core.database.models.AnswerEntity
import com.testdai.core.database.models.QuestionEntity


@Database(entities = [QuestionEntity::class, AnswerEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}