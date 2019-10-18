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
    private List<Date> weekDates;
    //private  List<Note> allNoteData;
    ItemClickedCallback callback;

    List<DayModel> listOfWeekDays;
    /*List<DayModel> listOfWeekDays;*/

     /*222public List<Date> getWeekDates() {
        return weekDates;
    }*/

    public List<DayModel> getListOfWeekDays() {
        return listOfWeekDays;
    }

    public void setListOfWeekDays(List<DayModel> listOfWeekDays) {
        this.listOfWeekDays = listOfWeekDays;
    }

/*11 public List<Note> getAllNoteData() {
        return allNoteData;
    }*/


    /*222public void setAllNoteData(List<DayModel> allData) {
        listOfWeekDays = allData;
    }*/

    /*22public ScheduleAdapter(Context context, List<Date> weekForward, List<Note> data) {
        inflater = LayoutInflater.from(context);
        weekDates = weekForward;
        //11 allNoteData = data;
    }*/
    public ScheduleAdapter(Context context, List<DayModel> listOfWeekDays) {
        inflater = LayoutInflater.from(context);
        //БЫЛО weekDates = weekForward;
        //БЫЛО allNoteData = data;

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
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_schedule_rv_item, viewGroup, false);
        View view = inflater.inflate(R.layout.activity_schedule_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.container.setOnClickListener(v -> {
            //weekDates.get(i);
            callback.onItemClicked(listOfWeekDays.get(position).getDateOfDay());
            //Log.i("timmyscheduledatepicked", listOfWeekDays.get(i).toString());
        });
        /*222Date date = getWeekDates().get(i);*/

        Date date = getListOfWeekDays().get(position).getDateOfDay();

        String adequateDayName = DateWorker.getDayName(date);
        String adequateMonth = DateWorker.getMonth(date);
        String dayOfMonth = DateWorker.getDayOfMonth(date);

        viewHolder.tvDayName.setText(adequateDayName);
        //viewHolder.tvDayDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + " " + adequateMonth /*+ " " + calendar.get(Calendar.YEAR) + " года"*/));
        viewHolder.tvDayDate.setText(String.valueOf(dayOfMonth + " " + adequateMonth /*+ " " + calendar.get(Calendar.YEAR) + " года"*/));

        viewHolder.tvNoteOne.setText("");
        viewHolder.tvNoteTwo.setText("");
        viewHolder.tvNoteThree.setText("");

        List<TextView> textViews = new ArrayList<>();
        textViews.add(viewHolder.tvNoteOne);
        textViews.add(viewHolder.tvNoteTwo);
        textViews.add(viewHolder.tvNoteThree);


        if (getListOfWeekDays().get(position).getNotesOfDay().size() > 0) {
            /*Calendar calendar = Calendar.getInstance();
            calendar.setTime(getListOfWeekDays().get(position).getDateOfDay());*/



            List<Note> listOfDayNotes = getListOfWeekDays().get(position).getNotesOfDay();
            for (int k = 0; k < listOfDayNotes.size(); k++) {
                String hour = DateWorker.getHour(listOfDayNotes.get(k).getNoteDate());
                String minute = DateWorker.getMinute(listOfDayNotes.get(k).getNoteDate());
                if (k < textViews.size()) {
                    String noteTime = String.format(Locale.US, "%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute));
                    textViews.get(k).setText(noteTime + " " + listOfDayNotes.get(k).getNoteText());

                } else break;
            }

            /* if (textViews.size() == k) {
                    break;
                }
                textViews.get(k).setText(listOfDayNotes.get(k).getNoteText());*/

            /*РАБОТАЕТ for(int k=0; k<listOfDayNotes.size();k++){
                if(textViews.size()==k){break;}
                textViews.get(k).setText(listOfDayNotes.get(k).getNoteText());

            }*/

            /*List<Note> listOfDayNotes = getListOfWeekDays().get(i).getNotesOfDay();
            while(listOfDayNotes){}
            for (int j = 0; j < listOfDayNotes.size(); j++) {

                textViews.get(j).setText(listOfDayNotes.get(j).getNoteText());
            }*/
        }

        /*333if (getListOfWeekDays().get(i).getNotesOfDay().size() > 0) {
            List<Note> listOfDayNotes = getListOfWeekDays().get(i).getNotesOfDay();
            for (int j = 0; j < textViews.size(); j++) {
                if(getListOfWeekDays().get(j).getNotesOfDay().get(j)!=null)
                textViews.get(j).setText(listOfDayNotes.get(j).getNoteText());
            }
        }*/
        //todo
    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.container.setOnClickListener(v -> {
            //weekDates.get(i);
            callback.onItemClicked(weekDates.get(i));
            Log.i("timmyscheduledatepicked",weekDates.get(i).toString() );
        });
        Date date = getWeekDates().get(i);

        String adequateDayName = DateWorker.getDayName(date);
        String adequateMonth = DateWorker.getMonth(date);
        String dayOfMonth = DateWorker.getDayOfMonth(date);

        viewHolder.tvDayName.setText(adequateDayName);
        //viewHolder.tvDayDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + " " + adequateMonth ));
        viewHolder.tvDayDate.setText(String.valueOf(dayOfMonth + " " + adequateMonth ));


    }*/

    /*222@Override
    public int getItemCount() {
        return weekDates.size();
    }*/

    @Override
    public int getItemCount() {
        return listOfWeekDays.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
