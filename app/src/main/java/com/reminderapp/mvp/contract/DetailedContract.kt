package com.reminderapp.mvp.contract

import com.reminderapp.mvp.data.entity.Note

interface DetailedContract {

    interface View : BaseContract.View {
        fun removeNoteFromUi(noteToRemove: Note)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onDeleteNoteFromDatabase(noteToDelete: Note)
        fun onRemoveNoteAlarm(noteToDelete: Note)
    }

    interface Router : BaseContract.Router
}