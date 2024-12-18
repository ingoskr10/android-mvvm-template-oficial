package com.sdov.plantillamobile.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.ingoskr.template_oficial.Implementaciones.Firebase.firebaseAuth
import kotlinx.coroutines.tasks.await

class firebaseService(val firebaseAuth: firebaseAuth) {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .build()
        firestore.firestoreSettings = settings
    }

    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            firebaseAuth.iniciarSesion(email, password)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun cerrarSesion() {
        firebaseAuth.cerrarSesion()
    }

    suspend fun <T> getDocument(collection: String, documentId: String, clazz: Class<T>): T? {
        return try {
            firestore.collection(collection)
                .document(documentId)
                .get()
                .await()
                .toObject(clazz)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun <T : Any> setDocument(collection: String, documentId: String, data: T) {
        firestore.collection(collection)
            .document(documentId)
            .set(data)
            .await()
    }

    suspend fun <T : Any> getCollection(collection: String, clazz: Class<T>): List<T> {
        return try {
            firestore.collection(collection)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(clazz) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
