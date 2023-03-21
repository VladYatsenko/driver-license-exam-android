package com.testdai.ui.screen.settings

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel private constructor(application: Application) : BaseAndroidViewModel(application) {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsViewModel(application) as T
            }
        }
    }

    private val examPreferences by lazy { ExamPreferences(context) }

    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }


}