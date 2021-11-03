package com.reminderapp.ui.activity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.data.DateWorker
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.R
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class NoteListAdapter(context: Context, private var listOfWeekDays: List<Day>) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var callback: ItemClickedCallback? = null

    private fun getListOfWeekDays(): List<Day> = listOfWeekDays //todo remove getter

    fun setListOfWeekDays(listOfWeekDays: List<Day>) {
        this.listOfWeekDays = listOfWeekDays //todo remove setter
    }

    interface ItemClickedCallback {
        fun onItemClicked(date: Date)
    }

    fun registerForItemClickedCallback(callback: ItemClickedCallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.fragment_notelist_rv_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.container.setOnClickListener { callback!!.onItemClicked(listOfWeekDays[position].dateOfDay) }

        val date: Date = getListOfWeekDays()[position].dateOfDay
        val dayName: String = DateWorker.getDayName(date)
        val month: String = DateWorker.getMonth(date)
        val dayOfMonth: String = DateWorker.getDayOfMonth(date)

        viewHolder.tvDayName.text = dayName
        val dayAndMonth: String = String.format(Locale.US, "%s %s", dayOfMonth, month)
        viewHolder.tvDayDate.text = dayAndMonth
        viewHolder.tvNoteOne.text = ""
        viewHolder.tvNoteTwo.text = ""
        viewHolder.tvNoteThree.text = ""

        val textViews = ArrayList<TextView>()
        textViews.add(viewHolder.tvNoteOne)
        textViews.add(viewHolder.tvNoteTwo)
        textViews.add(viewHolder.tvNoteThree)

        if (getListOfWeekDays()[position].notesOfDay.isNotEmpty()) {
            val listOfDayNotes: List<Note> = getListOfWeekDays()[position].notesOfDay
            for (dayNote: Note in listOfDayNotes) {
                val hour = DateWorker.getHour(dayNote.noteDate)
                val minute = DateWorker.getMinute(dayNote.noteDate)
                val noteText = dayNote.noteText
                val noteFullTime = String.format(Locale.US, "%02d:%02d %s", Integer.valueOf(hour), Integer.valueOf(minute), noteText)
                dayNote.noteText = noteFullTime
            }
        }
    }

    override fun getItemCount() = listOfWeekDays.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = view.findViewById(R.id.fragment_notelist_item_ll)
        val tvDayName: TextView = view.findViewById(R.id.fragment_notelist_rv_item_dayname)
        val tvDayDate: TextView = view.findViewById(R.id.fragment_notelist_rv_item_daydate)
        val tvNoteOne: TextView = view.findViewById(R.id.fragment_notelist_rv_item_noteone)
        val tvNoteTwo: TextView = view.findViewById(R.id.fragment_notelist_rv_item_notetwo)
        val tvNoteThree: TextView = view.findViewById(R.id.fragment_notelist_rv_item_notethree)
    }
}
