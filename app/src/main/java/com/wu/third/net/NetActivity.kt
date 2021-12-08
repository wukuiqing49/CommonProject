package com.wu.third.net

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wu.network.api.MallApi
import com.wu.network.listener.DataCallback
import com.wu.network.model.UserInfo
import com.wu.network.retrofit.NetWork
import com.wu.network.retrofit.convertExecute
import com.wu.third.R
import com.wu.third.databinding.ActivityNetBinding


/**
 * @author wkq
 *
 * @date 2021年12月07日 14:21
 *
 *@des
 *
 */

class NetActivity : AppCompatActivity() {
    var binding:ActivityNetBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityNetBinding>(this, R.layout.activity_net)
        initView()

    }

    private fun initView() {
        binding!!.btPost.setOnClickListener {
            getPostData()
        }

        binding!!.btGet.setOnClickListener {
            getData()
        }
    }

    private fun getPostData() {
        var map = HashMap<String, String>()
        map.put("name", "123")
        map.put("password", "456")
        NetWork.create(MallApi::class.java)
            .login(map)
            .convertExecute(object : DataCallback<Any>() {
                override fun onSuccess(bean: Any) {
                    Log.e("", "")
                }

                override fun onFailed(status: Int, message: String) {
                    Log.e("", "")
                }
            })

    }

    private fun getData() {
        var map = HashMap<String, String>()
        map.put("name", "123")
        map.put("age", "456")
        NetWork.create(MallApi::class.java)
            .get(map)
            .convertExecute(object : DataCallback<UserInfo>() {
                override fun onSuccess(bean: UserInfo) {
                    Log.e("", "")
                }

                override fun onFailed(status: Int, message: String) {
                    Log.e("", "")
                }
            })

    }


}