package com.wu.third.classification

import java.util.*


/**
 * @author wkq
 *
 * @date 2021年11月18日 16:25
 *
 *@des  刷新完成的 观察者  type 为预留的各种状态
 *
 */

object ClassificationObservable : Observable() {

    fun update(
        info
        : ClassificationObservableInfo
    ) {
        setChanged()
        notifyObservers(info)
    }

}