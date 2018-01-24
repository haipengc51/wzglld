package com.jiekai.wzglld.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiekai.wzglld.R;


/**
 * Created by laowu on 2017/12/4.
 * glide加载图片工具类
 */

public class GlidUtils {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void displayImage(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.drawable.ic_image_err);
            requestOptions.placeholder(R.drawable.ic_image_err);
            Glide.with(activity).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
        }
    }
}
