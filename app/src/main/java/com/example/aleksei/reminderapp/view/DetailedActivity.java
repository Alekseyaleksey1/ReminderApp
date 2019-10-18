package com.example.aleksei.reminderapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class DetailedActivity extends AppCompatActivity implements DetailedInterface, DetailedAdapter.ItemLongClickedCallback {

    DetailedPresenter detailedPresenterInstance;
    public DetailedAdapter detailedAdapter;
    RecyclerView detailedRecyclerView;
    Toolbar detailedToolbar;
    Button addNewNote;
    Date dateToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        addNewNote = findViewById(R.id.activity_detailed_btn_addnote);

        detailedToolbar = findViewById(R.id.activity_detailed_tb);
        String dateInString = getIntent().getStringExtra("chosenDay");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        dateToShow = new Date();
        try {
            dateToShow = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detailedToolbar.setTitle(DateWorker.getDayOfMonth(dateToShow) + " " + DateWorker.getMonth(dateToShow) + ", " + DateWorker.getDayName(dateToShow));
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

    private void configToolbar(String dateToShow) {
    }
    /* @Override
    protected void onPause() {
        super.onPause();
        Log.i("timmy", "detailed activity paused");
    }*/

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
                        detailedPresenterInstance.getNotesFromDatabase();
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

        /*Collections.sort(listToShow, new Comparator<Note>() {
            public int compare(Note o1, Note o2) {
                if (o1.getNoteDate() == null || o2.getNoteDate() == null)
                    return 0;
                return o1.getNoteDate().compareTo(o2.getNoteDate());
            }
        });*/


        detailedAdapter.setAllNoteData(listToShow);
        detailedAdapter.notifyDataSetChanged();
    }
}
