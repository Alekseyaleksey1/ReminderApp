package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.aleksei.reminderapp.utils.DateWorker;
import com.example.aleksei.reminderapp.utils.DayPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataStore {

    private static final String DB_NAME = "db";
    private static DataStore dataStore;
    private NoteDatabase database;

    private DataStore() {
    }

    public static DataStore getInstance(Context context) {
        if (dataStore == null) {
            dataStore = new DataStore();
            dataStore.database = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
        }
        return dataStore;
    }

    private NoteDatabase getDatabase() {
        return database;
    }

    public Completable saveNoteToDatabase(final Note noteToAdd) {
        return Completable
                .create(emitter -> {
                    getDatabase().getNotesDao().insert(noteToAdd);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io());
    }

    public Completable removeNoteFromDatabase(final Note noteToDelete) {
        return Completable
                .create(emitter -> {
                    getDatabase().getNotesDao().delete(noteToDelete);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Note>> getNotes() {
        return getDatabase()
                .getNotesDao()
                .getAllNotes()
                .subscribeOn(Schedulers.io());
    }

    public List<Note> getNotesToDate(Date reqDate, List<Note> allNotes) {

        List<Note> listOfNotesToDate = new ArrayList<>();
        Calendar calendarReqDate = Calendar.getInstance();
        calendarReqDate.setTime(reqDate);
        for (Note note : allNotes) {
            Date noteDate = note.getNoteDate();
            Calendar calendarNoteDate = Calendar.getInstance();
            calendarNoteDate.setTime(noteDate);
            if ((calendarReqDate.get(Calendar.DAY_OF_MONTH) == calendarNoteDate.get(Calendar.DAY_OF_MONTH)) && (calendarReqDate.get(Calendar.MONTH) == calendarNoteDate.get(Calendar.MONTH)) && (calendarReqDate.get(Calendar.YEAR) == calendarNoteDate.get(Calendar.YEAR))) {
                listOfNotesToDate.add(note);
            }
        }
        return listOfNotesToDate;
    }

    public List<DayPojo> fetchDataToShow(List<Note> allNotes) {

        List<Date> dateOfWeekDays = DateWorker.getWeekDates();
        List<DayPojo> listOfNotedWeekDays = new ArrayList<>();
        Calendar calendarOfDayDate = Calendar.getInstance();
        Calendar calendarOfNoteDate = Calendar.getInstance();

        for (Date dateDayOfWeek : dateOfWeekDays) {
            List<Note> noteListForDay = new ArrayList<>();
            calendarOfDayDate.setTime(dateDayOfWeek);

            for (Note note : allNotes) {
                calendarOfNoteDate.setTime(note.getNoteDate());
                if ((calendarOfDayDate.get(Calendar.DAY_OF_MONTH) == calendarOfNoteDate.get(Calendar.DAY_OF_MONTH)) && (calendarOfDayDate.get(Calendar.MONTH) == calendarOfNoteDate.get(Calendar.MONTH) && (calendarOfDayDate.get(Calendar.YEAR) == calendarOfNoteDate.get(Calendar.YEAR)))) {
                    noteListForDay.add(note);
                }
            }
            listOfNotedWeekDays.add(new DayPojo(dateDayOfWeek, noteListForDay));
        }
        return listOfNotedWeekDays;
    }
}
