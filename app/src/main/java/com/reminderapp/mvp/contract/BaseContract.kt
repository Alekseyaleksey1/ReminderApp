package com.reminderapp.mvp.contract

interface BaseContract {

    interface View {
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter<T : View> {
        fun onAttachView(view: T)
        fun onDetachView()
        fun onBackPressed()
    }

    interface Router {
        fun back()
    }
}