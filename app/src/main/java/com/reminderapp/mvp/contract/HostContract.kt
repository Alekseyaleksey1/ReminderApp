package com.reminderapp.mvp.contract

import com.reminderapp.mvp.data.entity.Note

interface HostContract {

    interface View : BaseContract.View{
        fun clickAddNewNote()
        fun clickEditNote(noteToEdit: Note)
        fun clickNoteDetailed()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onClickAddNewNote()
        fun onClickEditNote(noteToEdit: Note)
        fun onClickDetailedNote()
        fun onAlarm()
    }

    interface Router : BaseContract.Router {
        fun showNewNoteScreen(noteToEdit: Note? = null)
        fun showNoteDetailedScreen()
    }
}