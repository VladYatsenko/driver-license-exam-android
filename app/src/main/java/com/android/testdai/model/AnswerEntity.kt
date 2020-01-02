package com.android.testdai.model

import java.io.Serializable

class AnswerEntity constructor(
        var text: String?,
        var isCorrect: Boolean?,
        var isSelected: Boolean?,
        var isAnswered: Boolean?
): Serializable