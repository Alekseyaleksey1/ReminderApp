package com.example.aleksei.reminderapp.presenter;

import com.example.aleksei.reminderapp.view.details.DetailedInterface;
import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;

import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class DetailedPresenter {

    private CompositeDisposable disposable;
    private DetailedInterface detailedInterfaceInstance;
    private Date dateUpToShow;
    private DataStore dataStore;

    public DetailedPresenter(DetailedInterface detailedInterfaceInstance, Date dateUpToShow, DataStore dataStore) {
        disposable = new CompositeDisposable();
        this.detailedInterfaceInstance = detailedInterfaceInstance;
        this.dateUpToShow = dateUpToShow;
        this.dataStore = dataStore;
    }

    public void onUIReady() {
        getNotesFromDatabase();
    }

    public void getNotesFromDatabase() {
        disposable.add(dataStore.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allNotes -> {
                    List<Note> notesToShow = dataStore.getNotesToDate(dateUpToShow, allNotes);
                    detailedInterfaceInstance.setDataToList(notesToShow);
                }));
    }

    private void removeNote(Note noteToDelete) {
        disposable.add(dataStore.removeNoteFromDatabase(noteToDelete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getNotesFromDatabase));
    }

    public void onRemoveNote(Note noteToDelete) {
        removeNote(noteToDelete);
    }

    public void disposeDisposables() {
        disposable.dispose();
    }
}
