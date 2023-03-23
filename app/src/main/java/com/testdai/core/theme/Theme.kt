package com.testdai.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.LiveData
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.testdai.R

private val DarkPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200
)

private val LightPalette = lightColors(
//    primary = Purple500,
//    primaryVariant = Purple700,
//    secondary = Teal200

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

    val theme = themeState.observeAsState(Theme.System)

    val darkTheme = when (theme.value) {
        Theme.System -> isSystemInDarkTheme()
        else -> theme.value == Theme.Dark
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = colorResource(id = R.color.black)
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = colorResource(id = R.color.white)
        )
    }

    val colors = if (darkTheme) {
        DarkPalette
    } else {
        LightPalette
    }

    MaterialTheme(
        colors = colors,
        //typography = Typography,
        //shapes = Shapes,
        content = content
    )
}