package com.wu.network.interceptor

import com.wu.network.util.ParameterUtil
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.Throws

/**
 * 基础拦截器 添加通用参数
 */
abstract class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if ("POST" == request.method()) {
            val authorizedUrlBuilder = request.url().newBuilder()
            val parameter: HashMap<String, String?> =HashMap()
            for (key in request.url().queryParameterNames()) {
                parameter[key] = request.url().queryParameter(key)
                authorizedUrlBuilder.removeAllQueryParameters(key)
            }

            val builder = FormBody.Builder()
            ParameterUtil.getOpenSignMap(parameter).forEach {
                builder.add(it.key, it.value)
            }

            val newBody: RequestBody = builder.build()
            request = request.newBuilder()
                .method(request.method(), newBody)
                .url(authorizedUrlBuilder.build())
                .build()
        } else {
            val authorizedUrlBuilder = request.url().newBuilder()
            val parameter: HashMap<String, String?> =
                HashMap()
            for (key in request.url().queryParameterNames()) {
                parameter[key] = request.url().queryParameter(key)
                authorizedUrlBuilder.removeAllQueryParameters(key)
            }
          ParameterUtil.getOpenSignMap(parameter).entries.forEach {
                authorizedUrlBuilder.addQueryParameter(it.key, it.value);
            }
            authorizedUrlBuilder.scheme(request.url().scheme())
            authorizedUrlBuilder.host(request.url().host())
            request = request.newBuilder()
                .method(request.method(), request.body())
                .url(authorizedUrlBuilder.build())
                .build()
        }
        val response = chain.proceed(request)
        val responseBody = response.body()
        if (responseBody != null) {
            val contentLength = responseBody.contentLength()
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer()
            if ("gzip" == response.headers()["Content-Encoding"]) {
                val gzippedResponseBody = GzipSource(buffer.clone())
                buffer = Buffer()
                buffer.writeAll(gzippedResponseBody)
            }
            val contentType = responseBody.contentType()
            val charset: Charset?
            charset =
                if (contentType == null || contentType.charset(StandardCharsets.UTF_8) == null) {
                    StandardCharsets.UTF_8
                } else {
                    contentType.charset(StandardCharsets.UTF_8)
                }
            if (charset != null && contentLength != 0L) {
                return intercept(
                    response,
                    request.newBuilder().build().url().toString(),
                    buffer.clone().readString(charset)
                )
            }
        }
        return chain.proceed(request)
    }

    @Throws(IOException::class)
    abstract fun intercept(
        response: Response?,
        url: String?,
        body: String?
    ): Response
}