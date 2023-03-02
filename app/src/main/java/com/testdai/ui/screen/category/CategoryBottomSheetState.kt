package com.testdai.ui.screen.category

import com.testdai.model.Category

data class CategorySelectorState(
    val categories: List<CategoryItem> = emptyList(),
    val changed: Boolean = false
)

sealed class CategoryItem {

    data class Item(
        val category: Category,
        val state: SelectionState = SelectionState.Common
    ): CategoryItem()

    object Empty: CategoryItem()

    companion object {
        fun create(category: Category, selectedCategories: Set<Category>): Item {
            val categories = selectedCategories.map { it.groupName }
            val allowedGroups = Category.groups.filter { group ->
                group.any { innerCategory ->
                    categories.any { it == innerCategory }
                } && group.contains(category.groupName)
            }

            val state = when  {
                allowedGroups.isEmpty() -> SelectionState.Disabled
                selectedCategories.contains(category) -> SelectionState.Selected
                else -> SelectionState.Common
            }
            return Item(category, state)
        }
    }

    enum class SelectionState {
        Common, Selected, Disabled
    }
}