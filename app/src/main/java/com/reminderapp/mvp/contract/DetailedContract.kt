package com.reminderapp.mvp.contract

import com.reminderapp.mvp.data.entity.Note
import java.util.*

interface DetailedInfoContract {//todo именование Инфо?
    interface View : BaseContract.View {
        val dateToShow: Date //todo переделать, убрать из контракта?
        fun showNotesForThisDay(allNotesForThisDay: List<Note>)
        fun showConfirmationDialog(noteToRemove: Note)
        fun removeNote(noteToRemove: Note)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onShowConfirmationDialog(noteToRemove: Note)
        fun onRemoveNote(noteToRemove: Note)
        fun onGetDayNotes(/*date: Date*/)
    }

    interface Router : BaseContract.Router {
        fun showConfirmationDialog(noteToRemove: Note)
    }
}