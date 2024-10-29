package com.ingoskr.template_oficial.repositorios

import com.ingoskr.template_oficial.entidades.ServicioApi
import com.ingoskr.template_oficial.reutilizable.room.DAO.UsuarioDao
import com.ingoskr.template_oficial.reutilizable.room.entidades.Usuario
import com.ingoskr.template_oficial.utilidades.EventoBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID

class UsuarioRepositorio(
    private val usuarioDao: UsuarioDao,
    private val servicioApi: ServicioApi,
    private val eventoBus: EventoBus
) {
    fun obtenerTodosLosUsuarios(): Flow<List<Usuario>> =
        usuarioDao.obtenerTodos()

    suspend fun guardarUsuario(correo: String, nombre: String) {
        withContext(Dispatchers.IO) {
            val usuario = Usuario(
                id = UUID.randomUUID().toString(),
                correo = correo,
                nombre = nombre
            )
            usuarioDao.insertar(usuario)
            eventoBus.publicar(EventoUsuarioGuardado(usuario))
        }
    }

    suspend fun iniciarSesion(correo: String, contraseña: String): Result<Boolean> =
        withContext(Dispatchers.IO) {
            servicioApi.iniciarSesion(correo, contraseña)
        }
}

data class EventoUsuarioGuardado(val usuario: Usuario)