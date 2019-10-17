package com.example.aleksei.reminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.NewNotePresenter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    EditText edNoteText;
    String pickedDate;
    String pickedTime;
    TextView tvDateAndTime;
    NewNotePresenter newNotePresenterInstance;
    int yearToSet;
    int monthToSet;
    int dayToSet;
    int hourToSet;
    int minuteToSet;
    Date dateToAdd;
    String textToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        Toolbar myToolbar = findViewById(R.id.activity_newnote_tb);
        setSupportActionBar(myToolbar);

        edNoteText = findViewById(R.id.activity_newnote_et_notetext);
        tvDateAndTime = findViewById(R.id.activity_newnote_tv_dateandtime);

        edNoteText.addTextChangedListener(new TextWatcher() {//todo перенос на след строку + 2 строки максимум
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //todo restriction in 100 symbols
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setDateAndTime(View view) {
        pickDate();
    }

    private void pickDate() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //todo restriction in adding note to past days
                pickedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                pickTime();

                yearToSet = year;
                monthToSet = month;
                dayToSet = dayOfMonth;

            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void pickTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pickedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);

                Calendar c2 = Calendar.getInstance();
                //if(c2.get(Calendar.DAY_OF_MONTH)==dayToSet)
                if ((c2.get(Calendar.DAY_OF_MONTH) == dayToSet) && ((hourOfDay < c2.get(Calendar.HOUR_OF_DAY) || (hourOfDay == c2.get(Calendar.HOUR_OF_DAY) && minute <= (c2.get(Calendar.MINUTE))))
                )) {
                    Toast.makeText(getApplicationContext(), "Нельзя установить прошедшее и текущее время", Toast.LENGTH_LONG).show();
                } else {

                    tvDateAndTime.setText(pickedTime + " " + pickedDate);//todo формат 09:00 вместо 9:0 - есть 106 строка
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(yearToSet, monthToSet, dayToSet, hourOfDay, minute, 0);
                    dateToAdd = calendar.getTime();

                }
            }
        }, mHour, mMinute, true);

        timePickerDialog.show();
    }

    public void saveNewNote(View view) {

        if (edNoteText.getText().toString().length() < 1 || pickedDate == null || pickedTime == null || dateToAdd == null) {
            Toast.makeText(this, "Проверьте введена ли заметка и выставлена ли дата", Toast.LENGTH_SHORT).show();
        } else {
            //todo insert into db
            Log.i("timmy", dateToAdd.toString());//Wed Oct 16 15:36:50 GMT+00:00
            newNotePresenterInstance = new NewNotePresenter(this, DataWorker.getInstance(this));
            newNotePresenterInstance.onAddNote(new Note(dateToAdd, edNoteText.getText().toString()));
            finish();
        }
    }
}
