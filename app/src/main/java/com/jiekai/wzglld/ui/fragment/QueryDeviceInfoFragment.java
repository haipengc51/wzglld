package com.jiekai.wzglld.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.PankuDataEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.DeviceDetailActivity;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;
import com.jiekai.wzglld.ui.uiUtils.TypeUtils;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.DeviceIdUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.zxing.CaptureActivity;

import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LaoWu on 2018/1/22.
 */

public class QueryDeviceInfoFragment extends MyNFCBaseFragment implements View.OnClickListener {
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
    @BindView(R.id.enter)
    TextView enter;

    private TypeUtils typeUtils;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_query_device_info, container, false);
    }

    @Override
    public void initData() {
        back.setVisibility(View.INVISIBLE);
        title.setText(getResources().getString(R.string.query_device_info));

        back.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        enter.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        typeUtils = new TypeUtils(mActivity, deviceLeibie, deviceXinghao, deviceGuige, deviceId);
    }

    @Override
    protected void getNfcData(String nfcString) {
        if (deviceReadcardDialog != null) {
            deviceReadcardDialog.dismiss();
        }
        ((NFCBaseActivity) getActivity()).nfcEnable = false;
        enableNfc = false;
        //读取设备自编码和设备名称使用井号
        getDeviceDataById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_card:
                if (getActivity() instanceof NFCBaseActivity) {
                    ((NFCBaseActivity) getActivity()).nfcEnable = true;
                    enableNfc = true;
                    deviceReadcardDialog.show();
                    DeviceIdUtils.setEditSoftKeywordShow(getActivity(), deviceReadcardEdit, false);
                } else {
                    alert(R.string.dont_allow_readcard);
                }
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.enter:
                startDetail();
                break;
        }
    }

    private void startDetail() {
        if (StringUtils.isEmpty(deviceId.getText().toString())) {
            alert(R.string.please_choose_device);
            return;
        }
        DeviceDetailActivity.start(mActivity, deviceId.getText().toString());
    }

    /**
     * 通过ID卡号获取设备信息
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        deviceLeibie.setText("");
        deviceXinghao.setText("");
        deviceGuige.setText("");
        deviceId.setText("");
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetPanKuDataByID)
                .params(new String[]{id, id, id})
                .clazz(PankuDataEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_device));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            PankuDataEntity pankuDataEntity = (PankuDataEntity) result.get(0);
                            deviceId.setText(CommonUtils.getDataIfNull(pankuDataEntity.getBH()));
                            deviceLeibie.setText(CommonUtils.getDataIfNull(pankuDataEntity.getLeibie()));
                            deviceXinghao.setText(CommonUtils.getDataIfNull(pankuDataEntity.getXinghao()));
                            deviceGuige.setText(CommonUtils.getDataIfNull(pankuDataEntity.getGuige()));
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 通过ID卡号获取设备信息
     * @param id
     */
    private void getDeviceDataBySaoMa(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        deviceLeibie.setText("");
        deviceXinghao.setText("");
        deviceGuige.setText("");
        deviceId.setText("");
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetPanKuDataBySAOMA)
                .params(new String[]{id})
                .clazz(PankuDataEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_device));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            PankuDataEntity pankuDataEntity = (PankuDataEntity) result.get(0);
                            deviceId.setText(CommonUtils.getDataIfNull(pankuDataEntity.getBH()));
                            deviceLeibie.setText(CommonUtils.getDataIfNull(pankuDataEntity.getLeibie()));
                            deviceXinghao.setText(CommonUtils.getDataIfNull(pankuDataEntity.getXinghao()));
                            deviceGuige.setText(CommonUtils.getDataIfNull(pankuDataEntity.getGuige()));
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {  //扫码回到
            String code = data.getExtras().getString("result");
            getDeviceDataBySaoMa(code);
        }
    }
}
