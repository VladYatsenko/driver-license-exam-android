package com.testdai.ui.bottom.category

import com.testdai.core.usecase.CategoryUseCase
import com.testdai.model.Category

data class CategorySelectorState(
    val categories: List<CategoryItem> = emptyList(),
    val selectedCategories: Set<Category> = emptySet()
)

sealed class CategoryItem {

    data class Item(
        val category: Category,
        val state: SelectionState = SelectionState.Common
    ): CategoryItem()

    object Empty: CategoryItem()

    companion object {
        fun create(category: Category, selected: Set<Category>) =
            Item(category, CategoryUseCase.provideCategoryState(category, selected))
    }

    enum class SelectionState {
        Common, Selected, Disabled
    }
}