package com.testdai.core.repository.topic

import android.content.Context
import com.testdai.core.database.ExamDatabaseProvider
import com.testdai.core.database.dao.TopicDao
import com.testdai.model.TopicModel

class TopicRepository (context: Context) {

    private val databaseModule = ExamDatabaseProvider.getInstance(context)

    private val topicDao: TopicDao
        get() = databaseModule.topicDao()

    private val topicMapper = TopicMapper()

    suspend fun loadTopicById(topicId: Int): TopicModel {
        return topicMapper.mapTopic(topicDao.selectTopic(topicId))
    }

    suspend fun loadTopics(): List<TopicModel> {
        return topicMapper.mapTopics(topicDao.selectTopics())
    }

}