package com.jiekai.wzglld.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DeviceEntity;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.PankuDataEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.popup.DeviceCodePopup;
import com.jiekai.wzglld.ui.popup.DeviceNamePopup;
import com.jiekai.wzglld.ui.uiUtils.TypeUtils;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.GlidUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.zxing.CaptureActivity;
import com.luck.picture.lib.PictureSelector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/11.
 * 领导--设备信息查询
 */

public class QueryDeviceInfoActivity extends NFCBaseActivity implements View.OnClickListener {

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
    private AlertDialog alertDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_query_device_info);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.query_device_info));

        back.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        enter.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        typeUtils = new TypeUtils(this, deviceLeibie, deviceXinghao, deviceGuige, deviceId);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
    }

    @Override
    public void getNfcData(String nfcString) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        nfcEnable = false;
        //读取设备自编码和设备名称使用井号
        getDeviceDataById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.read_card:
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.enter:

                break;
        }
    }

    /**
     * 通过ID卡号获取设备信息
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {  //扫码回到
            String code = data.getExtras().getString("result");
            getDeviceDataById(code);
        }
    }
}
