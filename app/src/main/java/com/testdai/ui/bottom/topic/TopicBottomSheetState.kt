package com.testdai.ui.bottom.topic

import com.testdai.model.ExamMode
import com.testdai.model.TopicModel

data class ExamModeState(
    val mods: List<ExamModeWrapper> = emptyList(),
    val selectedMode: ExamMode = ExamMode.Exam,
    val topics: List<TopicWrapper> = emptyList(),
    val selectedTopic: TopicModel = noopTopic,
)

val noopTopic = TopicModel(-1, "")

data class ExamModeWrapper(
    val mode: ExamMode,
    val selected: Boolean,
    val topic: TopicModel? = null //TODO: sealed class?
) {

    companion object {
        fun create(mode: ExamMode, selectedMode: ExamMode, topic: TopicModel? = null) =
            ExamModeWrapper(mode, mode == selectedMode, topic)
    }

}

data class TopicWrapper(
    val topic: TopicModel,
    val selected: Boolean
) {

    companion object {
        fun create(topic: TopicModel, selectedTopic: TopicModel) =
            TopicWrapper(topic, topic.id == selectedTopic.id)
    }

}