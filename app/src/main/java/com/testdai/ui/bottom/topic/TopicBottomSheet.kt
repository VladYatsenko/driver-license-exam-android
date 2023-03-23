package com.testdai.ui.bottom.topic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.ui.screen.home.HomeViewModel
import com.testdai.widget.ChooseBottomSheet

@Composable
fun TopicsBottomSheet(
    viewModel: HomeViewModel,
    dismiss: () -> Unit = {}
) {

    val mode by viewModel.examMode.observeAsState(ExamModeState())

    ChooseBottomSheet(
        title = stringResource(id = R.string.choose_topic),
        list = mode.topics,
        contentModifier = { pos, text ->
            "${pos.plus(1)}. $text"
        },
        changed = { topic ->
            viewModel.changeTopic(topic)
            dismiss()
        }
    )
}

@Preview
@Composable
fun TopicsBottomSheetPreview() {
    TopicsBottomSheet(viewModel = viewModel(factory = HomeViewModel.Factory))
}