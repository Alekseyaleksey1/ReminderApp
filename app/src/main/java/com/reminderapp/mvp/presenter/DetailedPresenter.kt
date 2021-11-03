package com.reminderapp.mvp.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.gson.GsonBuilder
import com.reminderapp.MainApplication
import com.reminderapp.mvp.contract.DetailedContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.receiver.AlarmNotificationReceiver
import com.reminderapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailedPresenter(private val repository: NoteRepository, router: DetailedContract.Router) :
    DetailedContract.Presenter,
    BasePresenter<DetailedContract.View, DetailedContract.Router>(router) {

    override fun onDeleteNoteFromDatabase(noteToDelete: Note) {
        view?.showProgress()
        CoroutineScope(Default).launch {
            repository.deleteNoteFromDatabase(noteToDelete)
            withContext(Main) {
                onRemoveNoteAlarm(noteToDelete)
                view?.removeNoteFromUi(noteToDelete)
                view?.hideProgress()
            }
        }
    }

    override fun onRemoveNoteAlarm(noteToDelete: Note) {
        val alarmManager: AlarmManager =
            MainApplication.applicationContext()
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                MainApplication.applicationContext(),
                noteToDelete.id,
                Intent(
                    MainApplication.applicationContext(),
                    AlarmNotificationReceiver::class.java
                ).apply {
                    action = Constants.ACTION_SHOW_NOTIFICATION
                    putExtra(Constants.NOTE_KEY, GsonBuilder().create().toJson(noteToDelete))
                },
                0
            )
        )
    }
}