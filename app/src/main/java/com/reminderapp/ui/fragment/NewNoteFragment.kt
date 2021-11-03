package com.reminderapp.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.reminderapp.Application
import com.reminderapp.R
import com.reminderapp.mvp.contract.AddNoteContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.presenter.AddNotePresenter
import com.reminderapp.ui.navigation.ScheduleRouter
import java.util.*

class AddNoteFragment :
    BaseFragment<AddNoteContract.View, AddNoteContract.Presenter, AddNoteContract.Router>(),
    AddNoteContract.View {

    override val presenter: AddNotePresenter = AddNotePresenter(NoteRepository(), ScheduleRouter())
    private var edNoteText: EditText? = null
    private var tvDateAndTime: TextView? = null
    private var yearToSet: Int? = null
    private var monthToSet: Int? = null
    private var dayToSet: Int? = null
    private var hourToSet: Int? = null
    private var minuteToSet: Int? = null
    private var dateToAdd: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addnote, container, false)
        val myToolbar: Toolbar = view.findViewById(R.id.fragment_newnote_tb)
        (activity as AppCompatActivity).setSupportActionBar(myToolbar)
        edNoteText = view.findViewById(R.id.fragment_newnote_et_notetext)
        tvDateAndTime = view.findViewById(R.id.fragment_newnote_tv_dateandtime)
        return view
    }

    override fun pickDate() {

        val calendarToday = Calendar.getInstance()
        val currentYear = calendarToday.get(Calendar.YEAR)
        val currentMonth = calendarToday.get(Calendar.MONTH)
        val currentDay = calendarToday.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            activity!!.applicationContext,
            { view, year, month, dayOfMonth ->//todo правильно ли так контекст получать?
                yearToSet = year
                monthToSet = month
                dayToSet = dayOfMonth
                pickTime()
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }

    override fun pickTime() {

        val calendarToday = Calendar.getInstance()
        val currentHour = calendarToday.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendarToday.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(activity!!.applicationContext, { _, hourOfDay, minute ->

                hourToSet = hourOfDay
                minuteToSet = minute

                val c2 = Calendar.getInstance()
                if ((c2.get(Calendar.DAY_OF_MONTH) == dayToSet) && ((hourOfDay < c2.get(Calendar.HOUR_OF_DAY) || (hourOfDay == c2.get(
                        Calendar.HOUR_OF_DAY
                    ) && minute <= (c2.get(Calendar.MINUTE))))
                            )
                ) {
                    Toast.makeText(
                        Application.applicationContext(),
                        getString(R.string.addnote_activity_toasterror_badtime),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val dateAndTime = String.format(
                        Locale.US,
                        "%02d:%02d %02d.%02d.%d ",
                        hourToSet,
                        minuteToSet,
                        dayToSet,
                        monthToSet,
                        yearToSet
                    )
                    tvDateAndTime?.text = dateAndTime
                    val calendar = Calendar.getInstance()
                    calendar.set(
                        yearToSet!!,
                        monthToSet!!,
                        dayToSet!!,
                        hourToSet!!,
                        minuteToSet!!,
                        0
                    )
                    dateToAdd = calendar.time
                }
            }, currentHour, currentMinute, true)
        timePickerDialog.show()
    }

    override fun saveNewNote(view: AddNoteContract.View) {

        if (edNoteText?.text.toString().isEmpty() || dateToAdd == null) {
            Toast.makeText(
                Application.applicationContext(),
                getString(R.string.addnote_activity_toasterror_nodata),
                Toast.LENGTH_SHORT
            ).show()//todo бросать эксептион NewNoteInfoException
        } else {
            presenter.onSaveNewNoteClicked(Note(dateToAdd!!, edNoteText?.text.toString()))
        }
    }

    fun setDateAndTime(view: View) {}//todo нажатие

    fun finishTheView()//todo wo eta?
    {
        finish();
    }
}