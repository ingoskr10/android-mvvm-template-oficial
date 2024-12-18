import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ingoskr.template_oficial.Reutilizable.Volley.VolleyCallback
import com.ingoskr.template_oficial.Utilidades.Constantes
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.ExecutionException


class SendVolley {
    var JsonURL: String? = null
    var context: Context? = null
    var requestQueue: RequestQueue? = null
    var jsonresponse: String? = null
    var callback: VolleyCallback? = null
    private var header: HashMap<String, String>? = null

    fun limpiarCache() {
        requestQueue!!.cache.clear()
    }

    fun setUrl(url: String?) {
        this.JsonURL = url
    }

    private fun callbackVolley(): VolleyCallback{
        return object : VolleyCallback{
            override fun getResponse(response: String?) {
                TODO("Not yet implemented")
            }

            override fun getErrorVolley(volleyError: VolleyError?) {
                TODO("Not yet implemented")
            }
        }
    }
    constructor(context: Context?) {
        try {
            this.context = context
            this.requestQueue = Volley.newRequestQueue(context)
            this.callback = callbackVolley()
            header = HashMap()
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    fun addHeader(key: String, value: String) {
        header!![key] = value
    }

    fun executeRequest(method: Int, callback: VolleyCallback) {
        val stringRequest: StringRequest = object : StringRequest(method, JsonURL,
            Response.Listener { response ->
                jsonresponse = response
                callback.getResponse(jsonresponse)
            },
            Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                return header
            }
        }
        stringRequest.setRetryPolicy(
            DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }

    fun executeRequestJson(method: Int, objeto: JSONObject?, url: String?) {
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(method, url, objeto,
            Response.Listener { response: JSONObject -> callback!!.getResponse(response.toString()) },
            Response.ErrorListener { error: VolleyError? -> callback!!.getErrorVolley(error) }) {
            override fun getHeaders(): Map<String, String> {
                val param: MutableMap<String, String> = HashMap()
                param[Constantes.CONTENT_TYPE] = "application/json"
                return param
            }
        }

        jsonObjectRequest.setRetryPolicy(
            DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(jsonObjectRequest)
    }

    fun executeRequestJsonReturnAsync(method: Int?, objeto: JSONObject?, url: String?): String {
        val jsonresponse: String
        val future = RequestFuture.newFuture<JSONObject>()
        val request = JsonObjectRequest(method!!, url, objeto, future, future)
        request.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(request)
        jsonresponse = try {
            future.get().toString()
        } catch (e: InterruptedException) {
            "null"
        } catch (e: ExecutionException) {
            "null"
        }

        return jsonresponse
    }

    fun executeRequestJsonReturnget(method: Int, objeto: JSONObject?, url: String?): String? {
        val jsonresponse: String?
        val future = RequestFuture.newFuture<JSONObject>()

        val request = JsonObjectRequest(
            method,
            url,
            objeto,
            future,
            future
        )

        request.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )

        requestQueue!!.cache.clear()
        requestQueue!!.add(request)

        jsonresponse = try {
            future.get().toString()
        } catch (e: InterruptedException) {
            null
        } catch (e: ExecutionException) {
            null
        }

        return jsonresponse
    }

    fun executeRequestSoapSync(
        method: Int?,
        objeto: String,
        url: String?,
        metodoActivionSoap: String
    ): String {
        val soapResponse: String
        val future = RequestFuture.newFuture<String>()
        val request: StringRequest = object : StringRequest(method!!, url, future, future) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return objeto.toByteArray()
            }

            override fun getBodyContentType(): String {
                return "text/xml; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers[Constantes.CONTENT_TYPE] = "text/xml; charset=utf-8"
                headers["SOAPAction"] = metodoActivionSoap
                return headers
            }
        }
        request.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(request)
        soapResponse = try {
            future.get().toString()
        } catch (e: InterruptedException) {
            "null"
        } catch (e: ExecutionException) {
            "null"
        }

        return soapResponse
    }

