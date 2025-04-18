package com.iec.makeup.core.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptorWithToken @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    private val scope = runBlocking {

    }
    private var cacheToken: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (cacheToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer $cacheToken")
        } else {
            runBlocking(Dispatchers.IO) {
                tokenManager.getToken().first()?.let {
                    cacheToken = it
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
            }
        }
        return chain.proceed(requestBuilder.build())
    }

}