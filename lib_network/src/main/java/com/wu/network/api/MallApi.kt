package com.wu.network.api

import com.wu.network.model.BaseInfo
import com.wu.network.model.UserInfo
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:
 */


interface MallApi {

    //首页列表
    @GET("method/get")
    fun get(@QueryMap maps: Map<String, String>): Observable<BaseInfo<UserInfo>>

    @POST("login")
    fun login(@QueryMap maps: Map<String, String>): Observable<BaseInfo<Any>>

    //首页列表
    @POST("method/post")
    fun post(@QueryMap maps: Map<String, String>): Observable<BaseInfo<UserInfo>>


}