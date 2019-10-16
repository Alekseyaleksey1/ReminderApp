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
    Context context;
    ItemLongClickedCallback callback;
    private List<Note> allNoteData;

    public List<Note> getAllNoteData() {
        return allNoteData;
    }

    public void setAllNoteData(List<Note> allNoteData) {
        this.allNoteData = allNoteData;
    }


    void registerForItemLongClickedCallback(ItemLongClickedCallback callback) {
        this.callback = callback;
    }

    public interface ItemLongClickedCallback {
        void onItemLongClicked(Note note);
    }

    public DetailedRecyclerViewAdapter(Context context, List<Note> allNoteData) {
        this.context = context;
        this.allNoteData = allNoteData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_detailed_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvNoteTime.setText("sometime");
        viewHolder.tvNoteText.setText("sometext");
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

        private LinearLayout container;
        private TextView tvNoteTime;
        private TextView tvNoteText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTime = itemView.findViewById(R.id.activity_detailed_tv_notetime);
            tvNoteText = itemView.findViewById(R.id.activity_detailed_tv_notetext);
            container = itemView.findViewById(R.id.activity_detailed_ll);
        }


    }

}


