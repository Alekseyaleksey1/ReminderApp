package com.reminderapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reminderapp.R
import com.reminderapp.mvp.contract.ScheduleContract
import com.reminderapp.mvp.presenter.SchedulePresenter
import com.reminderapp.ui.navigation.ScheduleRouter

class ScheduleFragment :
    BaseFragment<ScheduleContract.View, ScheduleContract.Presenter>(),
    ScheduleContract.View {

    override lateinit var presenter: SchedulePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = SchedulePresenter(ScheduleRouter(findNavController()))
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }
}