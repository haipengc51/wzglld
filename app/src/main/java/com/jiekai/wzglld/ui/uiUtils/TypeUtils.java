package com.jiekai.wzglld.ui.uiUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DeviceBHEntity;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.ui.popup.DeviceCodePopup;
import com.jiekai.wzglld.ui.popup.DeviceNamePopup;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laowu on 2018/1/12.
 * 类型选择的实体类
 */

public class TypeUtils implements View.OnClickListener{
    private Activity activity;
    private TextView deviceLeibie;
    private TextView deviceXinghao;
    private TextView deviceGuige;
    private TextView deviceId;
    private ProgressDialog progressDialog = null;

    private DeviceNamePopup deviceLeibiePopup;
    private DeviceNamePopup deviceXinghaoPopup;
    private DeviceNamePopup deviceGuigePopup;
    private DeviceCodePopup deviceCodePopup;

    private DevicesortEntity currentLeibie;
    private DevicesortEntity currentXinghao;
    private DevicesortEntity currentGuige;
    private String currentDeviceCode = null;    //选中设备的自编号
    private SBBHClick sbbhClick;

    public interface SBBHClick {
        void clickGuige(DevicesortEntity guige);    //规格的点击回调
        void clickSBBH(String sbbh);                //设备自编号的点击回调
    }

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

    public void setSbbhClick(SBBHClick sbbhClick) {
        this.sbbhClick = sbbhClick;
    }

    private void init(Activity activity) {
        deviceLeibiePopup = new DeviceNamePopup(activity, deviceLeibie, leibieClick);
        deviceXinghaoPopup = new DeviceNamePopup(activity, deviceXinghao, xinghaoClick);
        deviceGuigePopup = new DeviceNamePopup(activity, deviceGuige, guigeClick);
        deviceCodePopup = new DeviceCodePopup(activity, deviceId, deviceidClick);

        deviceLeibie.setOnClickListener(this);
        deviceXinghao.setOnClickListener(this);
        deviceGuige.setOnClickListener(this);
        deviceId.setOnClickListener(this);
    }

    private DeviceNamePopup.OnDeviceNameClick leibieClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentLeibie = devicesortEntity;
            deviceXinghao.setText("");
            deviceGuige.setText("");
            deviceId.setText("");
            currentXinghao = null;
            currentGuige = null;
            currentDeviceCode = null;
        }
    };

    private DeviceNamePopup.OnDeviceNameClick xinghaoClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentXinghao = devicesortEntity;
            deviceGuige.setText("");
            deviceId.setText("");
            currentGuige = null;
            currentDeviceCode = null;
        }
    };

    private DeviceNamePopup.OnDeviceNameClick guigeClick = new DeviceNamePopup.OnDeviceNameClick() {
        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentGuige = devicesortEntity;
            deviceId.setText("");
            currentDeviceCode = null;
            if (sbbhClick != null) {
                sbbhClick.clickGuige(devicesortEntity);
            }
        }
    };

    private DeviceCodePopup.OnDeviceCodeClick deviceidClick = new DeviceCodePopup.OnDeviceCodeClick(){

        @Override
        public void OnDeviceCodeClick(String deviceCode) {
            currentDeviceCode = deviceCode;
            if (sbbhClick != null) {
                sbbhClick.clickSBBH(deviceCode);
            }
        }
    };

    /**
     * 返回选中的设备自编号
     * @return
     */
    public String getCurrentDeviceCode() {
        return  currentDeviceCode;
    }

    public void setCurrentDeviceCode(String deviceCode) {
        this.currentDeviceCode = deviceCode;
    }

    /**
     * 返回选中的类别
     * @return
     */
    public DevicesortEntity getLeibie() {
        return currentLeibie;
    }

    public DevicesortEntity getXinghao() {
        return currentXinghao;
    }

    public DevicesortEntity getGuige() {
        return  currentGuige;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_leibie:
                getLeiBie();
                break;
            case R.id.device_xinghao:
                getXingHaoByLeibie();
                break;
            case R.id.device_guige:
                getGuiGeByXingHao();
                break;
            case R.id.device_id:
                getDeviceBHByGuiGe();
                break;
        }
    }

    private void getLeiBie() {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetAllLeiBie)
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(activity.getResources().getString(R.string.loading_leixing));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result != null && result.size() != 0) {
                            deviceLeibiePopup.setPopListData(result);
                            deviceLeibiePopup.showCenter(deviceLeibie);
                        } else {
                            alert(activity.getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void getXingHaoByLeibie() {
        if (currentLeibie == null) {
            alert("请先选择类别");
            return;
        }
        String leibie = currentLeibie.getCOOD();
        if (StringUtils.isEmpty(leibie)) {
            alert(activity.getResources().getString(R.string.params_empty));
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetXingHaoByLeiBie)
                .params(new String[]{leibie})
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(activity.getResources().getString(R.string.loading_leixing));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result != null && result.size() != 0) {
                            deviceXinghaoPopup.setPopListData(result);
                            deviceXinghaoPopup.showCenter(deviceXinghao);
                        } else {
                            alert(activity.getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void getGuiGeByXingHao() {
        if (currentLeibie == null) {
            alert("请先输入类别");
            return;
        }
        if (currentXinghao == null) {
            alert("请先选择型号");
            return;
        }
        String xinghao = currentXinghao.getCOOD();
        if (StringUtils.isEmpty(xinghao)) {
            alert(activity.getResources().getString(R.string.params_empty));
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetGuiGeByXingHao)
                .params(new String[]{xinghao})
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(activity.getResources().getString(R.string.loading_leixing));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result != null && result.size() != 0) {
                            deviceGuigePopup.setPopListData(result);
                            deviceGuigePopup.showCenter(deviceGuige);
                        } else {
                            alert(activity.getResources().getString(R.string.no_data));
                        }
                    }
                });
    }


    /**
     * 通过设备规格加载设备自编号
     **/
    public void getDeviceBHByGuiGe() {
        if (currentLeibie == null) {
            alert("请先选择类别");
            return;
        }
        if (currentXinghao == null) {
            alert("请先选择型号");
            return;
        }
        if (currentGuige == null) {
            alert("请先选择规格");
            return;
        }
        String leibie = currentLeibie.getCOOD();
        String xinghao = currentXinghao.getCOOD();
        String guige = currentGuige.getCOOD();
        deviceId.setText("");
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetBHByLeiBieXinghaoGuige)
                .params(new String[]{leibie, xinghao, guige})
                .clazz(DeviceBHEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(activity.getResources().getString(R.string.loding_device_bh));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result == null || result.size() == 0) {
                            alert(activity.getResources().getString(R.string.no_data));
                            return;
                        }
                        List<String> deviceCodeList = new ArrayList<String>();
                        for (int i = 0; i < result.size(); i++) {
                            deviceCodeList.add(((DeviceBHEntity) result.get(i)).getBH());
                            deviceCodePopup.setPopTitle(activity.getResources().getString(R.string.device_id));
                            deviceCodePopup.setPopListData(deviceCodeList);
                            deviceCodePopup.showCenter(deviceId);
                        }
                    }
                });
    }

    private void showProgressDialog(String msg) {
        dismissProgressDialog();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(activity.getResources().getString(R.string.please_wait));
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void alert(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    private void alert(int strId) {
        Toast.makeText(activity, strId, Toast.LENGTH_SHORT).show();
    }
}
