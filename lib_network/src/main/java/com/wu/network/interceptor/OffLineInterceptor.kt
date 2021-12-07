package com.wu.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class OffLineInterceptor(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //离线的时候为7天的缓存。
        request = request.newBuilder()
            .header(
                "Cache-Control",
                "public, only-if-cached, max-stale=$TIMEOUT_DISCONNECT"
            )
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7 //7天
    }

}