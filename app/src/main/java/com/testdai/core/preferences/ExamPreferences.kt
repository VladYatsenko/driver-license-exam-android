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

    private val examModeKey = "exam_mode"
    private val topicKey = "topic"

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

    @Deprecated("")
    var timeLimit: Boolean
        get() = preferences.getBoolean(timeLimitKey, true)
        set(value) {
            preferences.putBoolean(timeLimitKey, value)
        }

    @Deprecated("")
    var errorLimit: Boolean
        get() = preferences.getBoolean(errorLimitKey, true)
        set(value) {
            preferences.putBoolean(errorLimitKey, value)
        }

    @Deprecated("")
    var doubleClick: Boolean
        get() = preferences.getBoolean(doubleClickKey, false)
        set(value) {
            preferences.putBoolean(doubleClickKey, value)
        }

    var examMode: String
        get() = preferences.getString(examModeKey, null) ?: ""
        set(value) {
            preferences.putString(examModeKey, value)
        }

    var topicId: Int
        get() = preferences.getInt(topicKey, -1)
        set(value) {
            preferences.putInt(topicKey, value)
        }


    fun clearPreferences() = preferences.edit().clear().apply()

    private fun SharedPreferences.putBoolean(key: String, value: Boolean) =
        this.edit().putBoolean(key, value).apply()

    private fun SharedPreferences.putInt(key: String, value: Int) =
        this.edit().putInt(key, value).apply()

    private fun SharedPreferences.putString(key: String, value: String) =
        this.edit().putString(key, value).apply()

}