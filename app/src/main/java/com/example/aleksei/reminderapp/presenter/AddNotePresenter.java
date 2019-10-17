package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class AddNotePresenter {

    //private Context context;
    private CompositeDisposable disposable;
    private DataWorker dataWorker;

    public AddNotePresenter(Context context, DataWorker dataWorker) {
        //this.context = context;
        this.dataWorker = dataWorker;
        disposable = new CompositeDisposable();
    }

    public void addNote(Note noteToSave) {
        disposable.add(dataWorker.saveNoteToDatabase(noteToSave)
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
