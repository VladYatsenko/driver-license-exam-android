package com.android.testdai.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.testdai.model.database.dao.QuestionDao
import com.android.testdai.model.enities.AnswerEntity
import com.android.testdai.model.enities.QuestionEntity


@Database(entities = [QuestionEntity::class, AnswerEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}