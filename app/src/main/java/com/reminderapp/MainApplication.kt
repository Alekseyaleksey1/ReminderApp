package com.reminderapp

import android.app.Application
import android.content.Context

//todo сделать активити фрагментами
//todo сделать фрагмент загрузки
//todo сразу выделять текущий день в вертикальной ориентации и открывать заметки на текущий день в горизонтальной
//todo
//todo
//todo

class Application : Application() {//todo manifest

    init {
        instance = this
    }

    companion object {
        private var instance: com.reminderapp.Application? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}