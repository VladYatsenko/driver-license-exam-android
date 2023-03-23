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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.core.navigation.NavActions
import com.testdai.core.navigation.Navigate
import com.testdai.model.ExamMode
import com.testdai.ui.bottom.BottomSheet
import com.testdai.ui.bottom.category.CategoryBottomSheet
import com.testdai.ui.bottom.topic.ExamModeState
import com.testdai.ui.bottom.topic.ExamModeWrapper
import com.testdai.ui.bottom.topic.TopicsBottomSheet
import com.testdai.ui.theme.Emperor
import com.testdai.ui.theme.Selago
import com.testdai.ui.theme.Skeptic
import com.testdai.widget.ButtonWidget
import com.testdai.widget.ToolbarWidget
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigate: Navigate = {}
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
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            ToolbarWidget(toolbarTitle = stringResource(id = R.string.app_name)) {
                Image(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            navigate(NavActions.Destination.Settings)
                        },
                    painter = painterResource(id = R.drawable.ic_settings),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = ""
                )
            }
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
            ButtonWidget(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 8.dp),
                containerColor = Skeptic,
                text = stringResource(id = R.string.start_exam),
                textColor = colorResource(id = R.color.black),
                onClick = {
                    navigate(NavActions.Destination.Exam)
                }
            )
        }
    }
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
            .background(MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
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
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.category),
                color = MaterialTheme.colors.primary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Start,
                text = categories,
                color = Emperor
            )
        }
        OutlinedButton(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.dp, Selago),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Selago),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            onClick = onClick
        ) {
            Text(
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.button_change),
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
            .background(MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 8.dp),
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
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                text = stringResource(id = mode.titleRes),
                color = MaterialTheme.colors.primary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Start,
                text = subtitle,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        if (selected) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
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