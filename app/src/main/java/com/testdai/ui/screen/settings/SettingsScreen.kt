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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.ui.bottom.BottomSheet
import com.testdai.ui.bottom.language.LanguageBottomSheet
import com.testdai.ui.bottom.theme.ThemeBottomSheet
import com.testdai.ui.screen.settings.state.Settings
import com.testdai.ui.screen.settings.state.SettingsScreenState
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

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
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
                BottomSheet.Language -> LanguageBottomSheet(viewModel) {
                    hideBottomSheet()
                }
                else -> ThemeBottomSheet(viewModel) {
                    hideBottomSheet()
                }
            }

        },
        sheetBackgroundColor = colorResource(id = R.color.black),
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.black))
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontFamily = Fonts.bold,
                text = stringResource(id = R.string.toolbar_settings),
                color = colorResource(id = R.color.white)
            )
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
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.shark), RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            fontFamily = Fonts.medium,
            text = stringResource(id = item.titleRes),
            color = colorResource(id = R.color.white)
        )
        Text(
            modifier = Modifier.wrapContentWidth(),
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            fontFamily = Fonts.medium,
            text = stringResource(id = item.valueRes),
            color = colorResource(id = R.color.gray)
        )
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}