package com.reminderapp.mvp.data

import androidx.room.Room
import com.reminderapp.MainApplication
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.util.Constants
import java.util.*

class NoteRepository {

    private val database: NoteDatabase by lazy {
        Room.databaseBuilder(
            MainApplication.applicationContext(),
            NoteDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    suspend fun saveNoteToDatabase(noteToSave: Note) =
        database.notesDao().insert(noteToSave)

    suspend fun deleteNoteFromDatabase(noteToDelete: Note) =
        database.notesDao().delete(noteToDelete)

    suspend fun updateNoteInDatabase(noteToUpdate: Note) =
        database.notesDao().update(noteToUpdate)

    suspend fun getNotes() = database.notesDao().allNotes()

    fun fetchDataToShow(allNotes: List<Note>): List<Day> {
        val dateOfWeekDays = getWeekDates()
        val listOfNotedWeekDays = ArrayList<Day>()
        val calendarOfDayDate = Calendar.getInstance()
        val calendarOfNoteDate = Calendar.getInstance()
        for (dateDayOfWeek in dateOfWeekDays) {
            val noteListForDay = ArrayList<Note>()
            calendarOfDayDate.time = dateDayOfWeek

            for (note in allNotes) {
                calendarOfNoteDate.time = note.noteDate
                if ((calendarOfDayDate.get(Calendar.DAY_OF_MONTH) == calendarOfNoteDate.get(Calendar.DAY_OF_MONTH)) && (calendarOfDayDate.get(
                        Calendar.MONTH
                    ) == calendarOfNoteDate.get(Calendar.MONTH) && (calendarOfDayDate.get(Calendar.YEAR) == calendarOfNoteDate.get(
                        Calendar.YEAR
                    )))
                ) {
                    noteListForDay.add(note)
                }
            }
            listOfNotedWeekDays.add(Day(dateDayOfWeek, noteListForDay))
        }
        return listOfNotedWeekDays
    }

    private fun getWeekDates(): List<Date> {
        val dateOfWeekDays = ArrayList<Date>()
        for (i in 0..13) {
            val calendarCurrentDate = Calendar.getInstance()
            val dayOfMonth = calendarCurrentDate.get(Calendar.DAY_OF_MONTH)
            calendarCurrentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth + i)
            val fillerDate: Date = calendarCurrentDate.time
            dateOfWeekDays.add(fillerDate)
        }
        return dateOfWeekDays
    }
}