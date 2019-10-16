package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date noteDate;

    private String noteText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Note(Date noteDate, String noteText) {
        this.noteDate = noteDate;
        this.noteText = noteText;
    }
}
