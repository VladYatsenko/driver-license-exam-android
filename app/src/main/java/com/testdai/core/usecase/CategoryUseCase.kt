package com.testdai.core.usecase

import com.testdai.model.Category
import com.testdai.ui.bottom.category.CategoryItem

object CategoryUseCase {

    fun provideSelector(categories: Set<Category>): List<CategoryItem> {

        return mutableListOf<CategoryItem>().apply {
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
    }

    fun provideCategoryState(
        category: Category,
        selectedCategories: Set<Category>
    ): CategoryItem.SelectionState {
        val categories = selectedCategories.map { it.groupName }.toSet()

        val allowed = Category.groups.any { group ->
            group.containsAll(categories) && group.contains(category.groupName)
        }

        return when {
            selectedCategories.isEmpty() -> CategoryItem.SelectionState.Common
            !allowed -> CategoryItem.SelectionState.Disabled
            selectedCategories.contains(category) -> CategoryItem.SelectionState.Selected
            else -> CategoryItem.SelectionState.Common
        }
    }

    fun provideCategoriesString(categories: Set<Category>): String {
        return mutableSetOf<Category>().apply {
            Category.values().forEach {
                if (categories.contains(it))
                    this.add(it)
            }
        }.joinToString(", ")
    }

}