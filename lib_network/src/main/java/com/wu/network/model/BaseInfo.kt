package com.wu.network.model

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:
 */


data class BaseInfo<T> (val code:Int,val message:String?,val data:T?) {
}