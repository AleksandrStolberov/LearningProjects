package com.skillbox.fragment.viewpager

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.skillbox.fragment.R
import com.skillbox.fragment.withArguments

class FilterDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val checkedItem = arguments?.getBooleanArray(KEY_FILTER) ?: error("")
        val tags = arrayOf("Реки", "Горы", "Водопады", "Бухты")
        return AlertDialog.Builder(requireActivity())
            .setTitle("Тэги статей")
            .setMultiChoiceItems(tags,checkedItem ) { _, which, isChecked ->
                checkedItem[which] = isChecked
            }
            .setPositiveButton("Применить") { _, _ ->
                parentFragment.let { it as ItemFilter }.onItemFilter(checkedItem)
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss()}
            .create()
    }

    companion object {
        fun newInstanceDialog(filter: BooleanArray): DialogFragment {
            return FilterDialogFragment().withArguments {
                putBooleanArray(KEY_FILTER, filter)
            }
        }
        private const val KEY_FILTER = "filter"
    }

}