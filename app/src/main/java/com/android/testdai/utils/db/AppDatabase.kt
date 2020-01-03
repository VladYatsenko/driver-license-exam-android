package com.android.testdai.utils.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.testdai.utils.db.QuestionDao
import com.android.testdai.model.AnswerEntity
import com.android.testdai.model.QuestionEntity
import com.android.testdai.utils.db.DateConverter


@Database(entities = [QuestionEntity::class, AnswerEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}