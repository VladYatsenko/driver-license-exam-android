package com.testdai.core.database

import androidx.room.Room
import android.app.Application
import com.testdai.core.database.dao.QuestionDao

class RoomModule(val application: Application) {

    private val DATABASE_NAME =  "dai_v2"
    private val demoDatabase =
            Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("databases/${DATABASE_NAME}.db")
                    .build()

    fun providesRoomDatabase(): AppDatabase {
        return demoDatabase
    }

    fun providesProductDao(demoDatabase: AppDatabase): QuestionDao {
        return demoDatabase.questionDao()
    }

    fun productRepository(productDao: QuestionDao): DataRepository {
        return DataRepository(productDao)
    }
}