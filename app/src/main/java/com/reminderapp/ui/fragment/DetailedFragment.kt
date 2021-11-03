package com.reminderapp.ui.fragment

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.reminderapp.R
import com.reminderapp.extensions.getDayName
import com.reminderapp.extensions.getDayOfMonth
import com.reminderapp.extensions.getMonthName
import com.reminderapp.mvp.contract.DetailedContract
import com.reminderapp.mvp.contract.HostContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.mvp.presenter.DetailedPresenter
import java.util.*
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.ui.navigation.DetailedRouter
import com.reminderapp.ui.viewmodel.UpdateViewModel

class DetailedFragment :
    BaseFragment<DetailedContract.View, DetailedContract.Presenter>(),
    DetailedContract.View,
    DetailedAdapter.ItemClickedCallback {

    override lateinit var presenter: DetailedPresenter
    private lateinit var detailedAdapter: DetailedAdapter
    private lateinit var dayToShow: Day
    private lateinit var dateInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)
        dateInfo = view.findViewById(R.id.fragment_detailed_tv_dateinfo)
        presenter = DetailedPresenter(NoteRepository(), DetailedRouter(findNavController()))
        val detailedRecyclerView: RecyclerView =
            view.findViewById(R.id.fragment_detailed_rv_allnotes)
        detailedAdapter =
            DetailedAdapter(listOf()).also { it.registerForItemLongClickedCallback(this) }

        detailedRecyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(DividerItemDecoration(requireContext(), ClipDrawable.HORIZONTAL))
            it.adapter = detailedAdapter
        }

        ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).listOfNotedWeekDays.observe(
            viewLifecycleOwner
        ) {
            setData()
        }

        ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).positionToShowDetailed.observe(
            viewLifecycleOwner
        ) {
            setData()
        }
//        setFragmentResultListener("key") { _, bundle ->
//            val noteToEdit: Note = Gson().fromJson(
//                bundle.getString("noteKey"),
//                Note::class.java
//            )
//            removeNoteFromUi(noteToEdit)
//        }
        return view
    }

    override fun removeNoteFromUi(noteToRemove: Note) {
        val listOfNotedWeekDays =
            ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).listOfNotedWeekDays.value!!
        for (day in listOfNotedWeekDays) {
            day.notesOfDay.remove(noteToRemove)
        }
        ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java)
            .updateListOfNotedWeekDays(listOfNotedWeekDays)
    }

    override fun onItemDeleteClicked(noteToDelete: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fragment_detailed_title))
            .setMessage(getString(R.string.fragment_detailed_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.fragment_detailed_btn_positivetext)) { _, _ ->
                presenter.onDeleteNoteFromDatabase(noteToDelete)
            }
            .setNegativeButton(getString(R.string.fragment_detailed_btn_negativetext)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    override fun onItemEditClicked(noteToEdit: Note) {
        (activity as HostContract.View).clickEditNote(noteToEdit)
    }

    private fun setData() {
        dayToShow =
            ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java).listOfNotedWeekDays.value!![ViewModelProvider(
                requireActivity()
            ).get(UpdateViewModel::class.java).positionToShowDetailed.value ?: 0]
        detailedAdapter.allData = dayToShow.notesOfDay
        detailedAdapter.notifyDataSetChanged()
        dateInfo.text =
            String.format(
                Locale.US,
                getString(R.string.format_shortdatedayname),
                dayToShow.dateOfDay.getDayOfMonth(),
                dayToShow.dateOfDay.getMonthName(),
                dayToShow.dateOfDay.getDayName()
            )
    }
}