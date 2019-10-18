package com.example.aleksei.reminderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.aleksei.reminderapp.R;
import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.utils.DayPojo;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.SchedulePresenter;
import com.example.aleksei.reminderapp.utils.DateWorker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class ScheduleActivity extends AppCompatActivity implements ScheduleInterface, ScheduleAdapter.ItemClickedCallback {

    SchedulePresenter schedulePresenterInstance;
    RecyclerView scheduleRecyclerView;
    public ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar myToolbar = findViewById(R.id.activity_schedule_tb);
        setSupportActionBar(myToolbar);

        List<DayPojo> listOfWeekDays = new ArrayList<>();
        List<Date> dateOfWeekDays = DateWorker.getWeekDates();
        for (Date dateDayOfWeek : dateOfWeekDays) {
            List<Note> noteList = new ArrayList<>();
            listOfWeekDays.add(new DayPojo(dateDayOfWeek, noteList));
        }

        schedulePresenterInstance = new SchedulePresenter(this, DataStore.getInstance(this));
        scheduleRecyclerView = findViewById(R.id.activity_schedule_rv);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(this, listOfWeekDays);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        scheduleRecyclerView.addItemDecoration(itemDecor);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        scheduleAdapter.registerForItemClickedCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        schedulePresenterInstance.onUIReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        schedulePresenterInstance.disposeDisposables();
    }

    @Override
    public void setDataToList(List<DayPojo> listToShow) {
        scheduleAdapter.setListOfWeekDays(listToShow);
        scheduleAdapter.notifyDataSetChanged();
    }

    public void onClickAddNewNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(Date dateOfClickedDay) {
        Intent intent = new Intent(this, DetailedActivity.class);
        Log.i("timmyscheduledaypicked", dateOfClickedDay.toString());
        intent.putExtra("chosenDay", dateOfClickedDay.toString());
        startActivity(intent);
    }
}
