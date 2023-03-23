package com.testdai.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.testdai.core.theme.Theme

private val DarkPalette = darkColors(
    primary = Alabaster,
    primaryVariant = Emperor,
    secondary = Shark,
    surface = Shark,

    background = Black,

)

private val LightPalette = lightColors(
    primary = Black,
    primaryVariant = Gray,
    secondary = Alabaster,
    surface = Gallery,

    background = Alabaster,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DriverLicenseExamTheme(
    themeState: LiveData<Theme>,
    content: @Composable () -> Unit
) {

    val theme = themeState.observeAsState()

    val darkTheme = when (theme.value) {
        Theme.System -> isSystemInDarkTheme()
        else -> theme.value == Theme.Dark
    }

    val colors = if (darkTheme) DarkPalette else LightPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = colors.background)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        //shapes = Shapes,
        content = content
    )
}