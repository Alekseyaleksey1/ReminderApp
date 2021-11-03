package com.aleksei.reminderapp.mvp.presenter

import com.aleksei.reminderapp.mvp.view.details.DetailedInterface
import com.aleksei.reminderapp.mvp.data.DataStore
import com.aleksei.reminderapp.mvp.data.Note
import java.util.Date
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class DetailedPresenter(private val detailedInterfaceInstance: DetailedInterface, private val dateUpToShow: Date, private val dataStore: DataStore) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun onUIReady() = getNotesFromDatabase()

    fun getNotesFromDatabase() =
            disposable.add(dataStore.notes
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { allNotes ->
                        val notesToShow: List<Note> = dataStore.getNotesToDate(dateUpToShow, allNotes);
                        detailedInterfaceInstance.setDataToList(notesToShow)
                    })


    private fun removeNote(noteToDelete: Note) =// TODO tyt два одинаковых метода
            disposable.add(dataStore.removeNoteFromDatabase(noteToDelete)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::getNotesFromDatabase));


    fun onRemoveNote(noteToDelete: Note) = removeNote(noteToDelete)

    fun disposeDisposables() = disposable.dispose()
}
