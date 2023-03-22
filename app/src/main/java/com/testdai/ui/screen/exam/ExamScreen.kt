package com.testdai.ui.screen.exam

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.model.AnswerModel
import com.testdai.model.QuestionModel
import com.testdai.model.State
import com.testdai.ui.bottom.result.ResultBottomSheet
import com.testdai.ui.screen.exam.data.ExamScreenState
import com.testdai.ui.screen.exam.data.Toolbar
import com.testdai.widget.AppButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel = viewModel(factory = ExamViewModel.Factory)
) {

    val toolbar by viewModel.toolbar.observeAsState(Toolbar.Noop)
    val examState by viewModel.exam.observeAsState(State.Loading())
    val timer by viewModel.timer.observeAsState(viewModel.initialTime)

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
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

    val toolbarTitle = when (val t = toolbar) {
        Toolbar.Exam -> stringResource(id = R.string.toolbar_exam)
        Toolbar.Training -> stringResource(id = R.string.toolbar_training)
        is Toolbar.Topic -> t.name
        Toolbar.Noop -> ""
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = {
            ResultBottomSheet(viewModel) {
                viewModel.loadQuestions()
                hideBottomSheet()
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
                    text = toolbarTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.white)
                )
                if (timer.isNotBlank()) {
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
            }
            when (val exam = examState) {
                is State.Loading -> LoadingState()
                is State.Success -> {
                    if (exam.data.isActiveExam.not()) {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        }
                    }
                    ExamState(
                        state = exam.data,
                        onQuestionClick = {
                            viewModel.onQuestionClick(it)
                        },
                        onAnswerClick = {
                            viewModel.onAnswerClick(it)
                        }
                    )
                }
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

}

@Composable
fun LoadingState() {
    Placeholder(stringResource(id = R.string.placeholder_loading)) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState() {
    Placeholder(stringResource(id = R.string.placeholder_error)) {
        Image(
            painter = painterResource(R.drawable.ic_warning),
            contentDescription = ""
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExamState(
    state: ExamScreenState,
    onQuestionClick: (QuestionModel) -> Unit = {},
    onAnswerClick: (AnswerModel) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(listState)

    val questions = state.questions
    val question = state.question
    val answers = question.answers

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(question.image)
            .size(Size.ORIGINAL)
            .build()
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            flingBehavior = flingBehavior
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
        if (question.hasImage && painter.state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .heightIn(min = 150.dp, max = 300.dp),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(count = answers.size, itemContent = { index ->
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