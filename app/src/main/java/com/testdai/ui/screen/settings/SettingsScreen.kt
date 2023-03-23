package com.testdai.ui.screen.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.ui.bottom.BottomSheet
import com.testdai.ui.bottom.language.LanguageBottomSheet
import com.testdai.ui.bottom.theme.ThemeBottomSheet
import com.testdai.ui.screen.settings.state.Settings
import com.testdai.ui.screen.settings.state.SettingsScreenState
import com.testdai.ui.theme.Gray
import com.testdai.widget.ToolbarWidget
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
) {

    val settings by viewModel.settings.observeAsState(SettingsScreenState())

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    var bottomSheet by remember { mutableStateOf(BottomSheet.Category) }

    fun showLanguageSheet() {
        bottomSheet = BottomSheet.Language
        coroutineScope.launch {
            sheetState.show()
        }
    }

    fun showThemeSheet() {
        bottomSheet = BottomSheet.Theme
        coroutineScope.launch {
            sheetState.show()
        }
    }

    fun hideBottomSheet(action: () -> Unit = {}) {
        coroutineScope.launch {
            sheetState.hide()
            action()
        }
    }

    BackHandler(sheetState.isVisible) {
        hideBottomSheet()
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = {
            when (bottomSheet) {
                BottomSheet.Theme -> ThemeBottomSheet(viewModel, ::hideBottomSheet)
                else -> LanguageBottomSheet(viewModel, ::hideBottomSheet)
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            ToolbarWidget(toolbarTitle = stringResource(id = R.string.toolbar_settings))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(count = settings.list.size, itemContent = { index ->
                    val item = settings.list[index]
                    SettingsItem(
                        item = item,
                        onClick = {
                            when (item) {
                                is Settings.LanguageItem -> showLanguageSheet()
                                is Settings.ThemeItem -> showThemeSheet()
                            }
                        }
                    )
                })
            }
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    item: Settings,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Start,
            text = stringResource(id = item.titleRes),
            color = MaterialTheme.colors.primary
        )
        Text(
            modifier = Modifier.wrapContentWidth(),
            style = MaterialTheme.typography.overline,
            textAlign = TextAlign.Start,
            text = stringResource(id = item.valueRes),
            color = Gray
        )
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}