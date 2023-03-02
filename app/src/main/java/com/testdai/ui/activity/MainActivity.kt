package com.testdai.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.testdai.compose.DriverLicenseExamTheme
import com.testdai.core.navigation.DriverLicenseExamNavGraph

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriverLicenseExamTheme {
                DriverLicenseExamApp()
            }
        }
    }

}

@Composable
fun DriverLicenseExamApp() {
    DriverLicenseExamNavGraph()
}