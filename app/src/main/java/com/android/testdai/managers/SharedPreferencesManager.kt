package com.android.testdai.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.android.testdai.R
import com.android.testdai.model.enities.CategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(val context: Context) {

    private val PREFERENCES_FILE_NAME = "settings"
    private val APP_PREFERENCES_CATEGORIES = "categories"
    private val APP_PREFERENCES_TIME_LIMIT = "time_limit"
    private val APP_PREFERENCES_ERROR_LIMIT = "error_limit"
    private val APP_PREFERENCES_DOUBLE_CLICK = "double_click"
    private val sharedPreferences: SharedPreferences

    init {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    var categories: ArrayList<CategoryEntity>?
        get() {
            if (sharedPreferences.getString(APP_PREFERENCES_CATEGORIES, null) != null){
                return Gson().fromJson(
                        sharedPreferences.getString(APP_PREFERENCES_CATEGORIES, null),
                        object : TypeToken<ArrayList<CategoryEntity>>() {}.type
                )

            } else {
                val list: ArrayList<CategoryEntity> = Gson().fromJson(
                        context.resources.openRawResource(R.raw.categories).bufferedReader(Charsets.UTF_8).use { it.readText() },
                        object : TypeToken<ArrayList<CategoryEntity>>() {}.type
                )
                list.firstOrNull { it.name == "B" }?.isSelected = true
                list.firstOrNull { it.isSelected == true }?.group?.let{
                    list.forEach {category ->
                        category.group?.isEnabled = category.group?.groupName == it.groupName
                    }
                }

                categories = list
                return list
            }
        }
        set(categories) {
            sharedPreferences.edit {
                putString(APP_PREFERENCES_CATEGORIES, Gson().toJson(categories))
            }
        }


    var isTimeLimit: Boolean
        get() = sharedPreferences.getBoolean(APP_PREFERENCES_TIME_LIMIT, true)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(APP_PREFERENCES_TIME_LIMIT, value)
            editor.apply()
        }

    var isErrorLimit: Boolean
        get() = sharedPreferences.getBoolean(APP_PREFERENCES_ERROR_LIMIT, true)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(APP_PREFERENCES_ERROR_LIMIT, value)
            editor.apply()
        }

    var isDoubleClick: Boolean
        get() = sharedPreferences.getBoolean(APP_PREFERENCES_DOUBLE_CLICK, true)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(APP_PREFERENCES_DOUBLE_CLICK, value)
            editor.apply()
        }

    fun clearAllSharedPreferences() {
        sharedPreferences.edit()
                .clear()
                .apply()
    }


}