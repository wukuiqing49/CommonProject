package com.wu.network.converter


import com.wu.network.model.BaseInfo
import com.wu.network.retrofit.ResultErrorException
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 通用数据类型转换封装
 */
class ConvertDataFunc<T> : Function<BaseInfo<T>, Observable<T>> {
    override fun apply(t: BaseInfo<T>): Observable<T> {
        if (t.code != 200) {
            return Observable.error(ResultErrorException(t.code, t.message))
        }
        return Observable.just(t.data)
    }
}
