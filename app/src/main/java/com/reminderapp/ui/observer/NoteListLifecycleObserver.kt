package com.reminderapp.ui.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.reminderapp.mvp.presenter.RepositoryPresenter

class NoteListLifecycleObserver(val presenter: RepositoryPresenter) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun uiReady() {
        presenter.onUIReady()
    }
}