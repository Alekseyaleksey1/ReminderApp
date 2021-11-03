package com.reminderapp.ui.navigation

import androidx.navigation.NavController
import com.reminderapp.mvp.contract.ScheduleContract

class ScheduleRouter(navController: NavController) : BaseRouter(navController),
    ScheduleContract.Router