package com.reminderapp.ui.navigation

import androidx.navigation.NavController
import com.reminderapp.mvp.contract.NoteListContract

class NoteListRouter(navController: NavController) : BaseRouter(navController), NoteListContract.Router