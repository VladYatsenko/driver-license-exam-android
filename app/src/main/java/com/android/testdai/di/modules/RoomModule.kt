package com.android.testdai.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import android.app.Application
import com.android.testdai.model.database.dao.QuestionDao
import com.android.testdai.model.database.AppDatabase
import com.android.testdai.model.database.DataRepository


@Module
class RoomModule(val application: Application) {

    private val DATABASE_NAME =  "dai_v2"
    private var demoDatabase =
            Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("databases/${DATABASE_NAME}.db")
                    .build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: AppDatabase): QuestionDao {
        return demoDatabase.questionDao()
    }

    @Singleton
    @Provides
    fun productRepository(productDao: QuestionDao): DataRepository {
        return DataRepository(productDao)
    }
}