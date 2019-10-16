package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;

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

    public NoteDatabase getDb() {
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
}
