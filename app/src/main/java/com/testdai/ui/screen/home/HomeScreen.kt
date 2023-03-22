package com.testdai.ui.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.core.navigation.NavActions
import com.testdai.model.ExamMode
import com.testdai.ui.bottom.BottomSheet
import com.testdai.ui.bottom.category.CategoryBottomSheet
import com.testdai.ui.bottom.topic.ExamModeState
import com.testdai.ui.bottom.topic.ExamModeWrapper
import com.testdai.ui.bottom.topic.TopicsBottomSheet
import com.testdai.widget.AppButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigate: (NavActions.Destination) -> Unit = {}
) {

    val categories: String by viewModel.categories.observeAsState("")
    val examMode: ExamModeState by viewModel.examMode.observeAsState(ExamModeState())


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    var bottomSheet by remember { mutableStateOf(BottomSheet.Category) }

    fun showTopicsSheet() {
        bottomSheet = BottomSheet.Topic
        coroutineScope.launch {
            sheetState.show()
        }
    }

    fun showCategorySheet() {
        bottomSheet = BottomSheet.Category
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
                BottomSheet.Category -> CategoryBottomSheet(viewModel) {
                    hideBottomSheet()
                }
                else -> TopicsBottomSheet(viewModel) {
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
                text = stringResource(id = R.string.app_name),
                color = colorResource(id = R.color.white)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {}
            if (examMode.mods.isNotEmpty()) {
                ExamModeSelector(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    mods = examMode.mods,
                    onClick = {
                        when {
                            it.mode is ExamMode.Topic && it.selected -> showTopicsSheet()
                            else -> viewModel.changeExamMode(it.mode)
                        }
                    }
                )
            }
            CategorySelector(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                categories = categories,
                onClick = {
                    viewModel.refreshCategorySelector()
                    showCategorySheet()
                }
            )
            ExamButton {
                navigate(NavActions.Destination.Exam)
            }
        }
    }
}

@Composable
fun ExamButton(onClick: () -> Unit) {
    AppButton(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 8.dp),
        containerColor = colorResource(id = R.color.skeptic),
        text = stringResource(id = R.string.start_exam),
        textColor = colorResource(id = R.color.black),
        onClick = onClick
    )
}

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    categories: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.shark), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = stringResource(id = R.string.category),
                color = colorResource(id = R.color.white)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = categories,
                color = colorResource(id = R.color.gray)
            )
        }
        OutlinedButton(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.dp, colorResource(id = R.color.selago)),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorResource(id = R.color.selago)
            ),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            onClick = onClick
        ) {
            Text(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontFamily = Fonts.medium,
                text = stringResource(id = R.string.change),
                color = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun ExamModeSelector(
    modifier: Modifier = Modifier,
    mods: List<ExamModeWrapper>,
    onClick: (ExamModeWrapper) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.shark), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        mods.forEach { modeWrapper ->
            ExamModeItem(
                mode = modeWrapper.mode,
                topicName = modeWrapper.topic?.name.orEmpty(),
                selected = modeWrapper.selected,
                onClick = {
                    onClick(modeWrapper)
                }
            )
        }
    }
}

@Composable
fun ExamModeItem(
    mode: ExamMode = ExamMode.Exam,
    topicName: String = "",
    selected: Boolean = true,
    onClick: () -> Unit
) {
    val subtitle = when (mode) {
        ExamMode.Exam -> stringResource(id = R.string.mode_exam_description)
        ExamMode.Training -> stringResource(id = R.string.mode_training_description)
        is ExamMode.Topic -> if (selected)
            stringResource(id = R.string.mode_topic_description, topicName)
        else topicName
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = stringResource(id = mode.titleRes),
                color = colorResource(id = R.color.white)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = subtitle,
                color = colorResource(id = R.color.gray)
            )
        }
        if (selected) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = ""
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}