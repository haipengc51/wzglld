package com.jiekai.wzglld.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by laowu on 2017/12/4.
 * glide加载图片工具类
 */

public class GlidUtils {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void displayImage(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).into(imageView);
        }
    }
}
