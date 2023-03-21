package com.testdai.core.repository

import android.content.Context
import com.testdai.core.database.ExamDatabaseModule
import com.testdai.core.database.TopicDao
import com.testdai.core.mapper.TopicMapper
import com.testdai.model.TopicModel

class TopicRepository (context: Context) {

    private val databaseModule = ExamDatabaseModule.getInstance(context)

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