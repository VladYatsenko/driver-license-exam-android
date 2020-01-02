package com.android.testdai.model

import java.io.Serializable

class QuestionEntity constructor(
        var id: Int?,
        var text: String?,
        var image: String?,
        var answerEntities: ArrayList<AnswerEntity>,
        var isSelected: Boolean?,
        var isAnswered: Boolean?
): Serializable