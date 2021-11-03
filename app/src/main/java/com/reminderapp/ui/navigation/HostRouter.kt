package com.reminderapp.ui.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.gson.GsonBuilder
import com.reminderapp.R
import com.reminderapp.mvp.contract.HostContract
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.util.Constants

class HostRouter(navController: NavController) : BaseRouter(navController), HostContract.Router {

    override fun showNewNoteScreen(noteToEdit: Note?) {
        navController.navigate(
            R.id.newNoteFragment,
            when (noteToEdit) {
                null -> null
                else -> Bundle().apply {
                    putString(
                        Constants.NOTE_TO_EDIT_KEY,
                        GsonBuilder().create().toJson(noteToEdit)
                    )
                }
            },
            NavOptions.Builder()
                .setEnterAnim(R.anim.anim_slidefromtop)
                .setExitAnim(R.anim.anim_fadeout)
                .setPopEnterAnim(R.anim.anim_fadeoutreversed)
                .setPopExitAnim(R.anim.anim_slidetotop)
                .build()
        )
    }

    override fun showNoteDetailedScreen() {
        navController.navigate(
            R.id.detailedFragment,
            null,
            NavOptions.Builder()
                .setEnterAnim(R.anim.anim_slidefromright)
                .setExitAnim(R.anim.anim_slidetoleft)
                .setPopEnterAnim(R.anim.anim_slidefromleft)
                .setPopExitAnim(R.anim.anim_slidetoright)
                .setLaunchSingleTop(true)
                .build()
        )
    }
}