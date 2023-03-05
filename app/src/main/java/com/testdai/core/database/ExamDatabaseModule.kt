package com.testdai.core.database

import android.app.Application
import android.content.Context
import androidx.room.Room

class ExamDatabaseModule private constructor(application: Application) {

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ExamDatabase? = null

        fun getInstance(context: Context): ExamDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private val DATABASE_NAME =  "dai_visual"

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): ExamDatabase {
            return Room.databaseBuilder(context, ExamDatabase::class.java, DATABASE_NAME)
                .createFromAsset("databases/${DATABASE_NAME}.db")
                .build()
        }
    }

}