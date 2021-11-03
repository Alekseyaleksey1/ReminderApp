package com.reminderapp.mvp.contract

interface ScheduleContract {

    interface View : BaseContract.View

    interface Presenter: BaseContract.Presenter<View>

    interface Router: BaseContract.Router
}