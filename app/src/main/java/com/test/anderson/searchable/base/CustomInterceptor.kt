package com.test.anderson.searchable.base

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {

    @SuppressLint("HardwareIds")
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .build()
        return chain.proceed(request)
    }
}