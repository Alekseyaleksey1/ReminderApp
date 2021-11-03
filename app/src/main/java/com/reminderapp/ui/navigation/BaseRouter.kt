
package com.reminderapp.ui.navigation

import androidx.navigation.NavController
import com.reminderapp.mvp.contract.BaseContract

abstract class BaseRouter(protected val navController: NavController): BaseContract.Router {
    override fun back() {
        navController.popBackStack()
    }
}
