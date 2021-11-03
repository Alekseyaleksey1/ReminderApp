package com.reminderapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reminderapp.mvp.data.entity.Day

class UpdateViewModel : ViewModel() {
    private val _listOfNotedWeekDays: MutableLiveData<List<Day>> = MutableLiveData()
    val listOfNotedWeekDays: LiveData<List<Day>>
        get() {
            return _listOfNotedWeekDays
        }

    private val _positionToShowDetailed: MutableLiveData<Int> = MutableLiveData()
    val positionToShowDetailed: LiveData<Int>
        get() {
            return _positionToShowDetailed
        }

    fun updateListOfNotedWeekDays(listOfNotedWeekDays: List<Day>) {
        this._listOfNotedWeekDays.value = listOfNotedWeekDays
    }

    fun updatePositionToShowDetailed(positionToShowDetailed: Int) {
        this._positionToShowDetailed.value = positionToShowDetailed
    }
}