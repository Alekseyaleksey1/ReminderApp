package com.reminderapp.mvp.data

import androidx.room.Room
import com.reminderapp.Application
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.mvp.data.entity.Note
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

object DataStore {

    private const val DB_NAME: String = "db"
    private val database: NoteDatabase = Room.databaseBuilder(Application.applicationContext(), NoteDatabase::class.java, DB_NAME).build()//todo lazy

    fun saveNoteToDatabase(noteToAdd: Note) =
            Completable
                    .fromRunnable { database.notesDao().insert(noteToAdd) }
                    .subscribeOn(Schedulers.io())

    fun removeNoteFromDatabase(noteToDelete: Note) =
            Completable
                    .fromRunnable { database.notesDao().delete(noteToDelete) }
                    .subscribeOn(Schedulers.io())

    fun getNotes() = database.notesDao().allNotes().subscribeOn(Schedulers.io())

    fun getNotesToDate(reqDate: Date, allNotes: List<Note>): List<Note> {
        val listOfNotesToDate = ArrayList<Note>()
        val calendarReqDate = Calendar.getInstance()
        calendarReqDate.time = reqDate
        for (note in allNotes) {
            val noteDate = note.noteDate
            val calendarNoteDate = Calendar.getInstance()
            calendarNoteDate.time = noteDate
            if ((calendarReqDate.get(Calendar.DAY_OF_MONTH) == calendarNoteDate.get(Calendar.DAY_OF_MONTH)) && (calendarReqDate.get(Calendar.MONTH) == calendarNoteDate.get(Calendar.MONTH)) && (calendarReqDate.get(Calendar.YEAR) == calendarNoteDate.get(Calendar.YEAR))) {
                listOfNotesToDate.add(note)
            }
        }
        return listOfNotesToDate
    }

    fun fetchDataToShow(allNotes: List<Note>): List<Day> {

        val dateOfWeekDays = DateWorker.getWeekDates()
        val listOfNotedWeekDays = ArrayList<Day>()
        val calendarOfDayDate = Calendar.getInstance()
        val calendarOfNoteDate = Calendar.getInstance()

        for (dateDayOfWeek in dateOfWeekDays) {
            val noteListForDay = ArrayList<Note>()
            calendarOfDayDate.time = dateDayOfWeek

            for (note in allNotes) {
                calendarOfNoteDate.time = note.noteDate
                if ((calendarOfDayDate.get(Calendar.DAY_OF_MONTH) == calendarOfNoteDate.get(Calendar.DAY_OF_MONTH)) && (calendarOfDayDate.get(Calendar.MONTH) == calendarOfNoteDate.get(Calendar.MONTH) && (calendarOfDayDate.get(Calendar.YEAR) == calendarOfNoteDate.get(Calendar.YEAR)))) {
                    noteListForDay.add(note)
                }
            }
            listOfNotedWeekDays.add(Day(dateDayOfWeek, noteListForDay))
        }
        return listOfNotedWeekDays
    }
}
