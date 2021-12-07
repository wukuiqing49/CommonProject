package com.wu.network.util

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:
 */


object NetWorkCode {

    val BASE_URL = "http://kalle.nohttp.net/"
    val CONNECTTIME = 5000L

    //成功Code
    val SUCESSCODE = 200
    val SUCESSCODE_MESSAGE: String = "成功"

    //错误Code
    val FAILCODE = -2
    val FAILCODE_MESSAGE: String = "请求失败"

    //网络异常Code
    val NETFAILCODE = -1
    val NETFAILCODE_MESSAGE: String = "网络异常"
}