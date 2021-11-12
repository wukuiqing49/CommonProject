package com.wu.third

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.tencent.connect.UnionInfo
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.connect.share.QQShare
import com.tencent.open.SocialConstants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject


/**
 * @author wkq
 *
 * @date 2021年11月12日 14:39
 *
 *@des 三方分享工具类(图片处理,以及url 长度处理牵扯到项目没展示)
 *
 */

object ThirdUtil {

    //QQ 登录
    fun loginQQ(context: Context, mTencent: Tencent, any: Any) {
        var jsObj = any as JSONObject

        if (jsObj != null) {
            var ret = jsObj.getInt(SocialConstants.PARAM_OPEN_ID)
            //授权成功
            if (ret == 0) {
                //此处登录授权成功
                val openId: String = jsObj.getString(SocialConstants.PARAM_OPEN_ID)
                val access_token: String = jsObj.getString(Constants.PARAM_ACCESS_TOKEN)
                val expires: String = jsObj.getString(Constants.PARAM_EXPIRES_IN)
                mTencent.openId = openId
                mTencent.setAccessToken(access_token, expires)
                //获取用户唯一ID
                getUnionId(
                    context,
                    mTencent
                )
            } else {
                // todo 授权失败

            }
        }
    }

    //获取用户唯一ID
    private fun getUnionId(context: Context, mTencent: Tencent) {
        var listener = object : IUiListener {
            override fun onComplete(response: Any?) {
                if (response != null) {
                    val jsonObject = response as JSONObject
                    try {
                        val unionid = jsonObject.getString("unionid")
                        getUserInfo(
                            context,
                            mTencent,
                            unionid
                        )
                    } catch (e: Exception) {
                        // todo 数据异常
                    }
                } else {
                    // todo 数据异常
                }
            }

            override fun onError(p0: UiError?) {
                // todo 获取唯一ID失败
            }

            override fun onCancel() {
            }

            override fun onWarning(p0: Int) {
            }

        }
        val unionInfo = UnionInfo(context, mTencent.qqToken)
        unionInfo.getUnionId(listener)

    }

    //获取用户信息
    private fun getUserInfo(context: Context, mTencent: Tencent, unionid: String) {
        val info = UserInfo(context, mTencent.qqToken)
        info.getUserInfo(object : IUiListener {
            override fun onComplete(value: Any?) {
                val jsonObject = value as JSONObject
                if (jsonObject == null) return
                //名字
                val name = jsonObject.getString("nickname")
                //头像
                val urlface = jsonObject.getString("figureurl_qq")
                //性别
                val gender = jsonObject.getString("gender")
                //省会
                val province = jsonObject.getString("province")
                //城市
                val city = jsonObject.getString("city")
                Log.e("QQ数据", name + ":" + urlface + ":" + gender + ":" + province + ":" + city)

            }

            override fun onError(p0: UiError?) {
                // todo 用户信息失败
            }

            override fun onCancel() {

            }

            override fun onWarning(p0: Int) {

            }
        })


    }

    //QQ分享网页

    fun shareQQ(
        context: QQThirdActivity,
        mTencent: Tencent,
        title: String,
        desc: String,
        imgUrl: String,
        url: String
    ) {
        val params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)  //分享类型  分享网页
        params.putString(
            QQShare.SHARE_TO_QQ_TITLE,
            title
        ) // 标题  限制(长度 128 )QQ_SHARE_TITLE_MAX_LENGTH
        params.putString(
            QQShare.SHARE_TO_QQ_SUMMARY,
            desc
        ) // 摘要限制(512) QQ_SHARE_SUMMARY_MAX_LENGTH = 512
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url) // 内容地址 最好控制下长度  长连接报过错

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl) //  网络图片地址(本地地址)

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "xxx") // 应用名称
        // 分享操作要在主线程中完成
        mTencent.shareToQQ(context, params, object : IUiListener {
            override fun onComplete(p0: Any?) {
                // todo 分享成功
            }

            override fun onError(p0: UiError?) {
                // todo 分享失败
            }

            override fun onCancel() {
            }

            override fun onWarning(p0: Int) {
            }
        })

    }

    // 分享图片
    fun shareQQImg(qqThirdActivity: QQThirdActivity, mTencent: Tencent, path: String) {
        val params = Bundle()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path) //分享图片地址 最好做个限制大小
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "xxxApp")// 应用名称
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)//分享类型  分享图片
        // 分享操作要在主线程中完成
        mTencent.shareToQQ(qqThirdActivity, params, object : IUiListener {
            override fun onComplete(p0: Any?) {
                // todo 分享成功
            }

            override fun onError(p0: UiError?) {
                // todo 分享失败
            }

            override fun onCancel() {
            }

            override fun onWarning(p0: Int) {
            }
        })
    }


}