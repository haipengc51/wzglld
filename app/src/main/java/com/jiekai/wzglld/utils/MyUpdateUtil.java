package com.jiekai.wzglld.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by LaoWu on 2018/2/7.
 * 更新检测类
 */

public class MyUpdateUtil {
    private void update(Context context) {
        int versionCode = getLocalVersion(context);
        if (versionCode != -1) {

        }
    }

//    private int getRemotVersion() {
//
//    }

    private int getLocalVersion(Context context) {
        int versionCode = -1;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
