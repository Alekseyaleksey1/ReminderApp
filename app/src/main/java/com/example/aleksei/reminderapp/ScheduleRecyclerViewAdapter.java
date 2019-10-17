package com.example.aleksei.reminderapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleksei.reminderapp.model.Note;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Date> weekDates;
    private static List<Note> allNoteData;
    ItemClickedCallback callback;

    public List<Date> getWeekDates() {
        return weekDates;
    }

    public List<Note> getAllNoteData() {
        return allNoteData;
    }

    public static void setAllNoteData(List<Note> allData) {
        allNoteData = allData;
    }

    public ScheduleRecyclerViewAdapter(Context context, List<Date> weekForward, List<Note> data) {
        inflater = LayoutInflater.from(context);
        weekDates = weekForward;
        allNoteData = data;
    }


    interface ItemClickedCallback{
        void onItemClicked(Date dateOfClickedDay);
    }

    public void registerForItemClickedCallback(ItemClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_schedule_rv_item, viewGroup, false);
        View view = inflater.inflate(R.layout.activity_schedule_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.container.setOnClickListener(v -> {
            //weekDates.get(i);
            callback.onItemClicked(weekDates.get(i));
            Log.i("timmyscheduledatepicked",weekDates.get(i).toString() );
        });
        Date date = getWeekDates().get(i);

        String adequateDayName = DateConverter.getDayName(date);
        String adequateMonth = DateConverter.getMonth(date);
        String dayOfMonth = DateConverter.getDayOfMonth(date);
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNotesToShow);

        String adequateDayName = "";//todo method getDayName
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case (1): {
                adequateDayName = "Воскресенье";
                break;
            }
            case (2): {
                adequateDayName = "Понедельник";
                break;
            }
            case (3): {
                adequateDayName = "Вторник";
                break;
            }
            case (4): {
                adequateDayName = "Среда";
                break;
            }
            case (5): {
                adequateDayName = "Четверг";
                break;
            }
            case (6): {
                adequateDayName = "Пятница";
                break;
            }
            case (7): {
                adequateDayName = "Суббота";
                break;
            }
            default: {
                adequateDayName = "Unavailable";
                break;
            }
        }*/
        /*String adequateMonth = "";//todo method getMonth
        switch (calendar.get(Calendar.MONTH) + 1) {
            case 1: {
                adequateMonth = "Января";
                break;
            }
            case 2: {
                adequateMonth = "Февраля";
                break;
            }
            case 3: {
                adequateMonth = "Марта";
                break;
            }
            case 4: {
                adequateMonth = "Апреля";
                break;
            }
            case 5: {
                adequateMonth = "Мая";
                break;
            }
            case 6: {
                adequateMonth = "Июня";
                break;
            }
            case 7: {
                adequateMonth = "Июля";
                break;
            }
            case 8: {
                adequateMonth = "Августа";
                break;
            }
            case 9: {
                adequateMonth = "Сентября";
                break;
            }
            case 10: {
                adequateMonth = "Октября";
                break;
            }
            case 11: {
                adequateMonth = "Ноября";
                break;
            }
            case 12: {
                adequateMonth = "Декабря";
                break;
            }
            default: {
                adequateMonth = "Unavailable";
                break;
            }
        }*/

        viewHolder.tvDayName.setText(adequateDayName);
        //viewHolder.tvDayDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + " " + adequateMonth /*+ " " + calendar.get(Calendar.YEAR) + " года"*/));
        viewHolder.tvDayDate.setText(String.valueOf(dayOfMonth + " " + adequateMonth /*+ " " + calendar.get(Calendar.YEAR) + " года"*/));

        //todo
    }

    @Override
    public int getItemCount() {
        return weekDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout container;
        public TextView tvDayName;
        public TextView tvDayDate;
        public TextView tvNoteOne;
        public TextView tvNoteTwo;
        public TextView tvNoteThree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.activity_schedule_ll);
            tvDayName = itemView.findViewById(R.id.rv_item_dayname);
            tvDayDate = itemView.findViewById(R.id.rv_item_daydate);
            tvNoteOne = itemView.findViewById(R.id.rv_item_noteone);
            tvNoteTwo = itemView.findViewById(R.id.rv_item_notetwo);
            tvNoteThree = itemView.findViewById(R.id.rv_item_notethree);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
