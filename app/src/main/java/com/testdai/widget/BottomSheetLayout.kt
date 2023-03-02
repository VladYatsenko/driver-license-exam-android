package com.testdai.widget

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testdai.R
import com.testdai.compose.Fonts
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit = {},
    screenContent: @Composable () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    BackHandler(sheetState.isVisible) {
        hideBottomSheet()
    }

    ModalBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = sheetContent,
        sheetBackgroundColor = colorResource(id = R.color.black),
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        content = screenContent
    )
}

@Preview
@Composable
fun BottomSheetLayout() {
    BottomSheetLayout()
}