package com.android.testdai.interfaces

import com.android.testdai.model.CategoryEntity
import java.util.ArrayList

interface OnCategorySelectedListener {
    fun onCategorySelected(categories: ArrayList<CategoryEntity>?)
}