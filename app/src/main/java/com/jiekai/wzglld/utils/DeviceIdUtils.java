package com.jiekai.wzglld.utils;

import android.app.Activity;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Created by LaoWu on 2018/3/9.
 * 外置读卡器读到数据和实际的数据变化的类
 */

public class DeviceIdUtils {
    /**
     * 读卡器读出来的高低位进行转换
     */
    public static String reverseDeviceId(String fromDeviceId) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=fromDeviceId.length()-1; i>0; i-=2) {
            stringBuilder.append(fromDeviceId.charAt(i-1));
            stringBuilder.append(fromDeviceId.charAt(i));
        }
        return stringBuilder.toString();
    }

    /**
     * 点击EditText获取到焦点，设置软键盘是否也同步弹出，清空editText的内容
     * @param isShowOnClick
     */
    public static void setEditSoftKeywordShow(Activity mActivity, EditText editText, boolean isShowOnClick) {
        editText.setText("");
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            if (isShowOnClick) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                editText.setInputType(InputType.TYPE_NULL);
            }
        } else {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(isShowOnClick);
                setSoftInputShownOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
