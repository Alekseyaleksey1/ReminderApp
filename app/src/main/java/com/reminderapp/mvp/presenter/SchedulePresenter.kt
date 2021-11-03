package com.reminderapp.mvp.presenter

import com.reminderapp.mvp.contract.ScheduleContract

class SchedulePresenter(router: ScheduleContract.Router) :
    ScheduleContract.Presenter,
    BasePresenter<ScheduleContract.View, ScheduleContract.Router>(router)