package com.ingoskr.template_oficial.reutilizable.Volley

import com.android.volley.VolleyError

interface VolleyCallback {

    fun getResponse(response: String?)
    fun getErrorVolley(volleyError: VolleyError?)

}