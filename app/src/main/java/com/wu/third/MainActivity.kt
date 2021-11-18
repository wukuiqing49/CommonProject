package com.wu.third

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wu.third.classification.ClassificationActivity

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
    }
}