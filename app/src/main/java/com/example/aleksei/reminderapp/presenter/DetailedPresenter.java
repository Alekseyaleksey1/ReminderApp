package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.DetailedInterface;
import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DetailedPresenter {

    //private Context context;
    private CompositeDisposable disposable;//todo один disposable для всех
    private DataStore dataStore;
    DetailedInterface detailedInterfaceInstance;
    Date dateToShow;

    public DetailedPresenter(Context context, DetailedInterface detailedInterfaceInstance, Date dateToShow, DataStore dataStore) {
        //this.context = context;
        this.dataStore = dataStore;
        disposable = new CompositeDisposable();
        this.detailedInterfaceInstance = detailedInterfaceInstance;
        this.dateToShow = dateToShow;
    }

    public void onUIReady() {
        getNotesFromDatabase();
    }

    public void getNotesFromDatabase() {
        disposable.add(dataStore.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> allNotes) {

                        Calendar currentCalendar = Calendar.getInstance();
                        /*currentCalendar.get(Calendar.YEAR);
                        currentCalendar.get(Calendar.MONTH);
                        currentCalendar.get(Calendar.DAY_OF_MONTH);*/


                       /* List<Note> actualNotes = new ArrayList<>();//todo удаление записей за прошлые даты уже работает но рекурсия
                        for (Note note : allNotes) {

                            Calendar notesCalendar = Calendar.getInstance();
                            notesCalendar.setTime(note.getNoteDate());

                            if ((notesCalendar.get(Calendar.DAY_OF_MONTH) >= currentCalendar.get(Calendar.DAY_OF_MONTH)) && (notesCalendar.get(Calendar.MONTH) >= currentCalendar.get(Calendar.MONTH)) && (notesCalendar.get(Calendar.YEAR) >= currentCalendar.get(Calendar.YEAR))) {
                            actualNotes.add(note);
                            } else {
                                removeNote(note);
                            }
                        }*/

                        //TODO удалять записи на прошлые даты РЕКУРСИЯ разобраться

                        //БЫЛО List<Note> notesToShow = dataStore.getNotesToDate(dateToShow, allNotes);
                        List<Note> notesToShow = dataStore.getNotesToDate(dateToShow, allNotes);
                        detailedInterfaceInstance.setDataToList(notesToShow);
                        /*DetailedAdapter.setAllNoteData(allNotes); БЫЛО
                        DetailedActivity.detailedAdapter.notifyDataSetChanged();*/
                        Log.i("timmy", "gotNotesFromDB");
                        //todo hide loading
                        /*RecyclerViewAdapter.setDataToAdapter((ArrayList<RepositoryModel>) repositoryModelList);
                        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
                        repoListInterfaceInstance.hideLoading();*/
                    }
                }));

    }

    /*void setNotesToShow(){
        *//*List<Note> notesToShow = dataStore.getNotesToDate(dateToShow, actualNotes);
        detailedInterfaceInstance.setDataToList(notesToShow);*//*
    }//*/

    /*public void addNote(Note noteToSave) {
        disposable.add(dataStore.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i("timmy", "заметка добавлена");
                    }
                }));
    }*/


    private void removeNote(Note noteToDelete) {
        disposable.add(dataStore.removeNoteFromDatabase(noteToDelete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.i("timmy", "заметка удалена");
                        getNotesFromDatabase();//todo начало рекурсии
                    }
                }));

    }

    /*public void onAddNote(Note noteToSave) {
        addNote(noteToSave);
    }*/

    public void onRemoveNote(Note noteToDelete) {
        removeNote(noteToDelete);
    }

}
