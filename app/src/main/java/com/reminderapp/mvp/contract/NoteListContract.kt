package com.reminderapp.mvp.contract

import com.reminderapp.mvp.data.entity.Day

interface NoteListContract {

    interface View : BaseContract.View {
        fun updateNotesOnUi(listOfNotedWeekDays: List<Day>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun onGetNotesFromDatabase()
    }

    interface Router: BaseContract.Router
}