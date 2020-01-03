package com.android.testdai.utils.db

import com.android.testdai.model.CategoryEntity
import com.android.testdai.model.QuestionWithAnswers
import io.reactivex.Observable
import io.reactivex.Single

class DataRepository(private val questionDao: QuestionDao) {

    fun loadQuestion(topicId: Int): Observable<List<QuestionWithAnswers>> {
        return questionDao.selectQuestion(topicId)
    }

    fun getListOfTopics(categories: List<CategoryEntity>?): ArrayList<Int> {
        val list: ArrayList<Int> = ArrayList()
        for (position in 0..19) {
            categories?.let {
                list.add(getTopicByPosition(position, categories))
            }
        }

        return list
    }

    private fun getTopicByPosition(position: Int, categories: List<CategoryEntity>): Int {
        val topics = arrayListOf<Int>()
        when (position) {
            0 -> {
                topics.add(2)
                topics.add(4)
                topics.add(5)
                topics.add(6)
                topics.add(7)
                topics.add(20)

                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(42)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(46)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(50)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(54)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(58)
                }
                if (categories.isContains("T")) {
                    topics.add(62)
                }

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
                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(42)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(46)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(50)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(54)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(58)
                }
                if (categories.isContains("T")) {
                    topics.add(62)
                }
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
                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(43)
                    topics.add(44)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(47)
                    topics.add(48)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(51)
                    topics.add(52)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(55)
                    topics.add(56)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(59)
                    topics.add(60)
                }
                if (categories.isContains("T")) {
                    topics.add(63)
                    topics.add(64)
                }
            }
            14 -> {
                topics.add(35)
            }
            15 -> {
                topics.add(40)
            }
            16 -> {
                topics.add(1)
                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(42)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(46)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(50)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(54)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(58)
                }
                if (categories.isContains("T")) {
                    topics.add(62)
                }
            }
            17 -> {
                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(43)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(47)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(51)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(55)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(59)
                }
                if (categories.isContains("T")) {
                    topics.add(63)
                }
            }
            18 -> {
                if (categories.isContains("A1") || categories.isContains("A")) {
                    topics.add(45)
                }
                if (categories.isContains("B1") || categories.isContains("B")) {
                    topics.add(49)
                }
                if (categories.isContains("C1") || categories.isContains("C")) {
                    topics.add(53)
                }
                if (categories.isContains("D1") || categories.isContains("D")) {
                    topics.add(57)
                }
                if (categories.isContains("C1E")
                        || categories.isContains("D1E")
                        || categories.isContains("BE")
                        || categories.isContains("CE")
                        || categories.isContains("DE")) {
                    topics.add(61)
                }
                if (categories.isContains("T")) {
                    topics.add(65)
                }

            }
            19 -> {
                topics.add(37)
            }
        }

        return topics.random()

    }


    private fun List<CategoryEntity>.isContains(value: String): Boolean {
        return this.firstOrNull { it.name == value } != null
    }

}