package com.ingoskr.template_oficial.reutilizable.injeccionDependencias

import android.content.Context
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.ingoskr.template_oficial.Presentacion.viewmodel.InicioSesionViewModel
import com.ingoskr.template_oficial.entidades.ServicioApi
import com.ingoskr.template_oficial.implementaciones.ServicioApiImpl
import com.ingoskr.template_oficial.repositorios.UsuarioRepositorio
import com.ingoskr.template_oficial.reutilizable.Volley.SendVolley
import com.ingoskr.template_oficial.reutilizable.room.BaseDatos
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduloRed = module {
    single { proveerVolleyQueue(androidContext()) }
    single<ServicioApi> { ServicioApiImpl(get()) }
    single { SendVolley(androidContext()) }
}

// Módulo de base de datos
val moduloBaseDatos = module {
    single { proveerBaseDatos(androidContext()) }
    single { get<BaseDatos>().usuarioDao() }
}

// Módulo de repositorios
val moduloRepositorios = module {
    factory { UsuarioRepositorio(get(), get(), get()) }
}

// Módulo de ViewModels
val moduloViewModels = module {
    factory { InicioSesionViewModel(get()) }
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