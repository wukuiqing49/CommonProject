package com.wu.network.listener

import com.google.gson.JsonSyntaxException
import com.wu.network.retrofit.NetWork
import com.wu.network.retrofit.ResultErrorException
import com.wu.network.util.NetWorkCode
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:
 */


 abstract  class DataCallback<T>: Observer<T> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
       onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (e is ResultErrorException) {
            // 服务器返回的异常
            onFailed( e.status, e.msg ?: "")
        } else {
            // 网络、解析等异常
            val status: Int
            val exceptionMessage = when (e) {
                is UnknownHostException -> {
                    // 无网络
                    status = NetWorkCode.NETFAILCODE
                    "网络异常"
                }
                is SocketTimeoutException -> {
                    // 网络超时
                    status = NetWorkCode.NETFAILCODE
                    "网络超时"
                }
                is JsonSyntaxException -> {
                    // 解析错误
                    status = NetWorkCode.FAILCODE
                    "解析错误"
                }
                else -> {
                    // 未知错误，打印出来，遇到可添加如上处理，正式的时候不会打印此消息
                    status = NetWorkCode.FAILCODE
                    e.printStackTrace()
                    e.message ?: "未知错误"
                }
            }
            onFailed( status, exceptionMessage)
        }
    }


   abstract fun onSuccess(bean: T)

   abstract fun onFailed( status: Int, message: String)

}