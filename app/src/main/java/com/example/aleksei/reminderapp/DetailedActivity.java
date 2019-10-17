package com.example.aleksei.reminderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class DetailedActivity extends AppCompatActivity implements DetailedInterface, DetailedRecyclerViewAdapter.ItemLongClickedCallback {

    DetailedPresenter detailedPresenterInstance;
    public static DetailedRecyclerViewAdapter detailedRecyclerViewAdapter;
    RecyclerView detailedRecyclerView;

    Button addNewNote;
    Date dateToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar myToolbar = findViewById(R.id.activity_detailed_tb);
        String dateInString = getIntent().getStringExtra("chosenDay");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        dateToShow = new Date();
        try {
            dateToShow = format.parse(dateInString);
            Log.i("timmy detailed date", "success");
        } catch (ParseException e) {
            Log.i("timmy detailed date", "exception");
            e.printStackTrace();
        }



       /* Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        DateConverter.getDayName(sdf);
        try {
            cal.setTime(sdf.parse(dateInString));// all done
            Log.i("timmy detailed dateInString", "success");
        } catch (ParseException e) {
            Log.i("timmy detailed dateInString", "exception");
            e.printStackTrace();
        }*/

        /*try {
            Date dateToShow = new SimpleDateFormat("EE MM dd yy HH:mm:ss", Locale.US).parse(dateInString);
            Log.i("timmy detailed dateInString", dateToShow.toString());
        } catch (ParseException e) {
            Log.i("timmy detailed dateInString", "exception");
            e.printStackTrace();
        }*/
        Log.i("timmy detailed date", dateInString);
       /* DateConverter.getDayName(dateToShow);
        DateConverter.getDayOfMonth(dateToShow);
        DateConverter.getMonth(dateToShow);*/
        myToolbar.setTitle(DateConverter.getDayOfMonth(dateToShow) + " " + DateConverter.getMonth(dateToShow) + ", " + DateConverter.getDayName(dateToShow));
        //myToolbar.setTitle(dateInString);

        setSupportActionBar(myToolbar);

        addNewNote = findViewById(R.id.activity_detailed_btn_addnote);


        detailedPresenterInstance = new DetailedPresenter(this, this, dateToShow, DataWorker.getInstance(this));

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
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        detailedRecyclerView.addItemDecoration(itemDecor);
        detailedRecyclerView.setAdapter(detailedRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("timmy", "detailed activity resumed");
        detailedPresenterInstance.onUIReady();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("timmy", "detailed activity paused");
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
                        //detailedPresenterInstance.getNotesFromDatabase();
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

    @Override
    public void setDataToList(List<Note> listToShow) {

        /*List<Note> listToShow = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToShow);

        for (Note note : allNotes) {
            Date tempDate = note.getNoteDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(tempDate);
            if (calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)) {
                listToShow.add(note);
            }
        }*/

        Collections.sort(listToShow, new Comparator<Note>() {
            public int compare(Note o1, Note o2) {
                if (o1.getNoteDate() == null || o2.getNoteDate() == null)
                    return 0;
                return o1.getNoteDate().compareTo(o2.getNoteDate());
            }
        });


        detailedRecyclerViewAdapter.setAllNoteData(listToShow);
        detailedRecyclerViewAdapter.notifyDataSetChanged();
    }
}
