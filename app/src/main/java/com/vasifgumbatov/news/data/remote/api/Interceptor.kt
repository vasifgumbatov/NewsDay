package com.vasifgumbatov.news.data.remote.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("MyLog", "--> ${request.method} ${request.url} ")
        Log.d("MyLog", "--> ${request.headers} ")
        Log.d("MyLog", "--> ${request.body} ")
        Log.d("MyLog", "--> --> END ${request.method} ")

        val response = chain.proceed(request)
        Log.d("MyLog", "--> ${request.method} ${request.url} ")
        Log.d("MyLog", "<--> END ${request.method} ")
        Log.d("MyLog", "${response.body} ")
        Log.d("MyLog", "<-- END ${request.method} ")
        return response
    }
}