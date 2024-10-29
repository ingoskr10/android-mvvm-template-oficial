package com.ingoskr.template_oficial.reutilizable.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ingoskr.template_oficial.reutilizable.room.DAO.UsuarioDao
import com.ingoskr.template_oficial.reutilizable.room.entidades.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}