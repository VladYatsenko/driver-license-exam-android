package com.testdai.ui.screen.home

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.core.usecase.CategoryUseCase
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

    private val selectedCategories = mutableSetOf<Category>()
    private var categorySelectorState = CategorySelectorState()
        set(value) {
            field = value
            _categorySelector.postValue(value)
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            selectedCategories.addAll(examPreferences.categories)
            _categories.postValue(selectedCategories.joinToString(", "))

            refreshCategorySelector()
        }
    }

    fun onCategoryClicked(item: CategoryItem.Item) {
        when (item.state) {
            CategoryItem.SelectionState.Common -> selectedCategories.add(item.category)
            CategoryItem.SelectionState.Selected -> selectedCategories.remove(item.category)
            CategoryItem.SelectionState.Disabled -> return
        }

        refreshCategorySelector()
    }

    private fun refreshCategorySelector() {
        val categorySelectorList = CategoryUseCase.provideSelector(selectedCategories)
        categorySelectorState = categorySelectorState.copy(categories = categorySelectorList)
    }

}