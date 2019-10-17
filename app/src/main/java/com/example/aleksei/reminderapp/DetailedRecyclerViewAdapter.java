package com.example.aleksei.reminderapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleksei.reminderapp.model.Note;

import java.util.List;

public class DetailedRecyclerViewAdapter extends RecyclerView.Adapter<DetailedRecyclerViewAdapter.ViewHolder> {

    //ItemOnLongClickListener onLongClickListener = new ItemOnLongClickListener();

    ItemLongClickedCallback callback;
    private static List<Note> allNoteData;
    LayoutInflater inflater;

    public List<Note> getAllNoteData() {
        return allNoteData;
    }

    public static void setAllNoteData(List<Note> allData) {
        allNoteData = allData;
    }


    void registerForItemLongClickedCallback(ItemLongClickedCallback callback) {
        this.callback = callback;
    }

    public interface ItemLongClickedCallback {
        void onItemLongClicked(Note note);
    }

    public DetailedRecyclerViewAdapter(Context context, List<Note> allNoteData) {

        this.allNoteData = allNoteData;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_detailed_rv_item, viewGroup, false);
        View view = inflater.inflate(R.layout.activity_detailed_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String time = "HH:MM";
        String note = "Заметка";
        viewHolder.tvNoteTime.setText(time);
        viewHolder.tvNoteText.setText(note);
        //todo

        viewHolder.container.setOnLongClickListener(v -> {
            callback.onItemLongClicked(getAllNoteData().get(i));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return allNoteData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout container;
        public TextView tvNoteTime;
        public TextView tvNoteText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTime = itemView.findViewById(R.id.activity_detailed_tv_notetime);
            tvNoteText = itemView.findViewById(R.id.activity_detailed_tv_notetext);
            container = itemView.findViewById(R.id.activity_detailed_ll);
        }


    }

}


