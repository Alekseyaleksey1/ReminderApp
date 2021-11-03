package com.reminderapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.reminderapp.mvp.contract.BaseContract
import com.reminderapp.ui.fragment.ProgressFragment

abstract class BaseActivity<
        V : BaseContract.View,
        P : BaseContract.Presenter<V>> : AppCompatActivity(), BaseContract.View {

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
        ProgressFragment().show(supportFragmentManager, FRAGMENT_TAG)
    }

    override fun hideProgress() {
       (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as? DialogFragment)?.dismiss()
    }
}