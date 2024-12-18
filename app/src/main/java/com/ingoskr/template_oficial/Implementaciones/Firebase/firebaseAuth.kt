package com.ingoskr.template_oficial.Implementaciones.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ingoskr.template_oficial.Reutilizable.room.DAO.GlobalDao
import com.ingoskr.template_oficial.Reutilizable.room.entidades.Globals
import kotlinx.coroutines.tasks.await


class firebaseAuth (private val firebaseAuth: FirebaseAuth, private val globalDao: GlobalDao) {

    suspend fun iniciarSesion(email: String, password: String): FirebaseUser? {
        val result =
            firebaseAuth.currentUser ?: firebaseAuth.signInWithEmailAndPassword(email, password)
                .await().user
        globalDao.insertAll(Globals("user_uid",result!!.uid))
        return result
    }

    fun cerrarSesion() {
        firebaseAuth.signOut()
    }
}