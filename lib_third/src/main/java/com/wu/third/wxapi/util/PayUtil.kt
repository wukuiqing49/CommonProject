package com.wu.third.wxapi.util

import android.content.Context
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory

object PayUtil {
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