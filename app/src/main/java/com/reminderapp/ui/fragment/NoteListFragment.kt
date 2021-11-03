package com.reminderapp.ui.fragment

import android.content.res.Configuration
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reminderapp.R
import com.reminderapp.mvp.contract.HostContract
import com.reminderapp.mvp.contract.NoteListContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.mvp.presenter.NoteListPresenter
import com.reminderapp.ui.navigation.NoteListRouter
import com.reminderapp.ui.observer.NoteListLifecycleObserver
import com.reminderapp.ui.viewmodel.UpdateViewModel

class NoteListFragment :
    BaseFragment<NoteListContract.View, NoteListContract.Presenter>(),
    NoteListContract.View, NoteListAdapter.ItemClickedCallback {

    override lateinit var presenter: NoteListPresenter
    private lateinit var scheduleAdapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notelist, container, false)
        val scheduleRecyclerView: RecyclerView = view.findViewById(R.id.fragment_notelist_rv)
        scheduleAdapter =
            NoteListAdapter(listOf()).also {
                it.registerForItemClickedCallback(this)
            }

        scheduleRecyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(DividerItemDecoration(requireContext(), ClipDrawable.HORIZONTAL))
            it.adapter = scheduleAdapter
        }
        presenter = NoteListPresenter(NoteRepository(), NoteListRouter(findNavController()))

        viewLifecycleOwner.lifecycle.addObserver(
            NoteListLifecycleObserver(presenter)
        )
        ViewModelProvider(requireActivity())
            .get(UpdateViewModel::class.java)
            .listOfNotedWeekDays.observe(viewLifecycleOwner) {
                scheduleAdapter.listOfWeekDays = it
                scheduleAdapter.notifyDataSetChanged()
            }
        return view
    }

    override fun updateNotesOnUi(listOfNotedWeekDays: List<Day>) {
        ViewModelProvider(requireActivity())
            .get(UpdateViewModel::class.java)
            .updateListOfNotedWeekDays(listOfNotedWeekDays)
    }

    override fun onItemClicked(position: Int) {
        ViewModelProvider(requireActivity())
            .get(UpdateViewModel::class.java)
            .updatePositionToShowDetailed(position)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            (activity as HostContract.View).clickNoteDetailed()
        }
    }
}