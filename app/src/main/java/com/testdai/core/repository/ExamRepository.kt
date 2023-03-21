package com.testdai.core.repository

import android.app.Application
import com.testdai.core.database.ExamDatabaseModule
import com.testdai.core.database.QuestionDao
import com.testdai.core.mapper.ExamMapper
import com.testdai.core.preferences.ExamPreferences
import com.testdai.model.Category
import com.testdai.model.QuestionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class ExamRepository(application: Application) {

    private val categories by lazy { ExamPreferences(application).categories.map { it.groupName } }
    private val databaseModule = ExamDatabaseModule.getInstance(application)

    private val questionDao: QuestionDao
        get() = databaseModule.questionDao()

    private val hasA: Boolean
        get() = categories.contains(Category.A.groupName)

    private val hasB: Boolean
        get() = categories.contains(Category.B.groupName)

    private val hasC: Boolean
        get() = categories.contains(Category.C.groupName)

    private val hasD: Boolean
        get() = categories.contains(Category.D.groupName)

    private val hasE: Boolean
        get() = categories.contains(Category.BE.groupName)
                || categories.contains(Category.CE.groupName)
                || categories.contains(Category.DE.groupName)

    private val hasT: Boolean
        get() = categories.contains(Category.T.groupName)

    private val mutex = Mutex()

    private val mapper by lazy { ExamMapper() }
    suspend fun loadExamQuestions(): List<QuestionModel> {
        return withContext(Dispatchers.IO) {
            val questions = List(20, ::topicIdByQuestionPosition).map {
                async {
                    mutex.withLock {
                        questionDao.selectQuestion(it)
                    }
                }
            }.awaitAll()
            mapper.mapQuestions(questions)
        }
    }

    suspend fun loadTopicQuestions(topicId: Int): List<QuestionModel> {
        return mapper.mapQuestions(questionDao.selectQuestions(topicId))
    }

    private fun topicIdByQuestionPosition(position: Int): Int {
        //find random topic id by question position
        val topics = arrayListOf<Int>()
        when (position) {
            0 -> {
                topics.add(2)
                topics.add(4)
                topics.add(5)
                topics.add(6)
                topics.add(7)
                topics.add(20)

                if (hasA)
                    topics.add(42)
                if (hasB)
                    topics.add(46)
                if (hasC)
                    topics.add(50)
                if (hasD)
                    topics.add(54)
                if (hasE)
                    topics.add(58)
                if (hasT)
                    topics.add(62)
            }
            1 -> {
                topics.add(3)
                topics.add(27)
                topics.add(32)
            }
            2 -> {
                topics.add(35)
            }
            3 -> {
                topics.add(36)
            }
            4 -> {
                topics.add(10)
                topics.add(21)
            }
            5 -> {
                topics.add(11)
                topics.add(12)
            }
            6 -> {
                topics.add(13)
                topics.add(14)
                topics.add(15)
                if (hasA)
                    topics.add(42)
                if (hasB)
                    topics.add(46)
                if (hasC)
                    topics.add(50)
                if (hasD)
                    topics.add(54)
                if (hasE)
                    topics.add(58)
                if (hasT)
                    topics.add(62)
            }
            7 -> {
                topics.add(16)
                topics.add(19)
            }
            8 -> {
                topics.add(8)
                topics.add(9)
                topics.add(17)
                topics.add(18)
            }
            9 -> {
                topics.add(39)
            }
            10 -> {
                topics.add(22)
                topics.add(25)
                topics.add(26)
                topics.add(28)
                topics.add(29)
                topics.add(30)
                topics.add(31)
            }
            11 -> {
                topics.add(23)
                topics.add(24)
            }
            12 -> {
                topics.add(39)
            }
            13 -> {
                topics.add(33)
                topics.add(34)
                topics.add(38)
                topics.add(41)
                if (hasA)
                    topics.add(43)
                    topics.add(44)
                if (hasB)
                    topics.add(47)
                    topics.add(48)
                if (hasC)
                    topics.add(51)
                    topics.add(52)
                if (hasD)
                    topics.add(55)
                    topics.add(56)
                if (hasE)
                    topics.add(59)
                    topics.add(60)
                if (hasT)
                    topics.add(63)
                    topics.add(64)
            }
            14 -> {
                topics.add(35)
            }
            15 -> {
                topics.add(40)
            }
            16 -> {
                topics.add(1)
                if (hasA)
                    topics.add(42)
                if (hasB)
                    topics.add(46)
                if (hasC)
                    topics.add(50)
                if (hasD)
                    topics.add(54)
                if (hasE)
                    topics.add(58)
                if (hasT)
                    topics.add(62)
            }
            17 -> {
                if (hasA)
                    topics.add(43)
                if (hasB)
                    topics.add(47)
                if (hasC)
                    topics.add(51)
                if (hasD)
                    topics.add(55)
                if (hasE)
                    topics.add(59)
                if (hasT)
                    topics.add(63)
            }
            18 -> {
                if (hasA)
                    topics.add(45)
                if (hasB)
                    topics.add(49)
                if (hasC)
                    topics.add(53)
                if (hasD)
                    topics.add(57)
                if (hasE)
                    topics.add(61)
                if (hasT)
                    topics.add(65)
            }
            else -> {
                topics.add(37)
            }
        }

        return topics.random()
    }

}