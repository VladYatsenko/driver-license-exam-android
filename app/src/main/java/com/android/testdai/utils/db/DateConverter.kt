package com.android.testdai.utils.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toBoolean(value: Int?): Boolean? {
        return value == 1
    }

}