package com.sdov.plantillamobile.data.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.ingoskr.template_oficial.R
import com.ingoskr.template_oficial.Utilidades.Constantes

class firebaseInit {

    private fun inicializarProyectoFirebase(context: Context): FirebaseApp {
        val apiKey = context.getString(R.string.api_key)
        val applicationId = context.getString(R.string.application_id)
        val projectId = context.getString(R.string.project_id)

        val options = FirebaseOptions.Builder()
            .setApiKey(apiKey)
            .setApplicationId(applicationId)
            .setProjectId(projectId)
            .build()
        FirebaseApp.initializeApp(context, options, Constantes.ID_COORDI)
        return FirebaseApp.getInstance(Constantes.ID_COORDI)
    }
}