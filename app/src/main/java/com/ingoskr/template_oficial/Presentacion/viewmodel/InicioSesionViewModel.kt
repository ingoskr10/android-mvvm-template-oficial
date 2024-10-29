package com.ingoskr.template_oficial.Presentacion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingoskr.template_oficial.repositorios.UsuarioRepositorio
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class InicioSesionViewModel(
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel(), KoinComponent {

    private val _estadoInicioSesion = MutableLiveData<Boolean>()
    val estadoInicioSesion: LiveData<Boolean> = _estadoInicioSesion

    val usuarios = usuarioRepositorio.obtenerTodosLosUsuarios() as LiveData<*>

    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            usuarioRepositorio.iniciarSesion(correo, contrasena)
                .onSuccess { exito ->
                    _estadoInicioSesion.value = exito
                    if (exito) {
                        usuarioRepositorio.guardarUsuario(
                            correo = correo,
                            nombre = "Usuario ${System.currentTimeMillis()}"
                        )
                    }
                }
                .onFailure {

                }
        }
    }
}