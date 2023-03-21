package com.testdai.ui.bottom.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.ui.screen.exam.ExamViewModel
import com.testdai.widget.AppButton

@Composable
fun ResultBottomSheet(
    viewModel: ExamViewModel,
    restart: () -> Unit = {}
) {

    val result by viewModel.examResult.observeAsState(ResultScreenState())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp, 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.gray))
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.medium,
            text = stringResource(id = if (result.isExamPassed) R.string.exam_passed else R.string.exam_not_passed),
            color = colorResource(id = R.color.white)
        )
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
            color = colorResource(id = R.color.light_gray)
        )

        AppButton(
            modifier = Modifier
                .padding(top = 16.dp, start = 6.dp, end = 6.dp, bottom = 6.dp),
            borderColor = colorResource(id = R.color.skeptic),
            containerColor = colorResource(R.color.skeptic),
            text = stringResource(id = R.string.restart_exam),
            textColor = colorResource(R.color.black),
            onClick = {
                restart()
            }
        )
    }

}