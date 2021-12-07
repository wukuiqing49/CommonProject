package com.wu.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  添加Header数据拦截器
 */

class HttpAddHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var randomNumber = createRandomStr(8)
        val version=""
        val appKeyStr = "10010"
        val currentTimeStr = (System.currentTimeMillis() / 1000).toString()
        request = request.newBuilder()
            .addHeader("App-Key", appKeyStr)
            .addHeader("Timestamp", currentTimeStr)
            .addHeader("Phone-Model", "Android")
            .addHeader("Version", version)
            .addHeader("Nonce-Str", randomNumber)

            .build()
        return chain.proceed(request)
    }

    /**
     * 1.生成的字符串每个位置都有可能是str中的一个字母或数字，需要导入的包是import java.util.Random;
     * @param length
     * @return
     */
    fun createRandomStr(length: Int): String? {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val stringBuffer = StringBuffer()
        for (i in 0 until length) {
            val number = random.nextInt(62)
            stringBuffer.append(str[number])
        }
        return stringBuffer.toString()
    }
}
