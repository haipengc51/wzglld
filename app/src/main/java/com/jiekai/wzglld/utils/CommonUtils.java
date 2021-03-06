package com.jiekai.wzglld.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by LaoWu on 2017/12/8.
 * 公共方法
 */

public class CommonUtils {

    /**
     * 获取屏幕的高度px
     * @param context
     * @return
     */
    public static int getScreentHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int dip2Px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2Dip(Context context, float px) {
        float desity = context.getResources().getDisplayMetrics().density;
        return (int) (px / desity + 0.5f) - 15;
    }

    /**
     * 检查字符串是否为null，如果为null的时候返回"",防止崩溃
     * @param data
     * @return
     */
    public static String getDataIfNull(String data) {
        return StringUtils.isEmpty(data) ? "" : data;
    }

    /**
     * 获取软件版本号
     */

    public static String getVersionName(Context mContext) {
        String versionName = "1.0";
        try {
            versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
