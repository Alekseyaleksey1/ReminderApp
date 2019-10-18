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
import com.example.aleksei.reminderapp.utils.DayModel;
import com.example.aleksei.reminderapp.model.Note;
import com.example.aleksei.reminderapp.utils.DateWorker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<DayModel> listOfWeekDays;
    private ItemClickedCallback callback;

    private List<DayModel> getListOfWeekDays() {
        return listOfWeekDays;
    }

    void setListOfWeekDays(List<DayModel> listOfWeekDays) {
        this.listOfWeekDays = listOfWeekDays;
    }

    ScheduleAdapter(Context context, List<DayModel> listOfWeekDays) {
        inflater = LayoutInflater.from(context);
        this.listOfWeekDays = listOfWeekDays;
    }

    interface ItemClickedCallback {
        void onItemClicked(Date dateOfClickedDay);
    }

    void registerForItemClickedCallback(ItemClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.activity_schedule_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.container.setOnClickListener(v -> {
            callback.onItemClicked(listOfWeekDays.get(position).getDateOfDay());
        });

        Date date = getListOfWeekDays().get(position).getDateOfDay();

        String dayName = DateWorker.getDayName(date);
        String month = DateWorker.getMonth(date);
        String dayOfMonth = DateWorker.getDayOfMonth(date);

        viewHolder.tvDayName.setText(dayName);
        viewHolder.tvDayDate.setText(String.valueOf(dayOfMonth + " " + month));

        viewHolder.tvNoteOne.setText("");
        viewHolder.tvNoteTwo.setText("");
        viewHolder.tvNoteThree.setText("");

        List<TextView> textViews = new ArrayList<>();
        textViews.add(viewHolder.tvNoteOne);
        textViews.add(viewHolder.tvNoteTwo);
        textViews.add(viewHolder.tvNoteThree);

        if (getListOfWeekDays().get(position).getNotesOfDay().size() > 0) {
            List<Note> listOfDayNotes = getListOfWeekDays().get(position).getNotesOfDay();
            for (int k = 0; k < listOfDayNotes.size(); k++) {
                String hour = DateWorker.getHour(listOfDayNotes.get(k).getNoteDate());
                String minute = DateWorker.getMinute(listOfDayNotes.get(k).getNoteDate());
                String noteText = listOfDayNotes.get(k).getNoteText();
                if (k < textViews.size()) {
                    //работает String noteFullTime = String.format(Locale.US, "%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute));
                    //String noteFullTime = String.format(Locale.US, "%02d:%02d %s", Integer.valueOf(hour), Integer.valueOf(minute),listOfDayNotes.get(k).getNoteText());
                    String noteFullTime = String.format(Locale.US, "%02d:%02d %s", Integer.valueOf(hour), Integer.valueOf(minute), noteText);
                    //textViews.get(k).setText(noteFullTime + " " + listOfDayNotes.get(k).getNoteText());
                    textViews.get(k).setText(noteFullTime);
                } else break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listOfWeekDays.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView tvDayName;
        TextView tvDayDate;
        TextView tvNoteOne;
        TextView tvNoteTwo;
        TextView tvNoteThree;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.activity_schedule_ll);
            tvDayName = itemView.findViewById(R.id.rv_item_dayname);
            tvDayDate = itemView.findViewById(R.id.rv_item_daydate);
            tvNoteOne = itemView.findViewById(R.id.rv_item_noteone);
            tvNoteTwo = itemView.findViewById(R.id.rv_item_notetwo);
            tvNoteThree = itemView.findViewById(R.id.rv_item_notethree);
        }
    }
}
