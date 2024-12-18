package com.sdov.plantillamobile.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class CustomDates {

    fun obtenerFechaHora(format: String): String {
        val date = Timestamp.now().toDate()
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(date)
    }

}