package com.reminderapp.mvp.presenter

import com.reminderapp.mvp.contract.HostContract
import com.reminderapp.mvp.data.entity.Note

class HostPresenter(router: HostContract.Router) :
    HostContract.Presenter,
    BasePresenter<HostContract.View, HostContract.Router>(router) {

    override fun onClickAddNewNote() {
        router.showNewNoteScreen()
    }

    override fun onClickEditNote(noteToEdit: Note) {
        router.showNewNoteScreen(noteToEdit)
    }

    override fun onClickDetailedNote() {
        router.showNoteDetailedScreen()
    }

    override fun onAlarm() {
        router.showNoteDetailedScreen()
    }
}