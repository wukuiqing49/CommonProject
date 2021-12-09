package com.wu.view.util;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.wu.view.R;

import java.util.List;

public class LinkDialogUtil {

    public static Dialog showButtonsDialog(Context context, List<String> items, final DialogButtonsClick click) {
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_buttons, null);

        ListView listView = view.findViewById(R.id.items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(getScreenWidth(context) - dp2px(context, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (click != null)
                    click.onButtonsClick(i, dialog);
            }
        });
        dialog.addContentView(view, vl);
        dialog.show();
        return dialog;
    }

    public interface DialogButtonsClick {
        void onButtonsClick(int position, Dialog dialog);
    }

    /**
     * 获取手机宽度的分辨率
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
