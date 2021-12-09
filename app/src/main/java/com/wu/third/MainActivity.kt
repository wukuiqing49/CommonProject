package com.wu.third

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.wu.network.api.MallApi
import com.wu.network.listener.DataCallback
import com.wu.network.model.UserInfo
import com.wu.network.retrofit.NetWork
import com.wu.network.retrofit.convertExecute
import com.wu.third.classification.ClassificationActivity
import com.wu.third.net.NetActivity
import com.wu.third.textview.TextViewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.bt_qq).setOnClickListener {
            startActivity(Intent(this, QQThirdActivity::class.java))
        }
        findViewById<Button>(R.id.bt_wechat).setOnClickListener {
            startActivity(Intent(this, WechatThirdActivity::class.java))
        }
        findViewById<Button>(R.id.bt_classification).setOnClickListener {
            startActivity(Intent(this, ClassificationActivity::class.java))
        }
        findViewById<Button>(R.id.bt_net).setOnClickListener {
            startActivity(Intent(this, NetActivity::class.java))
        }
        findViewById<Button>(R.id.bt_text).setOnClickListener {
            startActivity(Intent(this, TextViewActivity::class.java))
        }
    }

    fun net(){
        var map = HashMap<String, String>()
        map.put("name", "123")
        map.put("password", "456")
        NetWork.create(MallApi::class.java)
            .post(map)
            .convertExecute(object : DataCallback<UserInfo>() {
                override fun onSuccess(bean: UserInfo) {
                }

                override fun onFailed(status: Int, message: String) {
                }

            })
    }
}