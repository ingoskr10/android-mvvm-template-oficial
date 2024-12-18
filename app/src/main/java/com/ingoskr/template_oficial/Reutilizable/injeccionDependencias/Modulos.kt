package com.ingoskr.template_oficial.Reutilizable.injeccionDependencias

import SendVolley
import android.content.Context
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.ingoskr.template_oficial.Implementaciones.Firebase.firebaseAuth
import com.ingoskr.template_oficial.Presentacion.Inicio.viewmodel.InicioSesionViewModel
import com.ingoskr.template_oficial.Repositorios.UsuarioRepositorio
import com.ingoskr.template_oficial.Reutilizable.room.BaseDatos
import com.sdov.plantillamobile.data.firebase.firebaseInit
import com.sdov.plantillamobile.data.firebase.firebaseService
import com.sdov.plantillamobile.utils.CustomAlertas
import com.sdov.plantillamobile.utils.CustomDates
import com.sdov.plantillamobile.utils.CustomPermisos
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloRed = module {
    single { SendVolley(androidContext()) }
}
val firebaseModulo = module {
    single { firebaseService(get()) }
    single { firebaseAuth(FirebaseAuth.getInstance(), get()) }
    single { firebaseInit() }
}

// Módulo de repositorios
val moduloRepositorios = module {
    single { UsuarioRepositorio(get(), get()) }
}

// Módulo de ViewModels
val moduloViewModels = module {
    viewModel { InicioSesionViewModel(get()) }
}

val proveerBaseDatos = module {
    single {
        Room.databaseBuilder(
            get(),
            BaseDatos::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<BaseDatos>().globalDao() }
    single { get<BaseDatos>().usuarioDao() }
}

val customModulo = module {
    single { CustomDates() }
    single { CustomAlertas() }
    single { CustomPermisos() }
}