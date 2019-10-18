package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class AddNotePresenter {

    //private Context context;
    private CompositeDisposable disposable;
    private DataStore dataStore;

    public AddNotePresenter(Context context, DataStore dataStore) {
        //this.context = context;
        this.dataStore = dataStore;
        disposable = new CompositeDisposable();
    }

    public void addNote(Note noteToSave) {
        disposable.add(dataStore.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i("timmy","заметка добавлена");
                    }
                }));
    }

    public void onAddNote(Note noteToSave){
        addNote(noteToSave);
    }
}
