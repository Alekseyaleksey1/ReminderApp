package com.reminderapp.ui.navigation

import androidx.navigation.NavController
import com.reminderapp.mvp.contract.NewNoteContract

class NewNoteRouter(navController: NavController): BaseRouter(navController), NewNoteContract.Router