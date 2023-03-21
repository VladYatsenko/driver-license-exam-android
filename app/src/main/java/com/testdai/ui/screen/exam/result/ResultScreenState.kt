package com.testdai.ui.screen.exam.result

data class ResultScreenState(
    val rightAnsweredCount: Int = -1,
    val totalCount: Int = -1,
    val isExamPassed: Boolean = true
)