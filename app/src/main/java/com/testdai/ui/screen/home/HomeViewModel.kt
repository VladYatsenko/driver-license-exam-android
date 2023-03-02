package com.testdai.ui.screen.home

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.model.Category
import com.testdai.ui.screen.category.CategoryItem
import com.testdai.ui.screen.category.CategorySelectorState
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

    private val _categorySelector = MutableLiveData<CategorySelectorState>()
    val categorySelector: LiveData<CategorySelectorState> = _categorySelector

    private var categorySelectorState = CategorySelectorState()
        set(value) {
            field = value
            _categorySelector.postValue(value)
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = examPreferences.categories
            _categories.postValue(categories.joinToString(", "))

            val categorySelectorList = mutableListOf<CategoryItem>().apply {
                //first line
                add(CategoryItem.create(Category.A1, categories))
                add(CategoryItem.create(Category.B1, categories))
                add(CategoryItem.create(Category.C1, categories))
                add(CategoryItem.create(Category.D1, categories))
                add(CategoryItem.Empty)

                //second line
                add(CategoryItem.Empty)
                add(CategoryItem.Empty)
                add(CategoryItem.create(Category.C1E, categories))
                add(CategoryItem.create(Category.D1E, categories))
                add(CategoryItem.Empty)

                //third line
                add(CategoryItem.create(Category.A, categories))
                add(CategoryItem.create(Category.B, categories))
                add(CategoryItem.create(Category.C, categories))
                add(CategoryItem.create(Category.D, categories))
                add(CategoryItem.create(Category.T, categories))

                //fourth line
                add(CategoryItem.Empty)
                add(CategoryItem.create(Category.BE, categories))
                add(CategoryItem.create(Category.CE, categories))
                add(CategoryItem.create(Category.DE, categories))
                add(CategoryItem.Empty)
            }

            categorySelectorState = categorySelectorState.copy(categories = categorySelectorList)
        }

    }

    private fun updateCategories() {

    }

}