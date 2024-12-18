package com.ingoskr.template_oficial.Repositorios

import SendVolley
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.ingoskr.template_oficial.Reutilizable.Volley.VolleyCallback
import com.ingoskr.template_oficial.Reutilizable.room.DAO.UsuarioDao

class UsuarioRepositorio(private val usuarioDao: UsuarioDao, private val sendVolley: SendVolley) : VolleyCallback {
    fun consultarDatos() {
        sendVolley.setUrl("https://pokeapi.co/api/v2/pokemon/ditto")
        sendVolley.executeRequestSync(Request.Method.GET, null, object : VolleyCallback {
            override fun getResponse(response: String?) {
                Log.d("RESULT", response.toString())
            }

            override fun getErrorVolley(volleyError: VolleyError?) {
                Log.d("RESULT", volleyError.toString())
            }
        })
    }

    override fun getResponse(response: String?) {
        TODO("Not yet implemented")
    }

    override fun getErrorVolley(volleyError: VolleyError?) {
        TODO("Not yet implemented")
    }
}