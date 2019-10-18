package com.example.aleksei.reminderapp.presenter;

import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.view.add.AddNoteInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class AddNotePresenter {

    private AddNoteInterface addNoteInterfaceInstance;
    private DataStore dataStore;
    private CompositeDisposable disposable;

    public AddNotePresenter(AddNoteInterface addNoteInterfaceInstance, DataStore dataStore) {
        disposable = new CompositeDisposable();
        this.addNoteInterfaceInstance = addNoteInterfaceInstance;
        this.dataStore = dataStore;
    }

    private void addNote(Note noteToSave) {
        disposable.add(dataStore.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> addNoteInterfaceInstance.finishTheView()));
    }

    public void onAddNote(Note noteToSave) {
        addNote(noteToSave);
    }

    public void disposeDisposables() {
        disposable.dispose();
    }
}
