package com.aleksei.reminderapp.mvp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY noteDate")
    fun allNotes(): Single<List<Note>>

    @Insert
    fun insert(entity: Note)

    @Delete
    fun delete(note: Note)
}