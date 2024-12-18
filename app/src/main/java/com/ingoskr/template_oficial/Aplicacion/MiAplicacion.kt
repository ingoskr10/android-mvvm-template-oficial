package com.ingoskr.template_oficial.Aplicacion

import androidx.multidex.MultiDexApplication
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.customModulo
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.firebaseModulo
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.moduloRed
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.moduloRepositorios
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.moduloViewModels
import com.ingoskr.template_oficial.Reutilizable.injeccionDependencias.proveerBaseDatos
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MiAplicacion : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        inicializarKoin()
    }

    private fun inicializarKoin() {
        startKoin {
            androidContext(this@MiAplicacion)
            modules(
                proveerBaseDatos,
                moduloRepositorios,
                customModulo,
                firebaseModulo,
                moduloRed,
                moduloViewModels
            )
        }
    }
}