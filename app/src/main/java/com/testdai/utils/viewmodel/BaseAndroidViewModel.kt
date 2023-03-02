package com.testdai.utils.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

abstract class BaseAndroidViewModel constructor(private val application: Application) : AndroidViewModel(application)  {

    protected val context: Context
        get() = application.baseContext

}