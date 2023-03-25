package com.testdai.ui.screen.home

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.preferences.ExamPreferences
import com.testdai.core.repository.topic.TopicRepository
import com.testdai.core.usecase.CategoryUseCase
import com.testdai.model.Category
import com.testdai.model.ExamMode
import com.testdai.model.TopicModel
import com.testdai.ui.bottom.category.CategoryItem
import com.testdai.ui.bottom.category.CategorySelectorState
import com.testdai.ui.bottom.topic.ExamModeState
import com.testdai.ui.bottom.topic.ExamModeWrapper
import com.testdai.ui.bottom.topic.TopicWrapper
import com.testdai.ui.bottom.topic.noopTopic
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel private constructor(application: Application) : BaseAndroidViewModel(application) {

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

    private val topicRepository by lazy { TopicRepository(context) }
    private val examPreferences by lazy { ExamPreferences(context) }

    private val _examMode = MutableLiveData<ExamModeState>()
    val examMode: LiveData<ExamModeState> = _examMode

    private var examModeState = ExamModeState()
        set(value) {
            field = value
            _examMode.postValue(value)
        }

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
            _categories.postValue(CategoryUseCase.provideCategoriesString(categories))

            refreshCategorySelector(categories)

            val topics = topicRepository.loadTopics()
            val topic = topics.firstOrNull { it.id == examPreferences.topicId } ?: kotlin.run {
                val topic = topics.firstOrNull()?: noopTopic
                examPreferences.topicId = topic.id
                topic
            }

            val mode = ExamMode.valueOf(examPreferences.examMode)
            val mods = listOf(
                ExamModeWrapper.create(ExamMode.Exam, mode),
                ExamModeWrapper.create(ExamMode.Training, mode),
                ExamModeWrapper.create(ExamMode.Topic, mode, topic)
            )

            examModeState = examModeState.copy(
                mods = mods,
                selectedMode = mode,
                topics = topics.map { TopicWrapper.create(it, topic) },
                selectedTopic = topic
            )
        }
    }

    fun changeExamMode(mode: ExamMode) {
        if (examModeState.selectedMode == mode) return

        val mods = examModeState.mods.map {
            it.copy(selected = it.mode::value == mode::value)
        }
        examModeState = examModeState.copy(mods = mods, selectedMode = mode)

        examPreferences.examMode = mode.value
    }

    fun changeTopic(topic: TopicModel) {
        if (examModeState.selectedTopic == topic) return

        val topics = examModeState.topics.map {
            it.copy(selected = it.value.id == topic.id)
        }

        val selectedMode = examModeState.selectedMode
        val state = if (selectedMode is ExamMode.Topic) {
            val mods = examModeState.mods.map {
                if (it.mode is ExamMode.Topic) it.copy(topic = topic) else it
            }

            examModeState.copy(mods = mods)
        } else examModeState

        examModeState = state.copy(topics = topics, selectedTopic = topic)

        examPreferences.topicId = topic.id
    }

    fun refreshCategorySelector() {
        refreshCategorySelector(examPreferences.categories)
    }

    fun onCategoryClicked(item: CategoryItem.Item) {
        val selectedCategories = categorySelectorState.selectedCategories.toMutableSet()

        when (item.state) {
            CategoryItem.SelectionState.Common -> selectedCategories.add(item.category)
            CategoryItem.SelectionState.Selected -> selectedCategories.remove(item.category)
            CategoryItem.SelectionState.Disabled -> return
        }

        refreshCategorySelector(selectedCategories)
    }

    private fun refreshCategorySelector(categories: Set<Category>) {
        val categorySelectorList = CategoryUseCase.provideSelector(categories)
        categorySelectorState = categorySelectorState.copy(
            categories = categorySelectorList,
            selectedCategories = categories
        )
    }

    fun saveSelectedCategories() {
        val selectedCategories = categorySelectorState.selectedCategories
        examPreferences.categories = selectedCategories
        _categories.postValue(CategoryUseCase.provideCategoriesString(selectedCategories))
    }

}