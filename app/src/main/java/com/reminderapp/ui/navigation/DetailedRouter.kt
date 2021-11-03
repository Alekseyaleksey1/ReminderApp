package com.reminderapp.ui.navigation

import androidx.navigation.NavController
import com.reminderapp.mvp.contract.DetailedContract

class DetailedRouter(navController: NavController): BaseRouter(navController), DetailedContract.Router