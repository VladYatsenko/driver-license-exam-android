package com.testdai.ui.bottom.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.testdai.R
import com.testdai.ui.screen.settings.SettingsViewModel
import com.testdai.ui.screen.settings.state.SettingsScreenState
import com.testdai.widget.ChooseBottomSheet

@Composable
fun ThemeBottomSheet(
    viewModel: SettingsViewModel,
    dismiss: (() -> Unit) -> Unit
) {

    val settings by viewModel.settings.observeAsState(SettingsScreenState())

    ChooseBottomSheet(
        title = stringResource(id = R.string.change_theme),
        list = settings.themes
    ) { theme ->
        dismiss {
            viewModel.changeTheme(theme)
        }
    }

}