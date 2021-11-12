package com.wu.third

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.wu.third.databinding.ActivityQqBinding


/**
 * @author wkq
 *
 * @date 2021年11月12日 14:14
 *
 *@des
 *
 */

class QQThirdActivity : AppCompatActivity(), IUiListener {

    var mTencent: Tencent? = null

    //qq的权限
    val ACOPE_ALL = "all"
    val url = "https://www.baidu.com/"
    var img = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.redocn.com%2Fsheying%2F20140704%2Fyuntaishanfengjing_2695407.jpg&refer=http%3A%2F%2Fimg.redocn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639293976&t=cc27d9362325a51c8ee2c2c16a6dbe25"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = DataBindingUtil.setContentView<ActivityQqBinding>(this, R.layout.activity_qq)
        mTencent = Tencent.createInstance("xxxx", this.getApplicationContext())

        binding.btLogin.setOnClickListener {

            //QQ 登录
            if (mTencent!!.isQQInstalled(this))
                mTencent!!.login(this, ACOPE_ALL, this)
        }

        binding.btShare.setOnClickListener {
            //QQ分享网页
            if (mTencent!!.isQQInstalled(this))
                ThirdUtil.shareQQ(this, mTencent!!, "标题", "这是简介", img, url)
        }

        binding.btShareImg.setOnClickListener {
            //QQ分享图片
            if (mTencent!!.isQQInstalled(this))
            ThirdUtil.shareQQImg(this, mTencent!!, "path");

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //设置回调
        Tencent.onActivityResultData(requestCode, resultCode, data, this)
    }

    //成功
    override fun onComplete(p0: Any?) {
        ThirdUtil.loginQQ(this, mTencent!!, p0!!)
    }

    //失败
    override fun onError(p0: UiError?) {

    }

    //取消
    override fun onCancel() {

    }

    override fun onWarning(p0: Int) {

    }


}