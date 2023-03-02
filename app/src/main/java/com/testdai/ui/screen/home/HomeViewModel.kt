package com.testdai.ui.screen.home

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel constructor(application: Application) : BaseAndroidViewModel(application) {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return HomeViewModel(application) as T
            }
        }
    }

    private val examPreferences by lazy { ExamPreferences(context) }

    private val _categories = MutableLiveData<String>()
    val categories: LiveData<String> = _categories

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = examPreferences.categories
            _categories.postValue(categories.joinToString(", "))
        }

    }

    private fun updateCategories() {

    }

}