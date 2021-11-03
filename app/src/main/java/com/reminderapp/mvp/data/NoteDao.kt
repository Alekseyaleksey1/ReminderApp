package com.reminderapp.mvp.data

import androidx.room.*
import com.reminderapp.mvp.data.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY noteDate")
   suspend fun allNotes(): List<Note>

    @Insert
   suspend fun insert(noteToInsert: Note): Long

    @Delete
    suspend fun delete(noteToDelete: Note)

    @Update
    suspend fun update(noteToUpdate: Note)
}