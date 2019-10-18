package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.DayModel;
import com.example.aleksei.reminderapp.utils.DateWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataStore {

    private static final String DB_NAME = "db";
    private static DataStore dataStore;
    private NoteDatabase db;

    private DataStore() {
    }

    public static DataStore getInstance(Context context) {
        if (dataStore == null) {
            dataStore = new DataStore();
            dataStore.db = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
        }
        return dataStore;
    }

    private NoteDatabase getDb() {
        return db;
    }

    public Completable saveNoteToDatabase(final Note noteToAdd) {
        return Completable
                .create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) {
                        getDb().getNotesDao().insert(noteToAdd);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Completable removeNoteFromDatabase(final Note noteToDelete) {
        return Completable
                .create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) {
                        getDb().getNotesDao().delete(noteToDelete);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Note>> getNotes() {
        return getDb().getNotesDao().getAllNotes()
                .subscribeOn(Schedulers.io());
    }

    public List<Note> getNotesToDate(Date date, List<Note> allNotes) {

        List<Note> listOfNotesToDate = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (Note note : allNotes) {
            Date tempDate = note.getNoteDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(tempDate);
            if ((calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)) && (calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)) && (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR))) {
                listOfNotesToDate.add(note);
            }
        }
        return listOfNotesToDate;
    }

    /*public List<Date> getWeekDates() {
        List<Date> dateOfWeekDays = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            calendar.setTime(currentDate);
            int numberOfDay = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, numberOfDay + i);
            Date tempDate = calendar.getTime();
            dateOfWeekDays.add(tempDate);
        }
        return dateOfWeekDays;
    }*/

    public List<DayModel> fetchDataToShow(List<Note> allNotes) {

        /// TEST////

        /*for(Note note : allNotes){

            Log.i("timmyTestNotes",note.getNoteDate().toString());
            Log.i("timmyTestNotes",note.getNoteText());

        }*/

        List<Date> dateOfWeekDays = DateWorker.getWeekDates();
        List<DayModel> listOfNotedWeekDays = new ArrayList<>();


        for (Date dateDayOfWeek : dateOfWeekDays) {
            List<Note> noteList = new ArrayList<>();


            Calendar cOfDay = Calendar.getInstance();
            cOfDay.setTime(dateDayOfWeek);

            for (Note note : allNotes) {


                Calendar cOfNote = Calendar.getInstance();
                cOfNote.setTime(note.getNoteDate());

                if( (cOfDay.get(Calendar.DAY_OF_MONTH)==cOfNote.get(Calendar.DAY_OF_MONTH)) && (cOfDay.get(Calendar.MONTH)==cOfNote.get(Calendar.MONTH) &&(cOfDay.get(Calendar.YEAR)==cOfNote.get(Calendar.YEAR)))){
                    noteList.add(note);
                }
                /*333Date notesDate = note.getNoteDate();
                noteList = new ArrayList<>();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(dateDayOfWeek);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(notesDate);

                if ((c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)))) {
                    noteList.add(note);
                }*/
            }
            listOfNotedWeekDays.add(new DayModel(dateDayOfWeek, noteList));
        }


        /// TEST////

        for (DayModel dayModel : listOfNotedWeekDays) {
            Log.i("timmyTest", dayModel.getDateOfDay().toString());

            List<Note> notes = dayModel.getNotesOfDay();
            for (Note note : notes) {
                Log.i("timmyTest", note.getNoteText());
            }
        }
        return listOfNotedWeekDays;


    }
}
