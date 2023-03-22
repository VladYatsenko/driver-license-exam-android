package com.testdai.ui.screen.settings

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.testdai.core.localization.LangPreferences
import com.testdai.core.localization.Language
import com.testdai.core.theme.Theme
import com.testdai.core.theme.ThemePreferences
import com.testdai.ui.screen.settings.state.LanguageWrapper
import com.testdai.ui.screen.settings.state.Settings
import com.testdai.ui.screen.settings.state.SettingsScreenState
import com.testdai.ui.screen.settings.state.ThemeWrapper
import com.testdai.utils.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel private constructor(application: Application) :
    BaseAndroidViewModel(application) {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsViewModel(application) as T
            }
        }
    }

    private val themePreferences by lazy { ThemePreferences(context) }
    private val langPreferences by lazy { LangPreferences(context) }

    private var settingsState = SettingsScreenState()

    private val _settings = MutableLiveData(settingsState)
    val settings: LiveData<SettingsScreenState> = _settings

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val language = langPreferences.defaultLang
            val theme = themePreferences.theme

            val settings = listOf(
                Settings.LanguageItem(language),
                Settings.ThemeItem(theme)
            )
            val languages = Language.values().map { LanguageWrapper(it, it == language) }

            val themes = Theme.values().map { ThemeWrapper(it, it == theme) }

            settingsState = settingsState.copy(
                list = settings,
                languages = languages,
                language = language,
                themes = themes,
                theme = theme
            )
            _settings.postValue(settingsState)
        }
    }

    fun changeLanguage(language: Language) {
        if (settingsState.language == language) return

        viewModelScope.launch(Dispatchers.IO) {
            langPreferences.lang = language

            val settings = settingsState.list.map {
                if (it is Settings.LanguageItem) it.copy(language) else it
            }
            val languages = Language.values().map { LanguageWrapper(it, it == language) }

            settingsState = settingsState.copy(
                list = settings,
                languages = languages,
                language = language
            )
            _settings.postValue(settingsState)
        }
    }

    fun changeTheme(theme: Theme) {
        if (settingsState.theme == theme) return

        viewModelScope.launch(Dispatchers.IO) {
            themePreferences.theme = theme

            val settings = settingsState.list.map {
                if (it is Settings.ThemeItem) it.copy(theme) else it
            }
            val themes = Theme.values().map { ThemeWrapper(it, it == theme) }

            settingsState = settingsState.copy(
                list = settings,
                themes = themes,
                theme = theme
            )
            _settings.postValue(settingsState)
        }
    }

}