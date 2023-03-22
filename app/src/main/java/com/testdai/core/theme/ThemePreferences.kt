package com.testdai.core.theme

import android.content.Context
import androidx.core.content.edit
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

class ThemePreferences(context: Context) {

    private val preferencesFileName = "theme"

    private val sharedPreferences =
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    private val themeKey = "theme"


    var theme: Theme
        get() = Theme.valueOf(sharedPreferences.getString(themeKey, null))
        set(theme) {
            sharedPreferences.edit(true) {
                putString(themeKey, theme.name)
            }
        }

}