package com.wu.network.retrofit

import com.wu.network.converter.GsonConverterFactory
import com.wu.network.interceptor.HttpAddHeadersInterceptor
import com.wu.network.interceptor.ProcessDataInterceptor
import com.wu.network.util.NetWorkCode
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途: 初始化 Retrofit数据
 */



object  RetrofitFactory {

     fun initHttp(): Retrofit {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
         //读取时间
        okHttpClientBuilder.readTimeout(NetWorkCode.CONNECTTIME, TimeUnit.SECONDS)
         //链接时间
        okHttpClientBuilder.connectTimeout(NetWorkCode.CONNECTTIME,TimeUnit.SECONDS)
         //数据处理拦截器
        okHttpClientBuilder.addInterceptor(ProcessDataInterceptor())
         //Header 添加数据拦截器
        okHttpClientBuilder.addInterceptor(HttpAddHeadersInterceptor())
         //设置连接池
        okHttpClientBuilder.connectionPool( ConnectionPool( 5, 5,TimeUnit.SECONDS )
        )
        var retrofit = Retrofit.Builder()
            .baseUrl(NetWorkCode.BASE_URL)//设置BASEURL(以/结尾)
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 数据转换
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit

    }
}