    fun executeRequestSync(method: Int, headers: Map<String, String>?, callback: VolleyCallback) {
        val stringRequest: StringRequest = object : StringRequest(method, JsonURL,
            Response.Listener { response ->
                jsonresponse = response
                callback.getResponse(jsonresponse)
            },
            Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return headers ?: super.getHeaders()
            }
        }

        stringRequest.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(stringRequest)
    }


    fun executeRequestAsync(
        method: Int,
        headers: Map<String, String>?,
        jsonBody: JSONObject?,
        callback: VolleyCallback
    ) {
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(method, JsonURL, jsonBody,
                Response.Listener { response -> callback.getResponse(response.toString()) },
                Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headers ?: super.getHeaders()
                }
            }

        jsonObjectRequest.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(jsonObjectRequest)
    }

    fun executeBase64RequestAsync(
        method: Int,
        headers: Map<String, String>?,
        base64Body: String,
        callback: VolleyCallback
    ) {
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(method, JsonURL, null,
            Response.Listener { response -> callback.getResponse(response.toString()) },
            Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val customHeaders: MutableMap<String, String> =
                    if (headers != null) HashMap(headers) else HashMap()
                customHeaders["Content-Type"] = "application/base64"
                return customHeaders
            }

            override fun getBody(): ByteArray {
                return base64Body.toByteArray()
            }
        }
        jsonObjectRequest.setRetryPolicy(
            DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.cache.clear()
        requestQueue!!.add(jsonObjectRequest)
    }

    fun executeRequestAsyncAll(
        method: Int,
        headers: Map<String, String>?,
        jsonBody: JSONObject?,
        callback: VolleyCallback
    ) {
        val customJsonRequest = CustomJsonRequest(
            method,
            JsonURL,
            jsonBody,
            headers,
            { response -> callback.getResponse(response.toString()) },
            { error -> callback.getErrorVolley(error) }
        )

        customJsonRequest.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )

        requestQueue!!.add(customJsonRequest)
    }


    inner class CustomJsonRequest(
        method: Int, url: String?, private val jsonRequest: JSONObject?,
        private val headers: Map<String, String>?,
        private val listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener?
    ) :
        Request<JSONObject>(method, url, errorListener) {
        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            return headers ?: super.getHeaders()
        }

        @Throws(AuthFailureError::class)
        override fun getBody(): ByteArray? {
            return jsonRequest?.toString()?.toByteArray()
        }

        override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
            try {
                val jsonString =
                    String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
                return Response.success(
                    JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            } catch (e: Exception) {
                return Response.error(VolleyError(e))
            }
        }

        override fun deliverResponse(response: JSONObject) {
            listener.onResponse(response)
        }
    }

    fun executeJsonArrayRequestAsync(
        method: Int,
        headers: Map<String, String>?,
        jsonArrayBody: JSONArray,
        callback: VolleyCallback
    ) {
        val jsonArrayRequest: Request<JSONArray> = object : Request<JSONArray>(method, JsonURL,
            Response.ErrorListener { error -> callback.getErrorVolley(error) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val customHeaders: MutableMap<String, String> =
                    if (headers != null) HashMap(headers) else HashMap()
                customHeaders["Content-Type"] = "application/json" // Tipo de contenido JSON
                return customHeaders
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return jsonArrayBody.toString().toByteArray()
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONArray> {
                try {
                    val jsonString = String(
                        response.data,
                        charset(HttpHeaderParser.parseCharset(response.headers))
                    )
                    return Response.success(
                        JSONArray(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                } catch (e: Exception) {
                    return Response.error(VolleyError(e))
                }
            }

            override fun deliverResponse(response: JSONArray) {
                callback.getResponse(response.toString())
            }
        }

        jsonArrayRequest.setRetryPolicy(
            DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue!!.add(jsonArrayRequest)
    }

}