package com.reminderapp.mvp.presenter

import android.content.Intent
import com.reminderapp.Application
import com.reminderapp.extensions.addToCompositeDisposable
import com.reminderapp.mvp.contract.ScheduleContract
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Day
import com.reminderapp.ui.activity.AddNoteActivity
import com.reminderapp.ui.navigation.ScheduleRouter
import io.reactivex.android.schedulers.AndroidSchedulers

class SchedulePresenter(router: ScheduleRouter, private val repository: NoteRepository) : ScheduleContract.Presenter, BasePresenter<ScheduleContract.View, ScheduleContract.Router>(router) {//todo должен принимать роутер и репозиторий

    fun onUIReady() = getNotesFromDatabase()

    private fun getNotesFromDatabase() =
            repository.getNotes()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { view?.showProgress() }
                    .doFinally { view?.hideProgress() }
                    .subscribe { allNotes ->
                        val listOfNotedWeekDays: List<Day> = repository.fetchDataToShow(allNotes)
                        view!!.setDataToList(listOfNotedWeekDays)
                    }.addToCompositeDisposable(compositeDisposable)

    override fun onClickAddNewNote() = router.showAddNoteScreen()

    /*override fun clickAddNewNote()
            = startActivity(Intent(Application.applicationContext(), AddNoteActivity::class.java))//todo ubrat' v presenter a tam v router*/
}