package com.ingoskr.template_oficial.Presentacion.Inicio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ingoskr.template_oficial.Repositorios.UsuarioRepositorio

class InicioSesionViewModel(
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {

    private val _text = MutableLiveData("Hello, MVVM + Koin!")
    val text: LiveData<String> get() = _text
}