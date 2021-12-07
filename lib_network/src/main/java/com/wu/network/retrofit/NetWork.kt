package com.wu.network.retrofit

import android.content.Context
import com.wu.network.api.MallApi
import com.wu.network.listener.DataCallback
import com.wu.network.model.BaseInfo
import com.wu.network.model.UserInfo
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2021/12/4
 *
 * 用途:  网络请求
 */


object NetWork {
    private var retrofit: Retrofit? = null

    init {
        retrofit = RetrofitFactory.initHttp()
    }

    /**
     * 具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit!!.create(service)
    }


    // 可取消数据请求示例
    private fun <T> test(context: Context, callback: DataCallback<T>): Disposable {
        var map = HashMap<String, String>()
        map.put("name", "123")
        map.put("password", "456")
        var datas = create(MallApi::class.java).login(map)
        var logInService = create(MallApi::class.java).login(map) as Observable<BaseInfo<T>>
        var data = logInService.convertExecute(callback)
        return data
    }

    // 不可取消数据请求示例
    private fun <T> test2(context: Context, callback: DataCallback<T>) {
        var map = HashMap<String, String>()
        map.put("name", "123")
        map.put("password", "456")
        var datas = create(MallApi::class.java).login(map)
        var logInService = create(MallApi::class.java).post(map).execute(object : DataCallback<UserInfo>() {
                override fun onSuccess(bean: UserInfo) {

                }

                override fun onFailed(status: Int, message: String) {

                }
            })
    }


}