package com.wu.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class OnLineInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取retrofit @headers里面的参数，参数可以自己定义，在本例我自己定义的是cache，跟@headers里面对应就可以了
        var cache = chain.request().header("cache")
        var originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是5秒，方便观察。注意这里的cacheControl是服务器返回的
        return if (cacheControl == null) {
            //如果cache没值，缓存时间为TIMEOUT_CONNECT，有的话就为cache的值
            if (cache == null || "" == cache) {
                cache =
                    TIMEOUT_CONNECT.toString() + ""
            }
            originalResponse = originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$cache")
                .build()
            originalResponse
        } else {
            originalResponse
        }
    }

    companion object {
        private const val TIMEOUT_CONNECT = 5 //5秒
    }
}