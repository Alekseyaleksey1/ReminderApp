package com.reminderapp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.reminderapp.R
import com.reminderapp.extensions.observeOnce
import com.reminderapp.mvp.contract.HostContract
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.presenter.HostPresenter
import com.reminderapp.ui.navigation.HostRouter
import com.reminderapp.ui.viewmodel.UpdateViewModel
import com.reminderapp.util.Constants

class HostActivity :
    BaseActivity<HostContract.View, HostContract.Presenter>(),
    HostContract.View {

    override lateinit var presenter: HostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        setSupportActionBar(findViewById(R.id.activity_host_tb))

        presenter = HostPresenter(
            HostRouter((supportFragmentManager.findFragmentById(R.id.activity_host_navfragment) as NavHostFragment).navController)
        )

        if (intent.getBooleanExtra(Constants.STARTED_FROM_RECEIVER_FLAG, false)
        ) {
            ViewModelProvider(this).get(UpdateViewModel::class.java).listOfNotedWeekDays.observeOnce(
                this,
                { presenter.onClickDetailedNote() })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hostactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addnewnote -> {
                clickAddNewNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun clickAddNewNote() {
        presenter.onClickAddNewNote()
    }

    override fun clickEditNote(noteToEdit: Note) {
        presenter.onClickEditNote(noteToEdit)
    }

    override fun clickNoteDetailed() {
        presenter.onClickDetailedNote()
    }
}