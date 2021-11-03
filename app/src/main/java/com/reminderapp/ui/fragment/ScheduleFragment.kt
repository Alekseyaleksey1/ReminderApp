package com.reminderapp.ui.fragment

import com.reminderapp.mvp.contract.DetailedInfoContract

class ParentFragment: BaseFragment<DetailedInfoContract.View, DetailedInfoContract.Presenter, DetailedInfoContract.Router>(),
    DetailedInfoContract.View, {
}