package com.wu.third.application

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.wu.third.util.GreySkinUtil


/**
 * @author wkq
 *
 * @date 2021年11月11日 16:48
 *
 *@des
 *
 */

class CommonAppclication:MultiDexApplication(), ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()
        //注册 Activity 监听
        registerActivityLifecycleCallbacks(this)

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        GreySkinUtil.showGrey(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }


}