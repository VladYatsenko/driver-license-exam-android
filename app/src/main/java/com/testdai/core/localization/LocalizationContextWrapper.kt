package com.testdai.core.localization

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.*

object LocalizationContextWrapper {

    fun changeLocale(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
    }

    fun updateBaseContextLocale(context: Context?, applyOverrideConfiguration: (Configuration) -> Unit): Context? {
        return context?.let {
            val languagePreferences = LangPreferences(context)

            val locale = languagePreferences.language.locale
            Locale.setDefault(locale)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResourcesLocale(context, locale, applyOverrideConfiguration)
            } else {
                updateResourcesLocaleLegacy(context, locale)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(context: Context, locale: Locale, applyOverrideConfiguration: (Configuration) -> Unit): Context? {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        applyOverrideConfiguration(configuration)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}