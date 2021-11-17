package com.wu.third.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wu.third.ThirdUtil;
import com.wu.third.wxapi.util.Constant;
import com.wu.third.wxapi.util.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: 吴奎庆
 * <p>
 * 时间: 2018/8/23
 * <p>
 * 简介:
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constant.INSTANCE.getAPP_WECHAT_KEY(), false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


    private SendAuth.Resp resp;

    @Override
    public void onResp(BaseResp baseResp) {

        //微信登录为getType为1，分享为0
        if (baseResp.getType() == 1) {
            //登录回调
            resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    //获取用户信息
                    ThirdUtil.INSTANCE.getAccessToken(code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    showMessage("用户拒绝授权");
                    this.finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    showMessage( "用户取消绑定");
                    this.finish();
                    break;
                default:
                    this.finish();
                    break;
            }
        }else if (baseResp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
            String result = "";
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "支付成功";

                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "支付取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "支付失败";
                    break;
                default:
                    result = "支付失败";
                    break;
            }
            showMessage( result);
            this.finish();

        }else {
            String result = "";
            switch (baseResp.errCode) {
                //现阶段 取消分享以及分享成功默认返回的都是分享成功 逻辑需求请注意
                case BaseResp.ErrCode.ERR_OK:
                    result = "分享成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "分享取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "分享失败";
                    break;
                default:
                    result = "分享失败";
                    break;
            }
            showMessage(result);
            this.finish();
        }


    }

    private  void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }



    String access = null;
    //获取用户token
//    private void getAccessToken(String code) {
//        String getTokenUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
//                        "appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constant.INSTANCE.getAPP_WECHAT_KEY(),
//                Constant.INSTANCE.getAPP_WECHAT_SERECT(), code);
//        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
//
//                String openId = null;
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    access = jsonObject.getString("access_token");
//                    openId = jsonObject.getString("openid");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //获取个人信息
//                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId + "";
//                OkHttpUtils.ResultCallback<WeChatInfo> resultCallback = new OkHttpUtils.ResultCallback<WeChatInfo>() {
//                    @Override
//                    public void onSuccess(WeChatInfo response) {
//                        response.setErrCode(resp.errCode);
//                        response.setAccessToken(access);
//                        if (response != null) WechatLoginObservable.getInstance().update(response);
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                OkHttpUtils.get(getUserInfo, resultCallback);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//            }
//        };
//        OkHttpUtils.get(getTokenUrl, resultCallback);
//
//    }


}
