package com.jiekai.wzglld.ui.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiekai.wzglld.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by laowu on 2018/1/16.
 */

public class FiltratePopUtils {
    private Context context;
    private PopupWindow popupWindow;

    public FiltratePopUtils(Context context, View view) {
        this.context = context;

        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(MATCH_PARENT);
        popupWindow.setHeight(WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.fade_in_popup);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showDropDown(View parentView) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAsDropDown(parentView);//, Gravity.BOTTOM, 0, parentView.getHeight());
        }
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
