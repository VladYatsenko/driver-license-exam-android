package com.testdai.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.testdai.core.localization.LocalizationContextWrapper
import com.testdai.core.navigation.DriverLicenseExamNavGraph
import com.testdai.core.theme.DriverLicenseExamTheme
import com.testdai.core.theme.ThemePreferences

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val localizedContext = LocalizationContextWrapper.updateBaseContextLocale(
            newBase,
            ::applyOverrideConfiguration
        )
        super.attachBaseContext(localizedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themePreferences = ThemePreferences.getInstance(this)

        setContent {
            DriverLicenseExamTheme(themePreferences.themeState) {
                DriverLicenseExamApp()
            }
        }
    }

}

@Composable
fun DriverLicenseExamApp() {
    DriverLicenseExamNavGraph()
}