package com.reminderapp.extensions

import com.reminderapp.MainApplication
import com.reminderapp.R
import java.util.*

fun Date.setTime(
    year: Int = 0,
    month: Int = 0,
    dayOfMonth: Int = 0,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
) = Calendar.getInstance().apply {
    set(year, month, dayOfMonth, hour, minute, second)
}.time


fun Date.getMinute() = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.MINUTE)
}

fun Date.getHour() = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.HOUR_OF_DAY)
}

fun Date.getDayOfMonth() = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.DAY_OF_MONTH)
}

fun Date.getDayName() = Calendar.getInstance().let {
    it.time = this
    when (it.get(Calendar.DAY_OF_WEEK)) {
        1 -> MainApplication.applicationContext().getString(R.string.dayname_sunday)
        2 -> MainApplication.applicationContext().getString(R.string.dayname_monday)
        3 -> MainApplication.applicationContext().getString(R.string.dayname_tuesday)
        4 -> MainApplication.applicationContext().getString(R.string.dayname_wednesday)
        5 -> MainApplication.applicationContext().getString(R.string.dayname_thursday)
        6 -> MainApplication.applicationContext().getString(R.string.dayname_friday)
        7 -> MainApplication.applicationContext().getString(R.string.dayname_saturday)
        else -> MainApplication.applicationContext().getString(R.string.error_stringnotavailable)
    }
}

fun Date.getMonthName() = Calendar.getInstance().let {
    it.time = this
    when (it.get(Calendar.MONTH)) {
        0 -> MainApplication.applicationContext().getString(R.string.monthname_january)
        1 -> MainApplication.applicationContext().getString(R.string.monthname_february)
        2 -> MainApplication.applicationContext().getString(R.string.monthname_march)
        3 -> MainApplication.applicationContext().getString(R.string.monthname_april)
        4 -> MainApplication.applicationContext().getString(R.string.monthname_may)
        5 -> MainApplication.applicationContext().getString(R.string.monthname_june)
        6 -> MainApplication.applicationContext().getString(R.string.monthname_july)
        7 -> MainApplication.applicationContext().getString(R.string.monthname_august)
        8 -> MainApplication.applicationContext().getString(R.string.monthname_september)
        9 -> MainApplication.applicationContext().getString(R.string.monthname_october)
        10 -> MainApplication.applicationContext().getString(R.string.monthname_november)
        11 -> MainApplication.applicationContext().getString(R.string.monthname_december)
        else -> MainApplication.applicationContext().getString(R.string.error_stringnotavailable)
    }
}

fun Date.getMonthNumber() = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.MONTH)
}

fun Date.getYearNumber() = Calendar.getInstance().let {
    it.time = this
    it.get(Calendar.YEAR)
}

