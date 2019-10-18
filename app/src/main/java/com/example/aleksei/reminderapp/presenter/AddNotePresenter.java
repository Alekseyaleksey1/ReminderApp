package com.example.aleksei.reminderapp.presenter;

import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class AddNotePresenter {

    private CompositeDisposable disposable;
    private DataStore dataStore;

    public AddNotePresenter(DataStore dataStore) {
        disposable = new CompositeDisposable();
        this.dataStore = dataStore;
    }

    private void addNote(Note noteToSave) {
        disposable.add(dataStore.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    public void onAddNote(Note noteToSave) {
        addNote(noteToSave);
    }

    public void disposeDisposables() {
        disposable.dispose();
    }
}
