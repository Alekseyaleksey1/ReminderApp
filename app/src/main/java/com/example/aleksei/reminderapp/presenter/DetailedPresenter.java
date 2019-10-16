package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class DetailedPresenter {

    private Context context;
    private CompositeDisposable disposable;//todo один disposable для всех
    private DataWorker dataWorker;

    public DetailedPresenter(Context context, DataWorker dataWorker) {
        this.context = context;
        this.dataWorker = dataWorker;
        disposable = new CompositeDisposable();
    }

    public void addNote(Note noteToSave) {
        disposable.add(dataWorker.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i("timmy", "заметка добавлена");
                    }
                }));
    }


     private void removeNote(Note noteToDelete) {
        disposable.add(dataWorker.removeNoteFromDatabase(noteToDelete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i("timmy", "заметка удалена");
                    }
                }));

    }

    public void onAddNote(Note noteToSave) {
        addNote(noteToSave);
    }

    public void onRemoveNote(Note noteToDelete) {
        removeNote(noteToDelete);
    }

}
