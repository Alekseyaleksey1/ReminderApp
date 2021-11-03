package com.reminderapp.ui.activity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.R
import com.reminderapp.mvp.data.DateWorker
import java.util.Date
import java.util.Locale

class DetailedAdapter(context: Context, var allData: List<Note>) : RecyclerView.Adapter<DetailedAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var callback: ItemLongClickedCallback? = null

    /*private fun getAllNoteData(): List<Note> = allData

    fun setAllNoteData(allData: List<Note>) { //todo убарть сеттер
        this.allData = allData
    }*/


    interface ItemLongClickedCallback {
        fun onItemLongClicked(note: Note)
    }

    fun registerForItemLongClickedCallback(callback: ItemLongClickedCallback) {// todo убрать геттер
        this.callback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) = ViewHolder((inflater.inflate(R.layout.fragment_detailed_rv_item, viewGroup, false)) as View)

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        val note: Note = allData[i]
        val noteText: String = note.noteText
        val date: Date = note.noteDate
        val timeToShow: String = String.format(Locale.US, "%02d:%02d", Integer.valueOf(DateWorker.getHour(date)), Integer.valueOf(DateWorker.getMinute(date)))
        viewHolder.tvNoteTime.text = timeToShow
        viewHolder.tvNoteText.text = noteText

        viewHolder.container.setOnLongClickListener {
            callback!!.onItemLongClicked(allData[i])
            true
        }
    }

    override fun getItemCount() = allData.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: LinearLayout = itemView.findViewById(R.id.fragment_detailed_item_ll)
        val tvNoteTime: TextView = itemView.findViewById(R.id.fragment_detailed_tv_notetime)
        val tvNoteText: TextView = itemView.findViewById(R.id.fragment_detailed_tv_notetext)

    }
}


