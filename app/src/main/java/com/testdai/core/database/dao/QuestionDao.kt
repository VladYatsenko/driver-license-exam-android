package com.testdai.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.testdai.core.database.QuestionWithAnswers

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question WHERE fk_topic = :topicId ORDER BY random() LIMIT 1")
    fun selectQuestion(topicId: Int): QuestionWithAnswers?

    @Query("SELECT * FROM question WHERE fk_topic = :topicId")
    fun selectQuestions(topicId: Int): List<QuestionWithAnswers>
}