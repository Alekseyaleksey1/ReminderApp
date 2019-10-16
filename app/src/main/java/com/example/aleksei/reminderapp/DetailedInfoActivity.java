package com.example.aleksei.reminderapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import com.example.aleksei.reminderapp.model.DataWorker;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.presenter.DetailedPresenter;

import java.util.ArrayList;
import java.util.List;

public class DetailedInfoActivity extends Activity implements DetailedRecyclerViewAdapter.ItemLongClickedCallback{

    DetailedPresenter detailedPresenterInstance;
    DetailedRecyclerViewAdapter detailedRecyclerViewAdapter;

    Button addNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        addNewNote = findViewById(R.id.activity_detailed_btn_addnote);

        detailedPresenterInstance = new DetailedPresenter(this, DataWorker.getInstance(this));
        List<Note> list = new ArrayList<>();
        detailedRecyclerViewAdapter = new DetailedRecyclerViewAdapter(this,list);
        detailedRecyclerViewAdapter.registerForItemLongClickedCallback(this);
    }

    public void onClickAddNewNote(View view) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);
    }

    public void removeNote(final Note noteToRemove){
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
