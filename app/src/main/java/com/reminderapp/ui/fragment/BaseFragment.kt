package com.reminderapp.ui.fragment

import androidx.fragment.app.Fragment
import com.reminderapp.mvp.contract.BaseContract

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    Fragment(), BaseContract.View {

    protected abstract val presenter: P

    companion object {
        private const val FRAGMENT_TAG = "progressFragment"
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttachView(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetachView()
    }

    override fun showProgress() {
        childFragmentManager.let {
            ProgressFragment().show(it, FRAGMENT_TAG)
        }
    }

    override fun hideProgress() {
        childFragmentManager.let {
            (it.findFragmentByTag(FRAGMENT_TAG) as? ProgressFragment)?.dismiss()
        }
    }
}