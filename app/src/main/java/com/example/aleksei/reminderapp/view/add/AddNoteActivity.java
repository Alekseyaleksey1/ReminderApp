package com.example.aleksei.reminderapp.view.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleksei.reminderapp.R;
import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.AddNotePresenter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity implements AddNoteInterface {

    AddNotePresenter addNotePresenterInstance;
    EditText edNoteText;
    TextView tvDateAndTime;
    int yearToSet;
    int monthToSet;
    int dayToSet;
    int hourToSet;
    int minuteToSet;
    Date dateToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        Toolbar myToolbar = findViewById(R.id.activity_newnote_tb);
        setSupportActionBar(myToolbar);

        edNoteText = findViewById(R.id.activity_newnote_et_notetext);
        tvDateAndTime = findViewById(R.id.activity_newnote_tv_dateandtime);

        addNotePresenterInstance = new AddNotePresenter(this, DataStore.getInstance(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addNotePresenterInstance.disposeDisposables();
    }

    public void setDateAndTime(View view) {
        pickDate();
    }

    private void pickDate() {

        final Calendar calendarToday = Calendar.getInstance();
        int currentYear = calendarToday.get(Calendar.YEAR);
        int currentMonth = calendarToday.get(Calendar.MONTH);
        int currentDay = calendarToday.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            yearToSet = year;
            monthToSet = month;
            dayToSet = dayOfMonth;
            pickTime();
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private void pickTime() {
        final Calendar calendarToday = Calendar.getInstance();
        int currentHour = calendarToday.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendarToday.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {

            hourToSet = hourOfDay;
            minuteToSet = minute;

            Calendar c2 = Calendar.getInstance();
            if ((c2.get(Calendar.DAY_OF_MONTH) == dayToSet) && ((hourOfDay < c2.get(Calendar.HOUR_OF_DAY) || (hourOfDay == c2.get(Calendar.HOUR_OF_DAY) && minute <= (c2.get(Calendar.MINUTE))))
            )) {
                Toast.makeText(getApplicationContext(), getString(R.string.addnote_activity_toasterror_badtime), Toast.LENGTH_LONG).show();
            } else {
                String dateAndTime = String.format(Locale.US, "%02d:%02d %02d.%02d.%d ", hourToSet, minuteToSet, dayToSet, monthToSet, yearToSet);
                tvDateAndTime.setText(dateAndTime);
                Calendar calendar = Calendar.getInstance();
                calendar.set(yearToSet, monthToSet, dayToSet, hourToSet, minuteToSet, 0);
                dateToAdd = calendar.getTime();
            }
        }, currentHour, currentMinute, true);
        timePickerDialog.show();
    }

    public void saveNewNote(View view) {
        if (edNoteText.getText().toString().length() < 1 || dateToAdd == null) {
            Toast.makeText(this, getString(R.string.addnote_activity_toasterror_nodata), Toast.LENGTH_SHORT).show();
        } else {
            addNotePresenterInstance.onAddNote(new Note(dateToAdd, edNoteText.getText().toString()));
        }
    }

    @Override
    public void finishTheView() {
        finish();
    }
}
