package com.ingoskr.template_oficial.reutilizable.remote

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.ingoskr.template_oficial.BuildConfig

object ConfiguracionRemota {
//    private val remoteConfig = Firebase.remoteConfig
//
//    fun inicializar() {
//        val configSettings = remoteConfigSettings {
//            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 3600
//        }
//
//        remoteConfig.setConfigSettingsAsync(configSettings)
//        remoteConfig.setDefaultsAsync(mapOf(
//            "version_minima" to "1.0.0",
//            "mensaje_bienvenida" to "¡Bienvenido a MiApp!"
//        ))
//
//        remoteConfig.fetchAndActivate()
//    }
//
//    fun obtenerMensajeBienvenida(): String =
//        remoteConfig.getString("mensaje_bienvenida")
}