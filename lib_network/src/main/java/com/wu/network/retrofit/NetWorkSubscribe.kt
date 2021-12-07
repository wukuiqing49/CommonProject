package com.wu.network.retrofit

import android.util.Log
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.wu.network.listener.DataCallback
import com.wu.network.util.NetWorkCode
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException


/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途: 请求数据处理
 */


class NetWorkSubscribe<T>(callback: DataCallback<T>) {
    var data: T? = null

    init {

    }

    fun isGoodJson(json: String): Boolean {
        return try {
            JsonParser().parse(json)
            true
        } catch (e: JsonParseException) {
            println("bad json: $json")
            false
        }
    }

    var next = object : Consumer<T> {
        override fun accept(t: T) {
            data = t
        }
    }

    var err = object : Consumer<Throwable> {
        override fun accept(t: Throwable) {
            if (t is HttpException) {
                var errInfo = t as HttpException
                var responseBody = errInfo.response().errorBody()
                if (errInfo.response() != null && responseBody != null) {
                    var content = responseBody!!.source().buffer()
                    var errData = content.clone().readString(responseBody.contentType()!!.charset())

                    var eCode = -2
                    var eMessage = ""
                    if (isGoodJson(errData)) {
                        var errjson = JSONObject(errData)

                        if (errjson.has("code")) {
                            eCode = errjson.get("code") as Int
                        }
                        if (errjson.has("message")) {
                            eMessage = errjson.get("message") as String
                        }
                    }

                    callback.onFailed(eCode, eMessage ?: NetWorkCode.FAILCODE_MESSAGE)
                } else {
                    callback.onFailed(
                        errInfo.code(),
                        errInfo.message() ?: NetWorkCode.FAILCODE_MESSAGE
                    )
                }
            } else if (t is ResultErrorException) {
                // 服务器返回的异常
                callback.onFailed(t.status, t.msg ?: NetWorkCode.FAILCODE_MESSAGE)
            } else {
                callback.onFailed(NetWorkCode.FAILCODE, t.message ?: NetWorkCode.FAILCODE_MESSAGE)
            }

        }
    }

    var action = object : Action {
        override fun run() {
            var info = data as T
            callback.onSuccess(info)
        }
    }
    var onSubscribe = object : Consumer<Disposable> {
        override fun accept(t: Disposable) {
            Log.e("", "")
        }
    }

}