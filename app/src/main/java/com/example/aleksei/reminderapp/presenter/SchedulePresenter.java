package com.example.aleksei.reminderapp.presenter;

import android.content.Context;

import com.example.aleksei.reminderapp.ScheduleInterface;
import com.example.aleksei.reminderapp.model.DataWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SchedulePresenter {

    //private Context context;
    private CompositeDisposable disposable;//todo all disposables
    private DataWorker dataWorker;
    private ScheduleInterface scheduleInterfaceInstance;


    public SchedulePresenter(Context context, ScheduleInterface scheduleInterfaceInstance, DataWorker dataWorker) {
        //this.context = context;
        this.dataWorker = dataWorker;
        this.scheduleInterfaceInstance = scheduleInterfaceInstance;
        disposable = new CompositeDisposable();
    }


    /*public void onUIReady() {

        //calculateTime();
        getNotesFromDatabase();//////////////

    }*/


    /*public Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }*/


   /* public void getDataToShow() {
        getNotesFromDatabase();
    }*/

    /*void fetchDataToShow(List<Note> allNotes) {
        List<Date> dateOfWeekDays = getWeekDates();
        List<DayModel> listOfNotedWeekDays = new ArrayList<>();

        for (Date dateDayOfWeek : dateOfWeekDays) {
            List<Note> noteList = new ArrayList<>();

            for (Note note : allNotes) {
                Date notesDate = note.getNoteDate();
                noteList = new ArrayList<>();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(dateDayOfWeek);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(notesDate);

                if ((c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)))) {
                    noteList.add(note);
                }
            }
            listOfNotedWeekDays.add(new DayModel(dateDayOfWeek, noteList));
        }

        scheduleInterfaceInstance.setDataToShow(listOfNotedWeekDays);
    }*/

    public List<Date> getWeekDates() {
        List<Date> dateOfWeekDays = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            calendar.setTime(currentDate);
            int numberOfDay = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, numberOfDay + i);
            Date tempDate = calendar.getTime();
            dateOfWeekDays.add(tempDate);
        }
        return dateOfWeekDays;
    }

    /*private Date[] calculateTime() {

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        Calendar calendar = Calendar.getInstance();

        Date currentDateAndTime = calendar.getTime();//Wed Oct 16 15:36:50 GMT+00:00
        Log.i("timmy", currentDateAndTime.toString());
        //calendar.setTime(dateFormat.parse(currentDateAndTime.toString()));

        calendar.setTime(currentDateAndTime);


        calendar.add(Calendar.DATE, 6);

        Date endDateAndTime = calendar.getTime();//Wed Oct 16 15:36:50 GMT+00:00
        Log.i("timmy", endDateAndTime.toString());
        //String str = dateFormat.format(endDateAndTime);

        //Log.i("timmy",currentDateAndTime.toString()+ " " + str);
        return new Date[]{currentDateAndTime, endDateAndTime};
    }*/

    /*private void getNotesFromDatabase() {
        disposable.add(dataWorker.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> allNotes) {

                        fetchDataToShow(allNotes);


                       *//* ScheduleAdapter.setAllNoteData(notes); БЫЛО
                        ScheduleActivity.scheduleAdapter.notifyDataSetChanged();
                        scheduleInterfaceInstance.hideLoading();*//*


                        *//*RecyclerViewAdapter.setDataToAdapter((ArrayList<RepositoryModel>) repositoryModelList);
                        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
                        repoListInterfaceInstance.hideLoading();*//*
                    }
                }));

    }*/

    /*private void addNote(Note noteToSave) {
        disposable.add(dataWorker.saveNoteToDatabase(noteToSave)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {

                    }
                }));
    }*/

   /* private void removeNote(Note noteToDelete) {
        disposable.add(dataWorker.removeNoteFromDatabase(noteToDelete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {

                    }
                }));

    }*/


    public void disposeAll() {
        disposable.dispose();
    }
}
