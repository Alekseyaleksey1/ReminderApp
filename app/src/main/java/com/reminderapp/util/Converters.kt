package com.aleksei.reminderapp.mvp.data

import androidx.room.TypeConverter
import java.util.Date

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}