package com.reminderapp.util

import androidx.room.TypeConverter
import java.util.Date

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date) = date.time
}