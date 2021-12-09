package com.wu.view.util;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author wkq
 *
 * @date 2021年12月08日 12:38
 *
 *@des 兼容链接的TextView
 *
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {

    private static LinkTouchMovementMethod sInstance;
    private static LinkTouchDecorHelper sHelper = new LinkTouchDecorHelper();

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (sHelper.onTouchEvent(widget, buffer, event)) return true;
        if (Touch.onTouchEvent(widget, buffer, event)) return true;
//        if (super.onTouchEvent(widget, buffer, event)) return true;
//        if (((ViewGroup) widget.getParent()).onTouchEvent(event)) return true;
        return false;
    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LinkTouchMovementMethod();
        return sInstance;
    }
}
