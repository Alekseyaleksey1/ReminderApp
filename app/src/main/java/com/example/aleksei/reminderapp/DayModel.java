package com.example.aleksei.reminderapp;

import com.example.aleksei.reminderapp.model.Note;

import java.util.Date;
import java.util.List;

public class DayModel {//todo delete

    private Date dateOfDay;

    private List<Note> notesOfDay;

    public Date getDateOfDay() {
        return dateOfDay;
    }

    public void setDateOfDay(Date dateOfDay) {
        this.dateOfDay = dateOfDay;
    }

    public List<Note> getNotesOfDay() {
        return notesOfDay;
    }

    public void setNotesOfDay(List<Note> notesOfDay) {
        this.notesOfDay = notesOfDay;
    }

    public DayModel(Date dateOfDay, List<Note> notesOfDay) {
        this.dateOfDay = dateOfDay;
        this.notesOfDay = notesOfDay;
    }
}
