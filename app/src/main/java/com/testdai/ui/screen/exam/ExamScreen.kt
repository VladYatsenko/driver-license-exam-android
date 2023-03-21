package com.testdai.ui.screen.exam

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.model.AnswerModel
import com.testdai.model.QuestionModel
import com.testdai.model.State
import com.testdai.widget.AppButton

@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(factory = ExamViewModel.Factory)
) {

    val examState by viewModel.exam.observeAsState(State.Loading())
    val timer by viewModel.timer.observeAsState(viewModel.initialTime)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.bold,
                text = stringResource(id = R.string.toolbar_exam),
                color = colorResource(id = R.color.white)
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = Fonts.regular,
                text = timer,
                color = colorResource(id = R.color.white)
            )
        }
        when (val exam = examState) {
            is State.Loading -> LoadingState()
            is State.Success -> ExamState(
                state = exam.data,
                onQuestionClick = {
                    viewModel.onQuestionClick(it)
                },
                onAnswerClick = {
                    viewModel.onAnswerClick(it)
                }
            )
            is State.Error -> exam.data?.let { state ->
                ExamState(
                    state = state,
                    onQuestionClick = {
                        viewModel.onQuestionClick(it)
                    },
                    onAnswerClick = {
                        viewModel.onAnswerClick(it)
                    }
                )
            } ?: ErrorState()
        }
    }
}

@Composable
fun LoadingState() {
    Placeholder("Loading questions") {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState() {
    Placeholder("Something went wrong") {
        Image(
            painter = painterResource(R.drawable.ic_warning),
            contentDescription = ""
        )
    }
}

@Composable
fun ExamState(
    state: ExamScreenState,
    onQuestionClick: (QuestionModel) -> Unit = {},
    onAnswerClick: (AnswerModel) -> Unit = {}
) {
    val questions = state.questions
    val question = state.question
    val answers = question.answers

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(count = questions.size, itemContent = { index ->
                Question(
                    question = questions[index],
                    position = index.plus(1),
                    onClick = {
                        onQuestionClick(questions[index])
                    }
                )
            })
        }
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            fontFamily = Fonts.medium,
            text = question.text,
            color = colorResource(id = R.color.white)
        )
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = if (question.hasImage) 4.dp else 0.dp)
                .fillMaxWidth()
                .heightIn(max = if (question.hasImage) 300.dp else 0.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(question.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            onState = {
                Log.i("testLog", it.toString())
            },
            contentScale = ContentScale.Fit
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(count = answers.size, key = { answers[it].id }, itemContent = { index ->
                val answer = answers[index]
                Answer(
                    question = question,
                    answer = answer,
                    onClick = {
                        onAnswerClick(answer)
                    }
                )
            })
        }
    }
}


@Composable
fun Question(
    question: QuestionModel,
    position: Int,
    onClick: () -> Unit = {}
) {
    val textColor = when {
        question.answered -> colorResource(id = R.color.black)
        else -> colorResource(id = R.color.white)
    }
    val borderColor = when {
        question.selected -> colorResource(id = R.color.white)
        else -> Color.Gray
    }
    val containerColor = when {
        question.answered && question.correct -> colorResource(id = R.color.skeptic)
        question.answered -> colorResource(id = R.color.lavender_blush)
        else -> colorResource(id = R.color.shark)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppButton(
            modifier = Modifier.size(40.dp),
            borderColor = borderColor,
            containerColor = containerColor,
            roundedCornerSize = 8.dp,
            contentPadding = PaddingValues(0.dp),
            textSize = 16.sp,
            fontFamily = Fonts.medium,
            text = position.toString(),
            textColor = textColor,
            onClick = onClick
        )
    }
}

@Composable
fun Answer(
    question: QuestionModel,
    answer: AnswerModel,
    onClick: () -> Unit = {}
) {
    val textColor = when {
        question.answered && answer.correct -> colorResource(id = R.color.shark)
        answer.answered -> colorResource(id = R.color.shark)
        else -> colorResource(id = R.color.white)
    }
    val borderColor = when {
        answer.selected -> colorResource(id = R.color.white)
        else -> Color.Gray
    }
    val containerColor = when {
        question.answered && answer.correct -> colorResource(id = R.color.skeptic)
        answer.answered -> colorResource(id = R.color.lavender_blush)
        else -> colorResource(id = R.color.shark)
    }
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            fontFamily = Fonts.medium,
            text = answer.text,
            color = textColor
        )
    }
}

@Composable
fun Placeholder(
    message: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.medium,
            text = message,
            color = colorResource(id = R.color.white)
        )
    }
}

@Preview
@Composable
fun LoadingStatePreview() {
    LoadingState()
}

@Preview
@Composable
fun ErrorStatePreview() {
    ErrorState()
}