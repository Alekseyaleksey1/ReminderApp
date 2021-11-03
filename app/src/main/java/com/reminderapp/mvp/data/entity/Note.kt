package com.reminderapp.mvp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class Note(val noteDate: Date, var noteText: String){
   @PrimaryKey(autoGenerate = true)
   var id = 0
}