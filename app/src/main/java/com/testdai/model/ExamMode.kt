package com.testdai.model

import com.testdai.R

sealed class ExamMode {

    object Exam : ExamMode()

    object Training : ExamMode()

    object Topic : ExamMode()

    val titleRes: Int
        get() = when (this) {
            Exam -> R.string.mode_exam
            Training -> R.string.mode_training
            is Topic -> R.string.mode_topic
        }

    val value: String
        get() = this::class.java.simpleName

    val timeLimit: Boolean
        get() = this == Exam

    val errorLimit: Boolean
        get() = this == Exam

    companion object {
        fun valueOf(value: String): ExamMode {
            return when {
                value.isBlank() -> Exam
                value == Training.value -> Training
                value == Exam.value -> Exam
                else -> Topic
            }
        }
    }

}