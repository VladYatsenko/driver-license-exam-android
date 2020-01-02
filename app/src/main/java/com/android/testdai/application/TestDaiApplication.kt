package com.android.testdai.application

import android.app.Application
import com.android.testdai.di.components.ApplicationComponent
import com.android.testdai.di.components.DaggerApplicationComponent
import com.android.testdai.di.modules.ApplicationModule

class TestDaiApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}