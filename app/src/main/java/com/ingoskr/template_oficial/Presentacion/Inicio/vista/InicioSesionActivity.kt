package com.ingoskr.template_oficial.Presentacion.Inicio.vista

import SendVolley
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.ingoskr.template_oficial.Repositorios.UsuarioRepositorio
import com.ingoskr.template_oficial.Reutilizable.Volley.VolleyCallback
import com.ingoskr.template_oficial.databinding.ActivityInicioSesionBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class InicioSesionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioSesionBinding
    private val usuarioRepositorio: UsuarioRepositorio by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuarioRepositorio.consultarDatos()
    }

}