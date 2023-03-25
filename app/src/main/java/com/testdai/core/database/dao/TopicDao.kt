package com.testdai.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.testdai.core.database.TopicEntity

@Dao
interface TopicDao {

    @Query("SELECT * FROM topic")
    fun selectTopics(): List<TopicEntity>

    @Query("SELECT * FROM topic WHERE _id = :topicId")
    fun selectTopic(topicId: Int): TopicEntity?

}