package com.wu.third

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.tencent.tauth.Tencent
import com.wu.third.databinding.ActivityWechatBinding
import java.util.Observer


/**
 * @author wkq
 *
 * @date 2021年11月12日 14:14
 *
 *@des
 *
 */

class WechatThirdActivity : AppCompatActivity(), Observer {

    var mTencent: Tencent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityWechatBinding.inflate(LayoutInflater.from(this))
        mTencent = Tencent.createInstance("xxxx", this.getApplicationContext())

        binding.btLogin.setOnClickListener {
            //微信登录
            if (ThirdUtil!!.isWXAppInstalledAndSupported(this)) {
                ThirdUtil.loginWechat(this)
            }
        }

        binding.btShare.setOnClickListener {
            //微信分享网页
            if (ThirdUtil!!.isWXAppInstalledAndSupported(this)) {
                ThirdUtil.shareWechat(this, "", "1", "2", null)
            }
        }

        binding.btShareImg.setOnClickListener {
            //微信分享图片
            if (ThirdUtil!!.isWXAppInstalledAndSupported(this)) {
                ThirdUtil.shareWechatImg(this, null)
            }

        }
        binding.btPay.setOnClickListener {
            //微信支付
            if (ThirdUtil!!.isWXAppInstalledAndSupported(this)) {
                ThirdUtil.payWechat(this, "", "", "", "", "", "", "", "")
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //设置回调
    }

    override fun update(o: java.util.Observable?, arg: Any?) {

    }


}