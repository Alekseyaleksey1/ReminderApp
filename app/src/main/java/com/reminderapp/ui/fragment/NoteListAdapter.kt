package com.reminderapp.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.reminderapp.MainApplication
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.R
import com.reminderapp.extensions.*
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class NoteListAdapter(var listOfWeekDays: List<Day>) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private lateinit var callback: ItemClickedCallback

    interface ItemClickedCallback {
        fun onItemClicked(position: Int)
    }

    fun registerForItemClickedCallback(callback: ItemClickedCallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.fragment_notelist_rv_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.container.setOnClickListener { callback.onItemClicked(position) }
        val date: Date = listOfWeekDays[position].dateOfDay
        viewHolder.tvDayName.text = date.getDayName()
        viewHolder.tvDayDate.text = String.format(
            Locale.US,
            MainApplication.applicationContext().getString(R.string.format_shortdate),
            date.getDayOfMonth(),
            date.getMonthName()
        )

        val textViews = ArrayList<TextView>()
        textViews.add(viewHolder.tvNoteOne.apply { text = "" })
        textViews.add(viewHolder.tvNoteTwo.apply { text = "" })
        textViews.add(viewHolder.tvNoteThree.apply { text = "" })

        var i = 0
        for (tv in textViews) {
            if (i < listOfWeekDays[position].notesOfDay.size) {
                tv.text = String.format(
                    Locale.US,
                    MainApplication.applicationContext()
                        .getString(R.string.format_timeandtext),
                    listOfWeekDays[position].notesOfDay[i].noteDate.getHour(),
                    listOfWeekDays[position].notesOfDay[i].noteDate.getMinute(),
                    listOfWeekDays[position].notesOfDay[i].noteText
                )
                i++
            } else break
        }
    }

    override fun getItemCount() = listOfWeekDays.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.findViewById(R.id.fragment_notelist_item_ll)
        val tvDayName: TextView = view.findViewById(R.id.fragment_notelist_rv_item_dayname)
        val tvDayDate: TextView = view.findViewById(R.id.fragment_notelist_rv_item_daydate)
        val tvNoteOne: TextView = view.findViewById(R.id.fragment_notelist_rv_item_noteone)
        val tvNoteTwo: TextView = view.findViewById(R.id.fragment_notelist_rv_item_notetwo)
        val tvNoteThree: TextView = view.findViewById(R.id.fragment_notelist_rv_item_notethree)
    }
}