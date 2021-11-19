package com.wu.third.classification

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller


/**
 * @author wkq
 *
 * @date 2021年11月19日 11:41
 *
 *@des
 *
 */

class TopLinearSmoothScroller(context: Context) : LinearSmoothScroller(context){
    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

}