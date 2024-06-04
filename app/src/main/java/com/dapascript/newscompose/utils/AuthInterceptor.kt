package com.dapascript.newscompose.utils

import android.content.Context
import com.dapascript.newscompose.R
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val token = context.getString(R.string.api_key)
        val request = chain.request().newBuilder().apply { addHeader("X-Api-Key", token) }
        return chain.proceed(request.build())
    }
}