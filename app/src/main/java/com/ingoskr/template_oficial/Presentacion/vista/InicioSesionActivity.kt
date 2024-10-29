package com.ingoskr.template_oficial.Presentacion.vista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingoskr.template_oficial.Presentacion.adaptadores.UsuarioAdapter
import com.ingoskr.template_oficial.Presentacion.viewmodel.InicioSesionViewModel
import com.ingoskr.template_oficial.databinding.ActivityInicioSesionBinding
import com.ingoskr.template_oficial.repositorios.UsuarioRepositorio
import com.ingoskr.template_oficial.reutilizable.room.entidades.Usuario
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioSesionBinding
//    private val viewModel: InicioSesionViewModel by viewModel()
    private val usuarioRepositorio: UsuarioRepositorio by inject()
    private val usuarioAdapter = UsuarioAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuarioRepositorio.obtenerTodosLosUsuarios()
//        configurarRecyclerView()
//        configurarObservadores()
//        configurarEventos()
    }

//    private fun configurarRecyclerView() {
//        binding.rvUsuarios.apply {
//            layoutManager = LinearLayoutManager(this@InicioSesionActivity)
//            adapter = usuarioAdapter
//        }
//    }
//
//    private fun configurarObservadores() {
//        viewModel.estadoInicioSesion.observe(this) { exitoso ->
//            if (exitoso) {
//                // Aquí podrías mostrar un mensaje de éxito
//            }
//        }
//
//        viewModel.usuarios.observe(this) { usuarios ->
//            val user = usuarios as List<Usuario>
//            usuarioAdapter.submitList(user)
//        }
//    }
//
//    private fun configurarEventos() {
//        binding.btnIniciarSesion.setOnClickListener {
//            val correo = binding.etCorreo.text.toString()
//            val contraseña = binding.etContraseA.text.toString()
//            viewModel.iniciarSesion(correo, contraseña)
//        }
//    }
}