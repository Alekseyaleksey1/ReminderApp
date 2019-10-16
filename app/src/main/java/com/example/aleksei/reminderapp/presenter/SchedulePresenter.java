package com.example.aleksei.reminderapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.reminderapp.ScheduleActivity;
import com.example.aleksei.reminderapp.ScheduleInterface;
import com.example.aleksei.reminderapp.ScheduleRecyclerViewAdapter;
import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SchedulePresenter {

    private Context context;
    private CompositeDisposable disposable;
    private DataWorker dataWorker;
    private ScheduleInterface scheduleInterfaceInstance;


    public SchedulePresenter(Context context, ScheduleInterface scheduleInterfaceInstance, DataWorker dataWorker) {
        this.context = context;
        this.dataWorker = dataWorker;
        this.scheduleInterfaceInstance = scheduleInterfaceInstance;
        disposable = new CompositeDisposable();
    }


    public void onUIReady() {

        //calculateTime();
        getNotesFromDatabase();//////////////

    }


    public Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }



    private Date[] calculateTime() {

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
    }

    private void getNotesFromDatabase() {
        disposable.add(dataWorker.getNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) {
                        ScheduleRecyclerViewAdapter.setAllNoteData(notes);
                        ScheduleActivity.scheduleRecyclerViewAdapter.notifyDataSetChanged();
                        scheduleInterfaceInstance.hideLoading();
                        /*RecyclerViewAdapter.setDataToAdapter((ArrayList<RepositoryModel>) repositoryModelList);
                        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
                        repoListInterfaceInstance.hideLoading();*/
                    }
                }));

    }

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
