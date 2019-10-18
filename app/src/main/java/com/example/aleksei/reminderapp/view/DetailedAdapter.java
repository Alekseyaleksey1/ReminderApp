package com.example.aleksei.reminderapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleksei.reminderapp.R;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.utils.DateWorker;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAdapter extends RecyclerView.Adapter<DetailedAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Note> allNoteData;
    private ItemLongClickedCallback callback;

    private List<Note> getAllNoteData() {
        return allNoteData;
    }

    void setAllNoteData(List<Note> allData) {
        allNoteData = allData;
    }

    DetailedAdapter(Context context, List<Note> allData) {
        inflater = LayoutInflater.from(context);
        allNoteData = allData;
    }

    public interface ItemLongClickedCallback {
        void onItemLongClicked(Note note);
    }

    void registerForItemLongClickedCallback(ItemLongClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.activity_detailed_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Note note = allNoteData.get(i);
        String noteText = note.getNoteText();
        Date date = note.getNoteDate();
        String timeToShow = String.format(Locale.US, "%02d:%02d", Integer.valueOf(DateWorker.getHour(date)), Integer.valueOf(DateWorker.getMinute(date)));
        viewHolder.tvNoteTime.setText(timeToShow);
        viewHolder.tvNoteText.setText(noteText);

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

        LinearLayout container;
        TextView tvNoteTime;
        TextView tvNoteText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTime = itemView.findViewById(R.id.activity_detailed_tv_notetime);
            tvNoteText = itemView.findViewById(R.id.activity_detailed_tv_notetext);
            container = itemView.findViewById(R.id.activity_detailed_ll);
        }
    }
}


