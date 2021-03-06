package com.wu.third

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.tencent.connect.UnionInfo
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.connect.share.QQShare
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.open.SocialConstants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.wu.third.wxapi.WeChatInfo
import com.wu.third.wxapi.WechatLoginObservable
import com.wu.third.wxapi.util.Constant
import com.wu.third.wxapi.util.Constant.APP_WECHAT_KEY
import com.wu.third.wxapi.util.Constant.APP_WECHAT_SERECT
import com.wu.third.wxapi.util.OkHttpUtils
import org.json.JSONException
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

    /**
     * 判断是否安装了微信客户端
     */
    fun isWXAppInstalledAndSupported(context: Context?): Boolean {
        val api = WXAPIFactory.createWXAPI(context, APP_WECHAT_KEY)
        return api.isWXAppInstalled
    }

    //登录微信
    fun loginWechat(mContext: Context) {
        if (isWXAppInstalledAndSupported(mContext)) {
            Thread {
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = ""
                val iwxapi = WXAPIFactory.createWXAPI(mContext, APP_WECHAT_KEY, true)
                iwxapi.registerApp(APP_WECHAT_KEY)
                iwxapi.sendReq(req)
            }.start()
        } else {
        }
    }


    var access: String? = null


    //获取用户token
    fun getAccessToken(code: String) {
        val getTokenUrl = String.format(
            "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=%s&secret=%s&code=%s&grant_type=authorization_code", APP_WECHAT_KEY,
            APP_WECHAT_SERECT, code
        )
        val resultCallback: OkHttpUtils.ResultCallback<String> =
            object : OkHttpUtils.ResultCallback<String>() {
                override fun onSuccess(response: String) {
                    var openId: String? = null
                    try {
                        val jsonObject = JSONObject(response)
                        access = jsonObject.getString("access_token")
                        openId = jsonObject.getString("openid")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    getWechatUserInfo(openId)
                }

                override fun onFailure(e: java.lang.Exception) {
                    // todo 异常处理
                }
            }
        OkHttpUtils.get(getTokenUrl, resultCallback)
    }

    //获取微信用户信息
    private fun getWechatUserInfo(openId: String?) {

        //获取个人信息
        val getUserInfo =
            "https://api.weixin.qq.com/sns/userinfo?access_token=$access&openid=$openId"
        val resultCallback: OkHttpUtils.ResultCallback<WeChatInfo> =
            object : OkHttpUtils.ResultCallback<WeChatInfo>() {
                override fun onSuccess(response: WeChatInfo) {
                    response.errCode = response.errCode
                    response.accessToken = access
                    //获取到微信用户信息
                    if (response != null) WechatLoginObservable.getInstance().update(response)
                }

                override fun onFailure(e: java.lang.Exception) {
                    // todo 异常处理
                }
            }
        OkHttpUtils.get(getUserInfo, resultCallback)
    }

    //微信分享
    fun shareWechat(
        mContext: Context,
        url: String,
        title: String,
        desc: String,
        thumbBmp: Bitmap?
    ) {
        var webpage = WXWebpageObject()
        val api = WXAPIFactory.createWXAPI(mContext, Constant.APP_WECHAT_KEY)
        //长度小于 10240
        webpage.webpageUrl = url
        //分享的内容
        var msg = WXMediaMessage(webpage)
        //长度限制 512
        msg.title = title
        //长度限制 1024
        msg.description = desc
        //byty[] 限制 65536 预览图
        msg.setThumbImage(thumbBmp)
        val req = SendMessageToWX.Req()
        req.message = msg
        //分享类型
        req.scene = SendMessageToWX.Req.WXSceneSession
        api.sendReq(req) //发送到微信
    }

    fun shareWechatImg(mContext: Context, bmp: Bitmap?) {

        val api = WXAPIFactory.createWXAPI(mContext, Constant.APP_WECHAT_KEY)
        //初始化 WXImageObject 和 WXMediaMessage 对象
        val imgObj = WXImageObject(bmp)
        val msg = WXMediaMessage()

        msg.mediaObject = imgObj
        val thumbBmp = Bitmap.createScaledBitmap(bmp!!, 120, 120, true)
        bmp.recycle()
        // 限制 65536 预览图
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true)


        //构造一个Req

        //构造一个Req
        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("img")
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneSession

        //调用api接口，发送数据到微信
        api.sendReq(req)
    }

    fun buildTransaction(type: String?): String? {
        return if (type == null) System.currentTimeMillis()
            .toString() else type + System.currentTimeMillis()
    }

    //微信支付 数据需要后台生成预订单的时候返回支付的数据(放后台做前端不做)
    fun payWechat(
        mContext: Context,
        appId: String,
        partnerId: String,
        prepayId: String,
        nonceStr: String,
        timeStamp: String,
        packageValue: String,
        sign: String,
        extData: String
    ) {
        var api = WXAPIFactory.createWXAPI(mContext, Constant.APP_WECHAT_KEY);
        api.registerApp(Constant.APP_WECHAT_KEY)

        var req = PayReq()
        req.appId = appId
        req.partnerId = partnerId
        req.prepayId = prepayId
        req.nonceStr = nonceStr
        req.timeStamp = timeStamp
        req.packageValue = "Sign=WXPay"
        req.sign = sign
        req.extData = extData
        api.sendReq(req)
    }
}