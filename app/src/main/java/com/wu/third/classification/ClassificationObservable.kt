package com.wu.third.classification

import java.util.*


/**
 * @author wkq
 *
 * @date 2021年11月18日 16:25
 *
 *@des
 *
 */

object ClassificationObservable:Observable (){

    fun update(info
               :ClassificationObservableInfo){
        setChanged()
        notifyObservers(info)
    }

}