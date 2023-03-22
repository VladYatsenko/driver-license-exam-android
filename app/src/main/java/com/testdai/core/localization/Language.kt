package com.testdai.core.localization

import android.content.Context
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
    }

}

class LangPreferences(context: Context) {

    private val preferencesFileName = "lang"

    private val sharedPreferences =
        context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)
    private val langKey = "language"

    var languageChanged: (Language) -> Unit = {}

    val defaultLang: Language
        get() = lang ?: Language.Ukrainian

    var lang: Language?
        get() {
            return Language.valueOf(sharedPreferences.getString(langKey, null))
        }
        set(language) {
            language ?: return
//            if (this.lang != language) {
//                languageChanged(language)
//            }
            sharedPreferences.edit(true) {
                putString(langKey, language.name)
            }
        }

}