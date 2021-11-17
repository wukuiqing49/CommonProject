package com.wu.third.wxapi;


import java.util.Observable;

/**
 * 作者: 吴奎庆
 * <p>
 * 时间: 2019/9/10
 * <p>
 * 简介: 微信三方登陆的观察者
 *
 */
public class WechatLoginObservable extends Observable {
    static WechatLoginObservable wechatLoginObservable;

    public static WechatLoginObservable getInstance() {
        if (wechatLoginObservable == null)
            synchronized (WechatLoginObservable.class) {
                if (wechatLoginObservable == null)
                    wechatLoginObservable = new WechatLoginObservable();
            }
        return wechatLoginObservable;
    }


    public void update(WeChatInfo wechatInfo) {
        setChanged();
        notifyObservers(wechatInfo);
    }

}
