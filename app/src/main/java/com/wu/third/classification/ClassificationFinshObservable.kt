package com.wu.third.classification

import java.util.*


/**
 * @author wkq
 *
 * @date 2021年11月18日 16:25
 *
 *@des 完成的观察者
 *
 */

object ClassificationFinshObservable:Observable (){

    fun update(position:Int){
        setChanged()
        notifyObservers(position)
    }

}