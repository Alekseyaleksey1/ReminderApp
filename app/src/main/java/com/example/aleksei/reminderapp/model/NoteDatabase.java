package com.example.aleksei.reminderapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao getNotesDao();
}
