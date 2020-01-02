package com.android.testdai.di.modules

import android.app.Application
import android.content.Context
import com.android.testdai.application.TestDaiApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TestDaiApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

}