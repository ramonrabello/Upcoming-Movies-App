package com.arctouch.codechallenge.network.interceptor

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.network.RequestParams
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for requests made to Marvel API.
 */
class RequestParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter(RequestParams.API_KEY, BuildConfig.API_KEY)
                .addQueryParameter(RequestParams.LANGUAGE, BuildConfig.DEFAULT_LANGUAGE)
                .addQueryParameter(RequestParams.REGION, BuildConfig.DEFAULT_REGION)
                .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}