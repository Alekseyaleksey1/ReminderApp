package com.example.aleksei.reminderapp;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.SchedulePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity implements ScheduleInterface, ScheduleRecyclerViewAdapter.ItemClickedCallback {

    SchedulePresenter schedulePresenterInstance;
    RecyclerView recyclerView;
    public static ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar myToolbar = findViewById(R.id.activity_schedule_tb);
        setSupportActionBar(myToolbar);
        showLoading();//todo
        
        //NoteDatabase db = Room.databaseBuilder(this,NoteDatabase.class,"database").build();


        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);



        /*DateFormat.getDateInstance();
        new SimpleDateFormat();*/
        String currentDateAndTime = sdf.format(new Date());


        /*TextView textView = findViewById(R.id.textView);
        textView.setText(currentDateAndTime);*/

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(currentDateAndTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 6);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        String output = sdf1.format(c.getTime());

        //textView.setText(output);
        //List<Date> dateOfWeekDays = new ArrayList<>();

        /*for (i = 0; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            calendar.setTime(currentDate);
            int j = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, j + 1);
            calendar.s;
            dateOfWeekDays.add()
        }*/


        List<Note> list = new ArrayList<>();
        //list.add(new Note());

        recyclerView = findViewById(R.id.activity_schedule_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(this, getWeekDate(), list);
        recyclerView.setAdapter(scheduleRecyclerViewAdapter);
        schedulePresenterInstance = new SchedulePresenter(this, this, DataWorker.getInstance(this));
        //schedulePresenterInstance.onUIReady();
        scheduleRecyclerViewAdapter.registerForItemClickedCallback(this);
        /*List<Date> weekDate = getWeekDate();
        for (Date date : weekDate) {
            Log.i("timmy", date.toString());
        }*/
    }

    List<Date> getWeekDate() {
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

    @Override
    protected void onResume() {
        super.onResume();
        ////////schedulePresenterInstance.onUIReady();


    }

    public void onClickAddNewNote(View view) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        //intent.putExtra("instance", schedulePresenterInstance);
        startActivity(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onItemClicked(Date dateOfClickedDay) {
        Intent intent = new Intent(this, DetailedInfoActivity.class);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detailed_ab, menu);
        return true;
    }*/
}
