package com.wu.network.retrofit

import com.wu.network.converter.ConvertDataFunc
import com.wu.network.listener.DataCallback
import com.wu.network.model.BaseInfo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途: 数据转换
 */


/**
 * 可取消方式数据请求
 */
fun <T> Observable<BaseInfo<T>>.convertExecute(callback: DataCallback<T>) :Disposable{
    var netWorkSubscribe = NetWorkSubscribe<T>(callback)
    return this.flatMap(ConvertDataFunc()).rawExecute(netWorkSubscribe)
}

/**
 * 数据监听 返回数据
 */
fun <T> Observable<T>.rawExecute(callback: NetWorkSubscribe<T>): Disposable {
    return  this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( callback.next,
            callback.err,
            callback.action,
            callback.onSubscribe)
}


/**
 *  数据转换 以及无法返回监听数据
 */
fun <T> Observable<BaseInfo<T>>.execute(subscriber: Observer<T>) {
    this.flatMap(ConvertDataFunc()).rawExecute(subscriber)
}

/**
 * 原始执行
 */
fun <T> Observable<T>.rawExecute(subscriber: Observer<T>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}





