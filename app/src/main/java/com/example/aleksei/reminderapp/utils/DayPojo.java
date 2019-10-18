package com.example.aleksei.reminderapp.utils;

import com.example.aleksei.reminderapp.model.Note;

import java.util.Date;
import java.util.List;

public class DayPojo {

    private Date dateOfDay;

    private List<Note> notesOfDay;

    public Date getDateOfDay() {
        return dateOfDay;
    }

    public List<Note> getNotesOfDay() {
        return notesOfDay;
    }

    public DayPojo(Date dateOfDay, List<Note> notesOfDay) {
        this.dateOfDay = dateOfDay;
        this.notesOfDay = notesOfDay;
    }
}
