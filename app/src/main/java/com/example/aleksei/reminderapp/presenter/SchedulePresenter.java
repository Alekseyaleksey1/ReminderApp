package com.example.aleksei.reminderapp.presenter;

import com.example.aleksei.reminderapp.utils.DayPojo;
import com.example.aleksei.reminderapp.view.schedule.ScheduleInterface;
import com.example.aleksei.reminderapp.model.DataStore;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class SchedulePresenter {

    private CompositeDisposable disposable;
    private ScheduleInterface scheduleInterfaceInstance;
    private DataStore dataStore;

    public SchedulePresenter(ScheduleInterface scheduleInterfaceInstance, DataStore dataStore) {
        disposable = new CompositeDisposable();
        this.scheduleInterfaceInstance = scheduleInterfaceInstance;
        this.dataStore = dataStore;
    }

    public void onUIReady() {
        getNotesFromDatabase();
    }

    private void getNotesFromDatabase() {
        disposable.add(dataStore.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allNotes -> {
                    List<DayPojo> listOfNotedWeekDays = dataStore.fetchDataToShow(allNotes);
                    scheduleInterfaceInstance.setDataToList(listOfNotedWeekDays);
                }));
    }

    public void disposeDisposables() {
        disposable.dispose();
    }
}
