package com.android.testdai.di.components

import android.content.Context
import com.android.testdai.di.modules.ApplicationModule
import com.android.testdai.di.modules.ViewModelModule
import com.android.testdai.managers.ConnectionManager
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {

    // objects from modules, that will be available for sub components:
    fun context(): Context
    fun sharedPreferences(): SharedPreferencesManager
    fun connectionManager(): ConnectionManager
    fun viewModelFactory(): ViewModelFactory

}