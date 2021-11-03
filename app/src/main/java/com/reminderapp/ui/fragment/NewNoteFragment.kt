package com.reminderapp.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.reminderapp.R
import com.reminderapp.extensions.*
import com.reminderapp.mvp.contract.NewNoteContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.presenter.NewNotePresenter
import com.reminderapp.ui.navigation.NewNoteRouter
import com.reminderapp.ui.viewmodel.UpdateViewModel
import com.reminderapp.util.Constants
import java.util.*

class NewNoteFragment :
    BaseFragment<NewNoteContract.View, NewNoteContract.Presenter>(),
    NewNoteContract.View, View.OnClickListener {

    private var calendarDateToAdd: Calendar =
        Calendar.getInstance()
    override lateinit var presenter: NewNotePresenter
    private lateinit var edNoteText: EditText
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private var isNoteToEdit: Boolean = false
    private lateinit var startingNoteToEdit: Note


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_newnote, container, false)
        edNoteText = view.findViewById(R.id.fragment_newnote_et_notetext)
        tvDate = view.findViewById(R.id.fragment_newnote_tv_date)
        tvTime = view.findViewById(R.id.fragment_newnote_tv_time)

        tvDate.setOnClickListener(this)
        tvTime.setOnClickListener(this)
        presenter = NewNotePresenter(NoteRepository(), NewNoteRouter(findNavController()))

        if (arguments != null) {
            isNoteToEdit = true
            startingNoteToEdit = GsonBuilder().create()
                .fromJson(
                    requireArguments().getString(Constants.NOTE_TO_EDIT_KEY),
                    Note::class.java
                )

            calendarDateToAdd.set(
                startingNoteToEdit.noteDate.getYearNumber(),
                startingNoteToEdit.noteDate.getMonthNumber(),
                startingNoteToEdit.noteDate.getDayOfMonth(),
                startingNoteToEdit.noteDate.getHour(),
                startingNoteToEdit.noteDate.getMinute(),
                0
            )

            tvDate.text = String.format(
                Locale.US,
                getString(R.string.format_fulldate),
                Calendar.getInstance().apply { time = startingNoteToEdit.noteDate }
                    .get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().apply { time = startingNoteToEdit.noteDate }
                    .get(Calendar.MONTH) + 1,
                Calendar.getInstance().apply { time = startingNoteToEdit.noteDate }
                    .get(Calendar.YEAR)
            )

            tvTime.text = String.format(
                Locale.US,
                getString(R.string.format_time),
                startingNoteToEdit.noteDate.getHour(),
                startingNoteToEdit.noteDate.getMinute()
            )

            edNoteText.setText(startingNoteToEdit.noteText)
        } else {

            ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).positionToShowDetailed.observe(
                viewLifecycleOwner
            ) {
                calendarDateToAdd.time =
                    ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).listOfNotedWeekDays.value?.get(
                        ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).positionToShowDetailed.value!!
                    )!!.dateOfDay

                tvDate.text = String.format(
                    Locale.US,
                    getString(R.string.format_fulldate),
                    calendarDateToAdd.get(Calendar.DAY_OF_MONTH),
                    calendarDateToAdd.get(Calendar.MONTH) + 1,
                    calendarDateToAdd.get(Calendar.YEAR)
                )
            }

            tvDate.text = String.format(
                Locale.US,
                getString(R.string.format_fulldate),
                calendarDateToAdd.get(Calendar.DAY_OF_MONTH),
                calendarDateToAdd.get(Calendar.MONTH) + 1,
                calendarDateToAdd.get(Calendar.YEAR)
            )
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_newnotefragment, menu.apply { clear() })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_savenewnote -> saveNewNote()
            R.id.action_cancelnewnote -> presenter.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_newnote_tv_date -> pickDate()
            R.id.fragment_newnote_tv_time -> pickTime()
        }
    }

    private fun pickDate() {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendarDateToAdd.set(year, month, dayOfMonth)
                tvDate.text = String.format(
                    Locale.US,
                    getString(R.string.format_fulldate),
                    dayOfMonth,
                    month + 1,
                    year
                )
            },
            calendarDateToAdd.get(Calendar.YEAR),
            calendarDateToAdd.get(Calendar.MONTH),
            calendarDateToAdd.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = Calendar.getInstance().timeInMillis
        }.show()
    }

    private fun pickTime() {
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendarDateToAdd.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendarDateToAdd.set(Calendar.MINUTE, minute)
                calendarDateToAdd.set(Calendar.SECOND, 0)
                tvTime.text = String.format(
                    Locale.US,
                    getString(R.string.format_time),
                    hourOfDay,
                    minute
                )
            },
            calendarDateToAdd.get(Calendar.HOUR_OF_DAY),
            calendarDateToAdd.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun saveNewNote() {
        if (edNoteText.text.toString().isBlank()
            || tvDate.text.toString().isEmpty()
            || tvTime.text.toString().isEmpty()
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_invaliddata),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (isNoteToEdit) {
                presenter.onSaveEditedNoteClicked(
                    Note(
                        calendarDateToAdd.time,
                        edNoteText.text.toString().trim()
                    ).apply {
                        id = startingNoteToEdit.id
                    }
                )
            } else {
                presenter.onSaveNewNoteClicked(
                    Note(
                        calendarDateToAdd.time,
                        edNoteText.text.toString().trim()
                    )
                )
            }
        }
    }

    override fun updateUiData(noteToSave: Note) {
        val listOfNotedWeekDays = ViewModelProvider(requireActivity())
            .get(UpdateViewModel::class.java)
            .listOfNotedWeekDays.value!!

        for (day in listOfNotedWeekDays) {
            if (isNoteToEdit) {
                day.notesOfDay.remove(day.notesOfDay.find { it.id == noteToSave.id })
            }
            val dayDateCalc: Calendar = Calendar.getInstance().apply { time = day.dateOfDay }
            val noteDateCalc = Calendar.getInstance().apply { time = noteToSave.noteDate }
            if (dayDateCalc.get(Calendar.YEAR) == noteDateCalc.get(Calendar.YEAR) &&
                dayDateCalc.get(Calendar.MONTH) == noteDateCalc.get(Calendar.MONTH) &&
                dayDateCalc.get(Calendar.DAY_OF_MONTH) == noteDateCalc.get(Calendar.DAY_OF_MONTH)
            ) {

                day.notesOfDay.add(noteToSave)
                day.notesOfDay.sortBy { it.noteDate }
            }
        }
        ViewModelProvider(requireActivity())
            .get(UpdateViewModel::class.java).updateListOfNotedWeekDays(listOfNotedWeekDays)
    }
}