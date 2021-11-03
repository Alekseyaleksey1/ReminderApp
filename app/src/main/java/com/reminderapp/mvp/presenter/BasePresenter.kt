package com.reminderapp.mvp.presenter

import com.reminderapp.mvp.contract.BaseContract

abstract class BasePresenter<
        V : BaseContract.View,
        R : BaseContract.Router>(protected val router: R) : BaseContract.Presenter<V> {

    protected var view: V? = null

    override fun onAttachView(view: V) {
        this.view = view
    }

    override fun onDetachView() {
        view = null
    }

    override fun onBackPressed() {
        router.back()
    }
}