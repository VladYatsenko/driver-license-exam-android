package com.testdai.core.mapper

import com.testdai.core.database.TopicEntity
import com.testdai.model.TopicModel

class TopicMapper {

    fun mapTopics(list: List<TopicEntity>): List<TopicModel> {
        return list.map(::mapTopic)
    }

    fun mapTopic(topic: TopicEntity?): TopicModel {
        return TopicModel(
            id = topic?.id ?: -1,
            name = topic?.name ?: ""
        )
    }

}