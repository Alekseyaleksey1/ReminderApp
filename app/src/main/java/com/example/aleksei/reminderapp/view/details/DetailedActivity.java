package com.example.aleksei.reminderapp.view.details;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.aleksei.reminderapp.R;
import com.example.aleksei.reminderapp.model.DataStore;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.DetailedPresenter;
import com.example.aleksei.reminderapp.utils.DateWorker;
import com.example.aleksei.reminderapp.view.add.AddNoteActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;
import static com.example.aleksei.reminderapp.view.schedule.ScheduleActivity.DAY_KEY;

public class DetailedActivity extends AppCompatActivity implements DetailedInterface, DetailedAdapter.ItemLongClickedCallback {

    DetailedPresenter detailedPresenterInstance;
    DetailedAdapter detailedAdapter;
    RecyclerView detailedRecyclerView;
    Toolbar detailedToolbar;
    Button addNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        addNewNote = findViewById(R.id.activity_detailed_btn_addnote);
        detailedToolbar = findViewById(R.id.activity_detailed_tb);

        String dateInString = getIntent().getStringExtra(DAY_KEY);
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date dateToShow = new Date();
        try {
            dateToShow = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayOfMonth = DateWorker.getDayOfMonth(dateToShow);
        String month = DateWorker.getMonth(dateToShow);
        String dayName = DateWorker.getDayName(dateToShow);
        String toolBarTitle = String.format(Locale.US, "%s %s, %s", dayOfMonth, month, dayName);
        detailedToolbar.setTitle(toolBarTitle);
        setSupportActionBar(detailedToolbar);

        detailedPresenterInstance = new DetailedPresenter(this, dateToShow, DataStore.getInstance(this));

        List<Note> listForNotes = new ArrayList<>();
        detailedRecyclerView = findViewById(R.id.activity_detailed_rv_allnotes);
        detailedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedAdapter = new DetailedAdapter(this, listForNotes);
        detailedAdapter.registerForItemLongClickedCallback(this);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        detailedRecyclerView.addItemDecoration(itemDecor);
        detailedRecyclerView.setAdapter(detailedAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailedPresenterInstance.onUIReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailedPresenterInstance.disposeDisposables();
    }

    public void onClickAddNewNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void removeNote(final Note noteToRemove) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.detailed_activity_removenote_title))
                .setMessage(getString(R.string.detailed_activity_removenote_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.detailed_activity_removenote_posbtn), (dialog, which) -> {
                    detailedPresenterInstance.onRemoveNote(noteToRemove);
                    detailedPresenterInstance.getNotesFromDatabase();
                })
                .setNegativeButton(getString(R.string.detailed_activity_removenote_negbtn),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemLongClicked(Note note) {
        removeNote(note);
    }

    @Override
    public void setDataToList(List<Note> listToShow) {
        detailedAdapter.setAllNoteData(listToShow);
        detailedAdapter.notifyDataSetChanged();
    }
}
