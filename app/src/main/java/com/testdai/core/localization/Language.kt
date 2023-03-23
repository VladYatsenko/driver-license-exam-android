package com.testdai.core.localization

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.content.edit
import com.testdai.R
import java.util.*


enum class Language {
    English, Ukrainian;

    val locale: Locale
        get() = when (this) {
            Ukrainian -> Locale("ua")
            English -> Locale("en")
        }

    val nameRes: Int
        get() = when (this) {
            English -> R.string.language_english
            Ukrainian -> R.string.language_ukrainian
        }

    val flag: String
        get() = when (this) {
            Ukrainian -> "\uD83C\uDDFA\uD83C\uDDE6"
            English -> "\uD83C\uDDEC\uD83C\uDDE7"
        }


    companion object {
        fun valueOf(lang: String?) = values().firstOrNull { it.name == lang }

        fun fromLocale(locale: Locale) = values().firstOrNull { it.locale.language == locale.language }
    }

}

class LangPreferences(context: Context) {

    private val preferencesFileName = "lang"

    private val sharedPreferences =
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    private val langKey = "language"

    private val deviceLocale: Locale
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales.get(0)
        } else {
            Resources.getSystem().configuration.locale
        }

    var language: Language
        get() = Language.valueOf(sharedPreferences.getString(langKey, null))
                ?: Language.fromLocale(deviceLocale)
                ?: Language.Ukrainian
        set(language) {
            if (this.language != language) {
                sharedPreferences.edit(true) {
                    putString(langKey, language.name)
                }
                LocalizationContextWrapper.changeLocale(language.locale)
            }
        }

}