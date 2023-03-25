package com.testdai.ui.screen.settings.state

import com.testdai.R
import com.testdai.core.localization.Language
import com.testdai.core.theme.Theme
import com.testdai.widget.ChooseItem

data class SettingsScreenState(
    val list: List<Settings> = emptyList(),
    val languages: List<LanguageWrapper> = emptyList(),
    val language: Language = Language.Ukrainian,
    val themes: List<ThemeWrapper> = emptyList(),
    val theme: Theme = Theme.System,
    val languageChanged: Boolean = false
)

sealed class Settings {

    data class ThemeItem(val value: Theme): Settings()

    data class LanguageItem(val value: Language): Settings()


    val titleRes: Int
        get() = when (this) {
            is LanguageItem -> R.string.settings_language
            is ThemeItem -> R.string.settings_theme
        }

    val valueRes: Int
        get() = when (this) {
            is LanguageItem -> value.nameRes
            is ThemeItem -> value.nameRes
        }

}

data class ThemeWrapper(
    override val value: Theme,
    override val selected: Boolean
): ChooseItem<Theme> {

    override val title: String? = null

    override val titleRes: Int = value.nameRes

}

data class LanguageWrapper(
    override val value: Language,
    override val selected: Boolean
): ChooseItem<Language> {

    override val title: String? = null

    override val titleRes: Int = value.nameRes

}