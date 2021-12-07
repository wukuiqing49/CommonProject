package com.wu.network.util

import com.wu.network.BuildConfig


object ParameterUtil {


    /**
     * 添加通用参数
     */
    fun getOpenSignMap( params: HashMap<String, String?>? ): Map<String, String?> {
        if (params == null) return HashMap()
        if ( !params.containsKey("plat")) {
            params["platform"] = "android"
        }
        if (!params.containsKey("timestamp")) {
            val time = System.currentTimeMillis()
            params["timestamp"] = (time / 1000).toString()
        }
        if ( !params.containsKey("ver")) {
            params["ver"] = BuildConfig.VERSION_NAME
        }
        return params
    }


}