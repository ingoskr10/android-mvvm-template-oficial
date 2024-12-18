package com.ingoskr.template_oficial.Reutilizable.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ingoskr.template_oficial.Reutilizable.room.DAO.GlobalDao
import com.ingoskr.template_oficial.Reutilizable.room.DAO.UsuarioDao
import com.ingoskr.template_oficial.Reutilizable.room.entidades.Globals
import com.ingoskr.template_oficial.Reutilizable.room.entidades.Usuario

@Database(entities = [Usuario::class, Globals::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun globalDao(): GlobalDao
}