package com.wu.view.util;

import android.view.View;

/**
 * Created by cnlive201803 on 2018/5/8.
 */

public interface TouchableSpan {
    void setPressed(boolean pressed);

    void onClick(View widget);

    void onLongClick(View widget);
}
