package com.ingoskr.template_oficial.implementaciones

import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.ingoskr.template_oficial.BuildConfig
import com.ingoskr.template_oficial.entidades.PerfilUsuario
import com.ingoskr.template_oficial.entidades.ServicioApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

class ServicioApiImpl(
    private val requestQueue: RequestQueue
) : ServicioApi {

    override suspend fun iniciarSesion(correo: String, contrasena: String): Result<Boolean> =
        suspendCancellableCoroutine { continuation ->
            try {
                val request = JsonObjectRequest(
                    "${BuildConfig.BASE_URL}auth/login",
                    JSONObject().apply {
                        put("email", correo)
                        put("password", contrasena)
                    },
                    { response ->
                        continuation.resume(Result.success(response.optBoolean("success", false)))
                    },
                    { error ->
                        continuation.resume(Result.failure(error))
                    }
                )

                continuation.invokeOnCancellation {
                    request.cancel()
                }

                requestQueue.add(request)
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }

    override suspend fun obtenerPerfilUsuario(id: String): Result<PerfilUsuario> =
        suspendCancellableCoroutine { continuation ->
            try {
                val request = JsonObjectRequest(
                    "${BuildConfig.BASE_URL}users/$id",
                    null,
                    { response ->
                        val perfil = PerfilUsuario(
                            id = response.getString("id"),
                            nombre = response.getString("name"),
                            correo = response.getString("email")
                        )
                        continuation.resume(Result.success(perfil))
                    },
                    { error ->
                        continuation.resume(Result.failure(error))
                    }
                )

                continuation.invokeOnCancellation {
                    request.cancel()
                }

                requestQueue.add(request)
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
}