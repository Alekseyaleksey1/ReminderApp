package com.example.aleksei.reminderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.DetailedPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity implements DetailedRecyclerViewAdapter.ItemLongClickedCallback {

    DetailedPresenter detailedPresenterInstance;
    public static DetailedRecyclerViewAdapter detailedRecyclerViewAdapter;
    RecyclerView detailedRecyclerView;

    Button addNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar myToolbar = findViewById(R.id.activity_detailed_tb);
        String date = getIntent().getStringExtra("chosenDay");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date tempDate = new Date();
        try {
            tempDate = format.parse(date);
            Log.i("timmy detailed date", "success");
        } catch (ParseException e) {
            Log.i("timmy detailed date", "exception");
            e.printStackTrace();
        }



       /* Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        DateConverter.getDayName(sdf);
        try {
            cal.setTime(sdf.parse(date));// all done
            Log.i("timmy detailed date", "success");
        } catch (ParseException e) {
            Log.i("timmy detailed date", "exception");
            e.printStackTrace();
        }*/

        /*try {
            Date tempDate = new SimpleDateFormat("EE MM dd yy HH:mm:ss", Locale.US).parse(date);
            Log.i("timmy detailed date", tempDate.toString());
        } catch (ParseException e) {
            Log.i("timmy detailed date", "exception");
            e.printStackTrace();
        }*/
        Log.i("timmy detailed date", date);
       /* DateConverter.getDayName(tempDate);
        DateConverter.getDayOfMonth(tempDate);
        DateConverter.getMonth(tempDate);*/
        myToolbar.setTitle(DateConverter.getDayName(tempDate)+" "+DateConverter.getDayOfMonth(tempDate)+" "+DateConverter.getMonth(tempDate));
        //myToolbar.setTitle(date);

        setSupportActionBar(myToolbar);

        addNewNote = findViewById(R.id.activity_detailed_btn_addnote);


        detailedPresenterInstance = new DetailedPresenter(this, DataWorker.getInstance(this));

        List<Note> listForNotes = new ArrayList<>();
        /*listForNotes.add(new Note(new Date(), "Сообщение1"));
        listForNotes.add(new Note(new Date(), "Сообщение2"));
        listForNotes.add(new Note(new Date(), "Сообщение3"));
        listForNotes.add(new Note(new Date(), "Сообщение4"));
        listForNotes.add(new Note(new Date(), "Сообщение5"));*/

        detailedRecyclerView = findViewById(R.id.activity_detailed_rv_allnotes);
        detailedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedRecyclerViewAdapter = new DetailedRecyclerViewAdapter(this, listForNotes);
        detailedRecyclerViewAdapter.registerForItemLongClickedCallback(this);
        detailedRecyclerView.setAdapter(detailedRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailedPresenterInstance.onUIReady();
    }

    public void onClickAddNewNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    public void removeNote(final Note noteToRemove) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удаление заметки")
                .setMessage("Вы уверены, что хотите удалить эту заметку?")
                .setCancelable(false)
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        detailedPresenterInstance.onRemoveNote(noteToRemove);
                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();


        /*
        DialogFragment noteDeletionDialog = new NoteDeletionDialogFragment();
        noteDeletionDialog.show(getFragmentManager(),"tag");*/
    }

    @Override
    public void onItemLongClicked(Note note) {
        removeNote(note);
    }
}
