package com.jiekai.wzglld.ui.fragment.base;

import android.widget.EditText;

/**
 * Created by LaoWu on 2018/3/9.
 */

public interface DeviceCardReaderInterface {
    /**
     * 初始化dialog，dialog中含有一个透明的edittext用了进行读卡器输入
     */
    void initDialog();

    /**
     * 设置edittext的读卡器输入监听
     * @param edit
     */
    void setDeviceIdEdit(EditText edit);

    /**
     * 读卡器读取标签之后进行处理并返回
     */
    void getEditTextString();
}
