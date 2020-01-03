package com.android.testdai.utils.db

import androidx.room.Dao
import androidx.room.Query
import com.android.testdai.model.QuestionWithAnswers
import io.reactivex.Single

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question WHERE fk_topic = :topicId")
    fun selectQuestion(topicId: Int): List<QuestionWithAnswers>//questionId: Int

    @Query("SELECT * FROM question WHERE fk_topic IN (:topics)")
    fun selectQuestions(topics: List<String>): Single<List<QuestionWithAnswers>>
}