package com.ingoskr.template_oficial.reutilizable.room.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val id: String,
    val correo: String,
    val nombre: String
)