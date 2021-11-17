package com.wu.third.wxapi;


import java.util.Observable;

/**
 * 作者: 吴奎庆
 * <p>
 * 时间: 2019/9/10
 * <p>
 * 简介: 支付观察者
 *
 */
public class WechatPayObservable extends Observable {
    static WechatPayObservable wechatPayObservable;

    public static WechatPayObservable getInstance() {
        if (wechatPayObservable == null)
            synchronized (WechatPayObservable.class) {
                if (wechatPayObservable == null)
                    wechatPayObservable = new WechatPayObservable();
            }
        return wechatPayObservable;
    }


    public void update(int state) {
        setChanged();
        notifyObservers(state);
    }

}
