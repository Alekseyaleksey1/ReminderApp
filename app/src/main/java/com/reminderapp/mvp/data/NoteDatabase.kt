package com.reminderapp.mvp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.util.Converters

@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}