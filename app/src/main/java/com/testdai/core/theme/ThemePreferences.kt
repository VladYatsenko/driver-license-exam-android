package com.testdai.core.theme

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testdai.R

enum class Theme {
    Light, Dark, System;

    val nameRes: Int
        get() = when (this) {
            System -> R.string.theme_system
            Light -> R.string.theme_light
            Dark -> R.string.theme_dark
        }

    companion object {
        fun valueOf(lang: String?) = Theme.values().firstOrNull { it.name == lang } ?: System
    }
}

class ThemePreferences private constructor(context: Context) {

    companion object {
        @Volatile private var instance: ThemePreferences? = null

        fun getInstance(context: Context): ThemePreferences {
            return instance ?: synchronized(this) {
                instance ?: ThemePreferences(context).also { instance = it }
            }
        }
    }

    private val preferencesFileName = "theme"

    private val sharedPreferences =
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    private val themeKey = "theme"

    private val _themeState = MutableLiveData<Theme>()
    val themeState: LiveData<Theme> = _themeState

    var theme: Theme
        get() = Theme.valueOf(sharedPreferences.getString(themeKey, null))
        set(theme) {
            sharedPreferences.edit(true) {
                putString(themeKey, theme.name)
            }
            _themeState.postValue(theme)
        }

}