package com.testdai.core.database

import android.content.Context
import androidx.room.Room

class ExamDatabaseProvider private constructor() {

    companion object {

        @Volatile private var instance: ExamDatabase? = null

        fun getInstance(context: Context): ExamDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private val DATABASE_NAME =  "dai_visual"

        private fun buildDatabase(context: Context): ExamDatabase {
            return Room.databaseBuilder(context, ExamDatabase::class.java, DATABASE_NAME)
                .createFromAsset("databases/${DATABASE_NAME}.db")
                .build()
        }
    }

}