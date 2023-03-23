package com.testdai.ui.bottom.topic

import com.testdai.model.ExamMode
import com.testdai.model.TopicModel
import com.testdai.widget.ChooseItem

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
    override val value: TopicModel,
    override val selected: Boolean
): ChooseItem<TopicModel> {

    override val title: String = value.name

    override val titleRes: Int? = null

    companion object {
        fun create(topic: TopicModel, selectedTopic: TopicModel) =
            TopicWrapper(topic, topic.id == selectedTopic.id)
    }

}