package com.reminderapp.mvp.contract

import com.reminderapp.mvp.data.entity.Note

interface NewNoteContract {

    interface View : BaseContract.View {
        fun saveNewNote()
        fun updateUiData(noteToSave: Note)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onSaveNewNoteClicked(noteToSave: Note)
        fun onSaveEditedNoteClicked(noteToUpdate: Note)
        fun onSaveNoteAlarm(noteToSave: Note)
    }

    interface Router : BaseContract.Router
}