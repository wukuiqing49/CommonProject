package com.wu.network.interceptor

import android.text.TextUtils
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  数据处理以及添加通用参数拦截器
 */

class ProcessDataInterceptor : BaseInterceptor() {


    override fun intercept(response: Response?, url: String?, body: String?): Response {
        val contentType = response!!.body()!!.contentType()
        var content: String = ""
        if (response!!.isSuccessful()) {
            content = procssSucessData(body)
        } else {
            if (body!=null){
                content = processFailData(body)
            }else{
                var jsonData = JSONObject()
                jsonData.put("message", response.message())
                jsonData.put("code", response.code().toString() + "")
                jsonData.put("data", "数据异常")
                content = jsonData.toString()
            }

        }
        var responseBody = ResponseBody.create(contentType, content)
        return response.newBuilder().body(responseBody).build()

    }

    /**
     * 处理成功数据
     */
    fun procssSucessData(sucessData: String?): String {
        var datas = JSONObject(sucessData)
        try {
            val jsonData = JSONObject()
            if (datas.has("code")) {
                var code = datas.get("code")
                jsonData.put("code", code)
            } else {
                jsonData.put("code", 200)
            }

            if (datas.has("message")) {
                var message = datas.get("message")
                jsonData.put("message", message)
            } else {
                jsonData.put("message", "成功")
            }

            if (datas.has("data")) {
                var dataContent = datas.get("data")
                jsonData.put("data", dataContent)
            } else {
                jsonData.put("data", "")
            }
            return jsonData.toString()

        } catch (e: JSONException) {
            processFailData(sucessData)
        }
        return ""
    }
    /**
     * 处理异常数据
     */
    fun processFailData(failContent: String?): String {
        try {
            var datas = JSONObject(failContent)
            val jsonData = JSONObject()
            if (datas.has("code")) {
                var code = datas.get("code")
                jsonData.put("code", code)
            } else {
                jsonData.put("code", 201)
            }
            if (datas.has("message")) {
                var message = datas.get("message")
                jsonData.put("message", message)
            } else {
                jsonData.put("message", "数据异常")
            }

            if (datas.has("data")) {
                var dataContent = datas.get("data")
                jsonData.put("data", dataContent)
            } else {
                jsonData.put("data", "")
            }
            return jsonData.toString()
        } catch (e: Exception) {
            val jsonData = JSONObject()
            jsonData.put("code", 101)
            if (e != null && !TextUtils.isEmpty(e.message)) {
                jsonData.put("message", e.message)
            } else {
                jsonData.put("message", "数据异常")
            }
            jsonData.put("data", "")
            return jsonData.toString()
        }
    }
}
