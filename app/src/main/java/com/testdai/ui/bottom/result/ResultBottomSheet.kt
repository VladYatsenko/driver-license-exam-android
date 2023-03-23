package com.testdai.ui.bottom.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testdai.R
import com.testdai.ui.screen.exam.ExamViewModel
import com.testdai.ui.theme.Fonts
import com.testdai.ui.theme.Gray
import com.testdai.ui.theme.Skeptic
import com.testdai.widget.BottomSheetWidget
import com.testdai.widget.ButtonWidget

@Composable
fun ResultBottomSheet(
    viewModel: ExamViewModel,
    restart: () -> Unit = {}
) {

    val result by viewModel.examResult.observeAsState(ResultScreenState())

    BottomSheetWidget(
        title = stringResource(id = if (result.isExamPassed) R.string.exam_passed else R.string.exam_not_passed)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.medium,
            text = stringResource(
                id = R.string.result_right_answers,
                result.rightAnsweredCount,
                result.totalCount
            ),
            color = Gray
        )
        ButtonWidget(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 6.dp),
            borderColor = Skeptic,
            containerColor = Skeptic,
            text = stringResource(id = R.string.restart_exam),
            textColor = Color.Black,
            onClick = {
                restart()
            }
        )
    }

}