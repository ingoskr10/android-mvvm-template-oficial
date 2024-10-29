package com.ingoskr.template_oficial.Aplicacion

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ingoskr.template_oficial.reutilizable.injeccionDependencias.moduloBaseDatos
import com.ingoskr.template_oficial.reutilizable.injeccionDependencias.moduloRed
import com.ingoskr.template_oficial.reutilizable.injeccionDependencias.moduloRepositorios
import com.ingoskr.template_oficial.reutilizable.injeccionDependencias.moduloViewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MiAplicacion : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        inicializarKoin()
        //ConfiguracionRemota.inicializar()
    }

    private fun inicializarKoin() {
        startKoin {
            androidContext(this@MiAplicacion)
            modules(
//                moduloRed,
                moduloBaseDatos,
                moduloRepositorios,
//                moduloViewModels
            )
        }
    }
}