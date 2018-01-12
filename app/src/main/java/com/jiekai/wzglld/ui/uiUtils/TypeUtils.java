package com.jiekai.wzglld.ui.uiUtils;

import android.app.Activity;
import android.widget.TextView;

import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.ui.popup.DeviceCodePopup;
import com.jiekai.wzglld.ui.popup.DeviceNamePopup;
import com.jiekai.wzglld.utils.StringUtils;

/**
 * Created by laowu on 2018/1/12.
 * 类型选择的实体类
 */

public class TypeUtils {
    private Activity activity;
    private TextView deviceLeibie;
    private TextView deviceXinghao;
    private TextView deviceGuige;
    private TextView deviceId;

    private DeviceNamePopup deviceLeibiePopup;
    private DeviceNamePopup deviceXinghaoPopup;
    private DeviceNamePopup deviceGuigePopup;
    private DeviceCodePopup deviceCodePopup;

    private DevicesortEntity currentLeibie;
    private DevicesortEntity currentXinghao;
    private DevicesortEntity currentGuige;
    private String currentDeviceCode = null;    //选中设备的自编号

    public TypeUtils(Activity activity,
                     TextView deviceLeibie, TextView deviceXinghao,
                     TextView deviceGuige, TextView deviceId) {
        this.activity = activity;
        this.deviceLeibie = deviceLeibie;
        this.deviceXinghao = deviceXinghao;
        this.deviceGuige = deviceGuige;
        this.deviceId = deviceId;

        init(activity);
    }

    private void init(Activity activity) {
        deviceLeibiePopup = new DeviceNamePopup(activity, deviceLeibie, leibieClick);
        deviceXinghaoPopup = new DeviceNamePopup(activity, deviceXinghao, xinghaoClick);
        deviceGuigePopup = new DeviceNamePopup(activity, deviceGuige, guigeClick);
        deviceCodePopup = new DeviceCodePopup(activity, deviceId, deviceidClick);
    }

    private DeviceNamePopup.OnDeviceNameClick leibieClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentLeibie = devicesortEntity;
            deviceXinghao.setText("");
            deviceGuige.setText("");
            deviceId.setText("");
        }
    };

    private DeviceNamePopup.OnDeviceNameClick xinghaoClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentXinghao = devicesortEntity;
            deviceGuige.setText("");
            deviceId.setText("");
        }
    };

    private DeviceNamePopup.OnDeviceNameClick guigeClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentGuige = devicesortEntity;
            deviceId.setText("");
        }
    };

    private DeviceCodePopup.OnDeviceCodeClick deviceidClick = new DeviceCodePopup.OnDeviceCodeClick(){

        @Override
        public void OnDeviceCodeClick(String deviceCode) {
            currentDeviceCode = deviceCode;
        }
    };

    public String getCurrentDeviceCode() {
        return  currentDeviceCode;
    }
}
