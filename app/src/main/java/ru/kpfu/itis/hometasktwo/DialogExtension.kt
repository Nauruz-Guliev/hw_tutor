package ru.kpfu.itis.hometasktwo

import android.app.AlertDialog
import androidx.fragment.app.Fragment

typealias Click = () -> Unit

fun Fragment.showDialog(
    title: String = "",
    message: String= "",
    positiveAction: Click = {},
    negativeAction: Click = {},
    neutralAction: Click = {},
) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("+") { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        .setNegativeButton("-") { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
        .setNeutralButton("o") { dialog, _ ->
            neutralAction()
            dialog.dismiss()
        }
        .show()
}
