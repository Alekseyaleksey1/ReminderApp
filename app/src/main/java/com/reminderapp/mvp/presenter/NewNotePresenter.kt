package com.reminderapp.mvp.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.reminderapp.MainApplication
import com.reminderapp.mvp.contract.NewNoteContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.receiver.AlarmNotificationReceiver
import com.reminderapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NewNotePresenter(private val repository: NoteRepository, router: NewNoteContract.Router) :
    NewNoteContract.Presenter, BasePresenter<NewNoteContract.View, NewNoteContract.Router>(router) {

    override fun onSaveNewNoteClicked(noteToSave: Note) {
        view?.showProgress()
        CoroutineScope(Default).launch {
            val noteId = repository.saveNoteToDatabase(noteToSave)
            withContext(Main) {
                view?.updateUiData(noteToSave.apply { id = noteId.toInt() })
                if (noteToSave.noteDate.time > Calendar.getInstance().time.time) {
                    onSaveNoteAlarm(noteToSave)
                }
                view?.hideProgress()
                router.back()
            }
        }
    }

    override fun onSaveEditedNoteClicked(noteToUpdate: Note) {
        view?.showProgress()
        CoroutineScope(Default).launch {
            repository.updateNoteInDatabase(noteToUpdate)
            withContext(Main) {
                view?.updateUiData(noteToUpdate)
                if (noteToUpdate.noteDate.time > Calendar.getInstance().time.time) {
                    onSaveNoteAlarm(noteToUpdate)
                }
                view?.hideProgress()
                router.back()
            }
        }
    }

    override fun onSaveNoteAlarm(noteToSave: Note) {
        (MainApplication.applicationContext()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager).setExact(
            AlarmManager.RTC_WAKEUP,
            noteToSave.noteDate.time,
            PendingIntent.getBroadcast(
                MainApplication.applicationContext(),
                noteToSave.id,
                Intent(
                    MainApplication.applicationContext(),
                    AlarmNotificationReceiver::class.java
                )
                    .putExtras(
                        Bundle().apply {
                            putString(
                                Constants.NOTE_KEY, GsonBuilder().create().toJson(noteToSave)
                            )
                        }).setAction(Constants.ACTION_SHOW_NOTIFICATION),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
}