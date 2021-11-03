package com.reminderapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import java.util.Date
import com.reminderapp.R
import com.reminderapp.mvp.contract.ScheduleContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.presenter.SchedulePresenter
import com.reminderapp.ui.navigation.ScheduleRouter

const val DAY_KEY = "chosenDay"

class ScheduleActivity :
    BaseActivity<ScheduleContract.View, ScheduleContract.Presenter, ScheduleContract.Router>(),
    ScheduleContract.View/*, NoteListAdapter.ItemClickedCallback*/ {

    override val presenter: SchedulePresenter =
        SchedulePresenter(ScheduleRouter(), NoteRepository())
    //private var scheduleAdapter: ScheduleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        val myToolbar: Toolbar = findViewById(R.id.activity_schedule_tb)
        setSupportActionBar(myToolbar)

        //  val listOfWeekDays = ArrayList<Day>()
        //   val dateOfWeekDays = DateWorker.getWeekDates()
        //  for (dateDayOfWeek in dateOfWeekDays) {
        //      listOfWeekDays.add(Day(dateDayOfWeek, ArrayList<Note>()))
        //   }

        // val scheduleRecyclerView: RecyclerView = findViewById(R.id.activity_schedule_rv)
        //scheduleRecyclerView.layoutManager = LinearLayoutManager(this)
        // val itemDecor =  DividerItemDecoration(this, HORIZONTAL)
        // scheduleRecyclerView.addItemDecoration(itemDecor)
        // scheduleAdapter = ScheduleAdapter(this, listOfWeekDays)
        //  scheduleAdapter!!.registerForItemClickedCallback(this)
        //  scheduleRecyclerView.adapter = scheduleAdapter

        // val btnAddNewNote = findViewById<Button>(R.id.fragment_schedule_btn_addnote)
        // btnAddNewNote.setOnClickListener{clickAddNewNote()}
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scheduleactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addnewnote -> {
                presenter.onClickAddNewNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onResume() {
        super.onResume()
        presenter.onUIReady()//todo отвязать от активити это действие в презентере
    }*/

    override fun showDetailedInfo(date: Date) {
        presenter.onShowDetailedInfo(date)
    }


    /*override fun setDataToList(listToShow: List<Day>) {
        scheduleAdapter!!.setListOfWeekDays(listToShow)//todo много нулов
        scheduleAdapter!!.notifyDataSetChanged()
    }*/

   /* override fun clickAddNewNote(*//*view: View*//*) = startActivity(
        Intent(
            this,
            AddNoteActivity::class.java
        )
    )//todo ubrat' v presenter a tam v router*/

   /* override fun onItemClicked(dateOfClickedDay: Date) {
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_schedule_cv1, DetailedFragment(), "DETAILED_FRAGMENT")//todo должен делать роутер?
    }*/
    /*
        startActivity(Intent(this, DetailedActivity::class.java)
            .also { it.putExtra(DAY_KEY, dateOfClickedDay.toString()) })*/
}
