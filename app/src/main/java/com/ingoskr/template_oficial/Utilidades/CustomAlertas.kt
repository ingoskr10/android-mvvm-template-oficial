package com.sdov.plantillamobile.utils

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomAlertas {

    fun alertPositiveActionStyle(
        title: String,
        message: String,
        activity: Activity,
        style: Int,
        iconRes: Int? = null,
        positiveAction: () -> Unit,
        cancelAction: (() -> Unit)? = null
    ) {
        if (!activity.isFinishing) {
            activity.runOnUiThread {
                val dialogBuilder = createDialogBuilder(activity, title, message, style, iconRes)
                    .setPositiveButton("Aceptar") { dialog, _ ->
                        positiveAction()
                        dialog.dismiss()
                    }
                cancelAction?.let {
                    dialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
                        it()
                        dialog.dismiss()
                    }
                }

                dialogBuilder.create().show()
            }
        }
    }

    private fun createDialogBuilder(
        activity: Activity,
        title: String,
        message: String,
        style: Int,
        iconRes: Int?
    ): MaterialAlertDialogBuilder {
        val dialogBuilder = MaterialAlertDialogBuilder(activity, style)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
        iconRes?.let { dialogBuilder.setIcon(it) }

        return dialogBuilder
    }
}