package com.reminderapp.ui.fragment

import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reminderapp.Application
import com.reminderapp.R
import com.reminderapp.mvp.contract.ScheduleContract
import com.reminderapp.mvp.data.DateWorker
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.presenter.SchedulePresenter
import com.reminderapp.ui.activity.AddNoteActivity
import com.reminderapp.ui.activity.DAY_KEY
import com.reminderapp.ui.activity.DetailedActivity
import com.reminderapp.ui.activity.ScheduleAdapter
import com.reminderapp.ui.navigation.NotesRouter
import java.util.*

class NotesListFragment() :
    BaseFragment<ScheduleContract.View, ScheduleContract.Presenter, ScheduleContract.Router>(),
    ScheduleContract.View, ScheduleAdapter.ItemClickedCallback  {

    override val presenter: SchedulePresenter =
        SchedulePresenter(NotesRouter(), NoteRepository())
    private var scheduleAdapter: ScheduleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_schedule, container, false)

        val myToolbar: Toolbar = view.findViewById(R.id.activity_schedule_tb)
        (activity as AppCompatActivity).setSupportActionBar(myToolbar)//todo так можно?

        val listOfWeekDays = ArrayList<Day>()
        val dateOfWeekDays = DateWorker.getWeekDates()
        for (dateDayOfWeek in dateOfWeekDays) {
            listOfWeekDays.add(Day(dateDayOfWeek, ArrayList<Note>()))
        }

        val scheduleRecyclerView: RecyclerView = view.findViewById(R.id.activity_schedule_rv)
        scheduleRecyclerView.layoutManager = LinearLayoutManager(activity)//todo так можно?
        val itemDecor =  DividerItemDecoration(activity, ClipDrawable.HORIZONTAL)//todo так можно?
        scheduleRecyclerView.addItemDecoration(itemDecor)
        scheduleAdapter = ScheduleAdapter(Application.applicationContext(), listOfWeekDays)////todo так можно?
        scheduleAdapter!!.registerForItemClickedCallback(this)
        scheduleRecyclerView.adapter = scheduleAdapter

        val btnAddNewNote = view.findViewById<Button>(R.id.activity_schedule_btn_addnote)
        btnAddNewNote.setOnClickListener{clickAddNewNote()}
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onUIReady()//todo отвязать от активити это действие в презентере
    }

    override fun setDataToList(listToShow: List<Day>) {
        scheduleAdapter!!.setListOfWeekDays(listToShow)//todo много нулов
        scheduleAdapter!!.notifyDataSetChanged()
    }

    override fun clickAddNewNote()
        = startActivity(Intent(this, AddNoteActivity::class.java))//todo ubrat' v presenter a tam v router


    override fun onItemClicked(dateOfClickedDay: Date)  =//todo пойти в активити и сказать запустить  в другом фрагменте детальную инфу по дате
        startActivity(Intent(this, DetailedActivity::class.java)
            .also { it.putExtra(DAY_KEY, dateOfClickedDay.toString()) })
}