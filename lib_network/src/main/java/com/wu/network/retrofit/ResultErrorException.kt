package com.wu.network.retrofit

/**
 * 定义通用异常
 */
class ResultErrorException(val status: Int, val msg: String?) : Throwable()
