package com.jiekai.wzglld.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.popup.DeviceCodePopup;
import com.jiekai.wzglld.ui.popup.DeviceNamePopup;
import com.jiekai.wzglld.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/11.
 * 领导--设备信息查询
 */

public class QueryDeviceInfoActivity extends NFCBaseActivity implements DeviceCodePopup.OnDeviceCodeClick {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.device_leibie)
    TextView deviceLeibie;
    @BindView(R.id.device_xinghao)
    TextView deviceXinghao;
    @BindView(R.id.device_guige)
    TextView deviceGuige;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.sao_ma)
    TextView saoMa;
    @BindView(R.id.cancle)
    TextView cancle;

    private DeviceNamePopup deviceLeibiePopup;
    private DeviceNamePopup deviceXinghaoPopup;
    private DeviceNamePopup deviceGuigePopup;
    private DeviceCodePopup deviceCodePopup;

    private DevicesortEntity currentLeibie;
    private DevicesortEntity currentXinghao;
    private DevicesortEntity currentGuige;
    private String currentDeviceCode = null;    //选中设备的自编号

    @Override
    public void initView() {
        setContentView(R.layout.activity_query_device_info);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {
        deviceLeibiePopup = new DeviceNamePopup(this, deviceLeibie, leibieClick);
        deviceXinghaoPopup = new DeviceNamePopup(this, deviceXinghao, xinghaoClick);
        deviceGuigePopup = new DeviceNamePopup(this, deviceGuige, guigeClick);
        deviceCodePopup = new DeviceCodePopup(this, deviceId, this);
    }

    @Override
    public void getNfcData(String nfcString) {

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

    @Override
    public void OnDeviceCodeClick(String deviceCode) {
        currentDeviceCode = deviceCode;
        if (!StringUtils.isEmpty(currentDeviceCode)) {
            findDeviceByID(currentDeviceCode);
        }
    }
}
