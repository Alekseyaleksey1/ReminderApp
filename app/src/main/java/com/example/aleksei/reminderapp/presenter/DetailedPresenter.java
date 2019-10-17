package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.DetailedActivity;
import com.example.aleksei.reminderapp.DetailedRecyclerViewAdapter;
import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DetailedPresenter {

    private Context context;
    private CompositeDisposable disposable;//todo один disposable для всех
    private DataWorker dataWorker;

    public DetailedPresenter(Context context, DataWorker dataWorker) {
        this.context = context;
        this.dataWorker = dataWorker;
        disposable = new CompositeDisposable();
    }

    public void onUIReady(){
        getNotesFromDatabase();
    }

    public void getNotesFromDatabase() {
        disposable.add(dataWorker.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) {
                        DetailedRecyclerViewAdapter.setAllNoteData(notes);
                        DetailedActivity.detailedRecyclerViewAdapter.notifyDataSetChanged();
                        //todo hide loading
                        /*RecyclerViewAdapter.setDataToAdapter((ArrayList<RepositoryModel>) repositoryModelList);
                        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
                        repoListInterfaceInstance.hideLoading();*/
                    }
                }));

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
