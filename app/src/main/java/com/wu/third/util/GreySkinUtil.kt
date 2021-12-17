package com.wu.third.util

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View
import com.wu.base.util.SharedPreferencesHelper

/**
 * @author wkq
 * @date 2021年12月17日 11:08
 * @des
 */
object GreySkinUtil {
    /**
     * 设置灰度展示
     * @param activity
     */
    fun showGrey(activity: Activity) {
        val isGrey = SharedPreferencesHelper.getInstance(activity).getBoolean("isGrey", false)
        if (isGrey) {
            val paint = Paint()
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = filter
            activity.window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
        }
    }
}