package com.wu.view.util;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
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

public class LinkTouchDecorHelper {
    private TouchableSpan mPressedSpan;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(true);
                //解决点击连接文字滚动问题
//                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
//                        spannable.getSpanEnd(mPressedSpan));
            }
            if (textView instanceof SpanTouchFix) {
                final SpanTouchFix tv = (SpanTouchFix) textView;
                tv.setTouchSpanHit(mPressedSpan != null);
            }
            return mPressedSpan != null;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan.setPressed(false);
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
            if (textView instanceof SpanTouchFix) {
                SpanTouchFix tv = (SpanTouchFix) textView;
                tv.setTouchSpanHit(mPressedSpan != null);
            }
            return mPressedSpan != null;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchSpanHint = false;
            if (mPressedSpan != null) {
                touchSpanHint = true;
                mPressedSpan.setPressed(false);
                mPressedSpan.onClick(textView);
            }

            mPressedSpan = null;
            Selection.removeSelection(spannable);
            if (textView instanceof SpanTouchFix) {
                SpanTouchFix tv = (SpanTouchFix) textView;
                tv.setTouchSpanHit(touchSpanHint);
            }
            return touchSpanHint;
        } else {
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(false);
            }
            if (textView instanceof SpanTouchFix) {
                SpanTouchFix tv = (SpanTouchFix) textView;
                tv.setTouchSpanHit(false);
            }
            Selection.removeSelection(spannable);
            return false;
        }

    }

    public static TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return getPressedSpan(textView, spannable, x, y);
    }

    public static TouchableSpan getPressedSpan(TextView textView, Spannable spannable, int x, int y) {

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);
        if (x < layout.getLineLeft(line) || x > layout.getLineRight(line)) {
            // 实际上没点到任何内容
            off = -1;
        }

        TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
        TouchableSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
