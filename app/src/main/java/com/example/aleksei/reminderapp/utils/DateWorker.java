package com.example.aleksei.reminderapp.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateWorker {

    public static List<Date> getWeekDates() {
        List<Date> dateOfWeekDays = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            Calendar calendarCurrentDate = Calendar.getInstance();
            int dayOfMonth = calendarCurrentDate.get(Calendar.DAY_OF_MONTH);
            calendarCurrentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth + i);
            Date fillerDate = calendarCurrentDate.getTime();
            dateOfWeekDays.add(fillerDate);
        }
        return dateOfWeekDays;
    }

    public static String getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MINUTE));
    }

    public static String getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    }

    public static String getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String adequateDayName;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case (1): {
                adequateDayName = "Воскресенье";
                break;
            }
            case (2): {
                adequateDayName = "Понедельник";
                break;
            }
            case (3): {
                adequateDayName = "Вторник";
                break;
            }
            case (4): {
                adequateDayName = "Среда";
                break;
            }
            case (5): {
                adequateDayName = "Четверг";
                break;
            }
            case (6): {
                adequateDayName = "Пятница";
                break;
            }
            case (7): {
                adequateDayName = "Суббота";
                break;
            }
            default: {
                adequateDayName = "Unavailable";
                break;
            }
        }
        return adequateDayName;
    }

    public static String getMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String adequateMonthName;
        switch (calendar.get(Calendar.MONTH) + 1) {
            case 1: {
                adequateMonthName = "Января";
                break;
            }
            case 2: {
                adequateMonthName = "Февраля";
                break;
            }
            case 3: {
                adequateMonthName = "Марта";
                break;
            }
            case 4: {
                adequateMonthName = "Апреля";
                break;
            }
            case 5: {
                adequateMonthName = "Мая";
                break;
            }
            case 6: {
                adequateMonthName = "Июня";
                break;
            }
            case 7: {
                adequateMonthName = "Июля";
                break;
            }
            case 8: {
                adequateMonthName = "Августа";
                break;
            }
            case 9: {
                adequateMonthName = "Сентября";
                break;
            }
            case 10: {
                adequateMonthName = "Октября";
                break;
            }
            case 11: {
                adequateMonthName = "Ноября";
                break;
            }
            case 12: {
                adequateMonthName = "Декабря";
                break;
            }
            default: {
                adequateMonthName = "Unavailable";
                break;
            }
        }
        return adequateMonthName;
    }
}
