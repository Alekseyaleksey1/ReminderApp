package com.reminderapp.ui.fragment

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import com.reminderapp.MainApplication
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.R
import com.reminderapp.extensions.getHour
import com.reminderapp.extensions.getMinute
import java.util.Locale

class DetailedAdapter(var allData: List<Note>) :
    RecyclerView.Adapter<DetailedAdapter.ViewHolder>() {

    private lateinit var callback: ItemClickedCallback

    interface ItemClickedCallback {
        fun onItemEditClicked(noteToEdit: Note)
        fun onItemDeleteClicked(noteToDelete: Note)
    }

    fun registerForItemLongClickedCallback(callback: ItemClickedCallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        ViewHolder(
            (LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.fragment_detailed_rv_item, viewGroup, false)) as View
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvNoteTime.text = String.format(
            Locale.US,
            MainApplication.applicationContext().getString(R.string.format_time),
            allData[i].noteDate.getHour(),
            allData[i].noteDate.getMinute()
        )
        viewHolder.tvNoteText.text = allData[i].noteText
    }

    override fun getItemCount() = allData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        PopupMenu.OnMenuItemClickListener,
        View.OnLongClickListener {
        init {
            view.setOnLongClickListener(this)
        }

        val container: LinearLayout =
            itemView.findViewById(R.id.fragment_detailed_item_ll)
        val tvNoteTime: TextView = itemView.findViewById(R.id.fragment_detailed_tv_notetime)
        val tvNoteText: TextView = itemView.findViewById(R.id.fragment_detailed_tv_notetext)

        override fun onLongClick(v: View?): Boolean {
            PopupMenu(v?.context, v).apply {
                inflate(R.menu.popupmenu_detailedfragment_listitem)
                setOnMenuItemClickListener(this@ViewHolder)
                show()
            }
            return true
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.popupmenu_detailedfragment_listitem_edit_item -> {
                    callback.onItemEditClicked(allData[bindingAdapterPosition])
                }
                R.id.popupmenu_detailedfragment_listitem_delete_item -> {
                    callback.onItemDeleteClicked(allData[bindingAdapterPosition])
                }
            }
            return true
        }
    }
}