package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataWorker {

    private static final String DB_NAME = "db";
    private static DataWorker dataWorker;
    private NoteDatabase db;

    private DataWorker() {
    }

    public static DataWorker getInstance(Context context) {
        if (dataWorker == null) {
            dataWorker = new DataWorker();
            dataWorker.db = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
        }
        return dataWorker;
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

    public List<Note> getNotesToDate(Date date, List<Note> allNotes){

        List<Note> listOfNotesToDate = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (Note note : allNotes) {
            Date tempDate = note.getNoteDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(tempDate);
            if ((calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH))&&(calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH))&&(calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR))) {
                listOfNotesToDate.add(note);
            }
        }
        return listOfNotesToDate;
    }
}
