package com.reminderapp.mvp.presenter

import com.reminderapp.mvp.contract.NoteListContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.mvp.data.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListPresenter(private val repository: NoteRepository, router: NoteListContract.Router) :
    NoteListContract.Presenter, RepositoryPresenter,
    BasePresenter<NoteListContract.View, NoteListContract.Router>(router) {

    override fun onUIReady() =
        onGetNotesFromDatabase()

    override fun onGetNotesFromDatabase() {
        view?.showProgress()
        CoroutineScope(Default).launch{
            val allNotes = repository.getNotes()
            withContext(Main){
                updateNotesOnUi(allNotes)
                view?.hideProgress()
            }
        }
    }

    private fun updateNotesOnUi(allNotes: List<Note>) {
        val listOfNotedWeekDays: List<Day> = repository.fetchDataToShow(allNotes)
        view?.updateNotesOnUi(listOfNotedWeekDays)
    }
}