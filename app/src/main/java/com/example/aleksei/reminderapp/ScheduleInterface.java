package com.example.aleksei.reminderapp;

import com.example.aleksei.reminderapp.model.Note;

import java.util.List;

public interface ScheduleInterface {

    void showLoading();
    void hideLoading();
    void setDataToList(List<DayModel> listToShow);
    //void setDataToShow(List<DayModel> listOfWeekDays);

}
