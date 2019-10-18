package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note entity);

    @Query("SELECT * FROM Note ORDER BY noteDate")
    Single<List<Note>> getAllNotes();

    @Delete
    void delete(Note note);
}
