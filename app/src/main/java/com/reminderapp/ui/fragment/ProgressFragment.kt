package com.reminderapp.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.reminderapp.R

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_progress_dialog, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?) =
            super.onCreateDialog(savedInstanceState).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
}