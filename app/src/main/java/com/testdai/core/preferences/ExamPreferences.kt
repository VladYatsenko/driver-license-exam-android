package com.testdai.core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.testdai.model.Category

class ExamPreferences constructor(private val context: Context) {

    private val fileName = "settings"
    private val categoriesKey = "categories"
    private val timeLimitKey = "time_limit"
    private val errorLimitKey = "error_limit"
    private val doubleClickKey = "double_click"

    private val preferences by lazy { context.getSharedPreferences(fileName, Context.MODE_PRIVATE) }

    var categories: Set<Category>
        get() = mutableSetOf<Category>().apply {
            preferences.getStringSet(categoriesKey, emptySet())
                ?.mapNotNullTo(this) { Category.typeOf(it) }

            if (this.isEmpty())
                this.add(Category.B)
        }
        set(value) {
            preferences.edit()
                .putStringSet(categoriesKey, value.map { it.name }.toSet())
                .apply()
        }

    var timeLimit: Boolean
        get() = preferences.getBoolean(timeLimitKey, true)
        set(value) {
            preferences.putBoolean(timeLimitKey, value)
        }

    var errorLimit: Boolean
        get() = preferences.getBoolean(errorLimitKey, true)
        set(value) {
            preferences.putBoolean(errorLimitKey, value)
        }

    var doubleClick: Boolean
        get() = preferences.getBoolean(doubleClickKey, false)
        set(value) {
            preferences.putBoolean(doubleClickKey, value)
        }

    fun clearPreferences() = preferences.edit().clear().apply()

    private fun SharedPreferences.putBoolean(key: String, value: Boolean) =
        this.edit().putBoolean(key, value).apply()

}