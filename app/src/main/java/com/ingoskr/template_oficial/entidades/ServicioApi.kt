package com.ingoskr.template_oficial.entidades

interface ServicioApi {
    suspend fun iniciarSesion(correo: String, contrasena: String): Result<Boolean>
    suspend fun obtenerPerfilUsuario(id: String): Result<PerfilUsuario>
}

data class PerfilUsuario(
    val id: String,
    val nombre: String,
    val correo: String
)