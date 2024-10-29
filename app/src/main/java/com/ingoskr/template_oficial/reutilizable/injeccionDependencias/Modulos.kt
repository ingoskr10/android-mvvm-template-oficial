package com.ingoskr.template_oficial.reutilizable.injeccionDependencias

import android.content.Context
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.ingoskr.template_oficial.reutilizable.room.BaseDatos
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val moduloRed = module {
    single { proveerVolleyQueue(androidContext()) }
    single<ServicioApi> { ServicioApiImpl(get()) }
}

// Módulo de base de datos
val moduloBaseDatos = module {
    single { proveerBaseDatos(androidContext()) }
    single { get<BaseDatos>().usuarioDao() }
}

// Módulo de repositorios
val moduloRepositorios = module {
    single { UsuarioRepositorio(get(), get(), get()) }
}

// Módulo de ViewModels
val moduloViewModels = module {
    viewModel { InicioSesionViewModel(get()) }
}

private fun proveerVolleyQueue(context: Context): RequestQueue =
    Volley.newRequestQueue(context)

private fun proveerBaseDatos(context: Context): BaseDatos =
    Room.databaseBuilder(
        context,
        BaseDatos::class.java,
        "app-db"
    ).fallbackToDestructiveMigration()
        .build()