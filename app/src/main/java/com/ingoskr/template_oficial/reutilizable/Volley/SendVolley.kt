package com.ingoskr.template_oficial.reutilizable.Volley

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*
import java.util.concurrent.ExecutionException


class SendVolley {

    var JsonURL: String? = null
    var context: Context? = null
    var requestQueue: RequestQueue? = null
    var jsonresponse: String? = null
    var callback: VolleyCallback? = null

    private var header: HashMap<String, String>? = null

    constructor(context: Context?, callback: VolleyCallback?) {
        try {
            this.context = context
            requestQueue = Volley.newRequestQueue(context)
            this.callback = callback
        } catch (e: Exception) {
        }
    }

    constructor(context: Context?) {
        try {
            this.context = context
            requestQueue = Volley.newRequestQueue(context)
        } catch (e: Exception) {
        }
    }


    fun addHeader(key: String, value: String) {
        header!!.put(key, value)
    }

    fun executeRequest(method: Int, callback: VolleyCallback) {
        val stringRequest: StringRequest = object : StringRequest(method, JsonURL,
            Response.Listener { response ->
                jsonresponse = response
                callback.getResponse(jsonresponse)
            }, Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                //Log.i("header",header.toString());
                return header!!
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }


    fun executeRequestWithoutMethods(method: Int, callback: VolleyCallback) {
        val stringRequest = StringRequest(method, JsonURL,
            { response ->
                jsonresponse = response
                callback.getResponse(jsonresponse)
            }) { error -> callback.getErrorVolley(error) }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }

    fun executeRequest(method: Int, url: String?, paramethers: HashMap<String, String>) {
        header = HashMap()
        header = paramethers
        val stringRequest: StringRequest = object : StringRequest(method, url,
            Response.Listener { response ->
                jsonresponse = response
                callback!!.getResponse(jsonresponse)
            },
            Response.ErrorListener { error -> callback!!.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                return header!!
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }

    fun executeRequestJson(method: Int, objeto: JSONObject?, url: String?) {
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(method, url, objeto,
            Response.Listener { response -> callback!!.getResponse(response.toString()) },
            Response.ErrorListener { error -> callback!!.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val param: MutableMap<String, String> = HashMap()
                param["Content-Type"] = "application/json"
                return param
            }
        }
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(jsonObjectRequest)
    }

    fun executeRequestJsonArray(method: Int, objeto: JSONArray, url: String?) {
        val jsonRequest = object : JsonRequest<Any>(
            method,
            url,
            objeto.toString(),
            Response.Listener { response ->
                when (response) {
                    is JSONArray -> callback?.getResponse(response.toString())
                    is JSONObject -> callback?.getResponse(response.toString())
                    else -> callback?.getErrorVolley(VolleyError("Respuesta inesperada"))
                }
            },
            Response.ErrorListener { error ->
                callback?.getErrorVolley(error)
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<Any> {
                return try {
                    val jsonString = String(response?.data ?: ByteArray(0))
                    val result = when {
                        jsonString.trim().startsWith("[") -> JSONArray(jsonString)
                        jsonString.trim().startsWith("{") -> JSONObject(jsonString)
                        else -> throw JSONException("Ni JSONObject ni JSONArray")
                    }
                    Response.success(result, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: Exception) {
                    Response.error(VolleyError(e))
                }
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val param: MutableMap<String, String> = HashMap()
                param["Content-Type"] = "application/json"
                return param
            }
        }

        jsonRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue?.cache?.clear()
        requestQueue?.add(jsonRequest)
    }

    fun executeRequestJsonReturnget(method: Int, objeto: JSONObject?, url: String?): String? {
        var jsonresponse: String? = null
        val future = RequestFuture.newFuture<JSONObject>()
        val request = JsonObjectRequest(method, url, objeto, future, future)
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(request)

        try {
            jsonresponse = future.get().toString()
        } catch (e: InterruptedException) {
            jsonresponse = null
        } catch (e: ExecutionException) {
            jsonresponse = null
        }

        return jsonresponse
    }


    fun executeRequestWithoutMethods(method: Int, url: String?) {
        val stringRequest = StringRequest(method, url,
            { response ->
                jsonresponse = response
                callback!!.getResponse(jsonresponse)
            }
        ) { error -> callback!!.getErrorVolley(error) }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }


    fun errorCodeVolleyMessage(
        volleyError: VolleyError,
        context: Context?,
        fragment: Fragment,
        activity: Activity
    ) {
        try {
//            dialogmessage(
//                "Error :" + GlobalWorkGroup.MESSAGE_ALERT_VOLLEY + volleyError.networkResponse.statusCode.toString() + " Reintente de nuevo",
//                fragment,
//                activity
//            )
        } catch (_: JSONException) {
//            dialogmessage(GlobalWorkGroup.MESSAGE_ALERT_VOLLEY, fragment, activity)
        } catch (errorr: UnsupportedEncodingException) {
//            dialogmessage(GlobalWorkGroup.MESSAGE_ALERT_VOLLEY, fragment, activity)
        } catch (e: java.lang.Exception) {
//            dialogmessage(GlobalWorkGroup.MESSAGE_ALERT_VOLLEY, fragment, activity)
        }
    }


    fun dialogmessage(mensaje: String, fragment: Fragment, activity: Activity) {

//        fragment.activity?.runOnUiThread(kotlinx.coroutines.Runnable {
//            val dialogBuilder =
//                MaterialAlertDialogBuilder(activity!!, R.style.AlertDialogTheme)
//                    .setMessage(mensaje)
//                    .setTitle("Atención")
//                    .setPositiveButton(
//                        "Aceptar"
//                    ) { dialog, which -> //
//                        dialog.dismiss()
//                    }
//
//            dialogBuilder.create().show()
//
//        })
    }

    fun errorCodeVolleyMessage(volleyError: VolleyError, activity1: Context?, activity: Activity) {
        try {
            //Log.i("error",volleyError.networkResponse.statusCode+"");
            val responseBody = volleyError.networkResponse.data.toString()
            val data = JSONObject(responseBody)
            val mensaje = JSONObject(data.getString("respuesta"))
            dialogmessage(mensaje.getString("mensaje"), activity)
        } catch (e: JSONException) {
            dialogmessage(e.message!!, activity)
        } catch (errorr: UnsupportedEncodingException) {
            dialogmessage(errorr.message!!, activity)
        } catch (e: java.lang.Exception) {
            dialogmessage(e.message!!, activity)
        }
    }


    fun dialogmessage(mensaje: String, activity: Activity) {

//        activity?.runOnUiThread(kotlinx.coroutines.Runnable {
//            val dialogBuilder =
//                MaterialAlertDialogBuilder(activity!!, R.style.AlertDialogTheme)
//                    .setMessage(mensaje)
//                    .setTitle("Atención")
//                    .setPositiveButton(
//                        "Aceptar"
//                    ) { dialog, which -> //
//                        dialog.dismiss()
//                    }
//
//            dialogBuilder.create().show()
//
//        })
    }
}