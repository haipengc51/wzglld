package com.jiekai.wzglld.ui.record;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordDeviceAdapter;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicelogsortEntity;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.PankuDataEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.uiUtils.TypeUtils;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2018/1/14.
 * 档案查询->设备使用记录查询
 */

public class RecordDeviceUseActivity extends NFCBaseActivity implements View.OnClickListener,
        TypeUtils.SBBHClick, AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    ListView listView;

    private TextView deviceLeibie;
    private TextView deviceXinghao;
    private TextView deviceGuige;
    private TextView deviceId;
    private TextView readCard;
    private TextView saoMa;

    private TypeUtils typeUtils;
    private AlertDialog alertDialog;
    private RecordDeviceAdapter adapter;
    private List<DevicelogsortEntity> dataList = new ArrayList();

    private DevicesortEntity currentGuiGe;

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_use);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.device_use_record_find));

        View headerView = LayoutInflater.from(this).inflate(R.layout.type_choose_layout, null);
        deviceLeibie = (TextView) headerView.findViewById(R.id.device_leibie);
        deviceXinghao = (TextView) headerView.findViewById(R.id.device_xinghao);
        deviceGuige = (TextView) headerView.findViewById(R.id.device_guige);
        deviceId = (TextView) headerView.findViewById(R.id.device_id);
        readCard = (TextView) headerView.findViewById(R.id.read_card);
        saoMa = (TextView) headerView.findViewById(R.id.sao_ma);

        listView.addHeaderView(headerView);

        back.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        typeUtils = new TypeUtils(mActivity, deviceLeibie, deviceXinghao, deviceGuige, deviceId);
        typeUtils.setSbbhClick(this);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
        if (adapter == null) {
            adapter = new RecordDeviceAdapter(mActivity, dataList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void getNfcData(String nfcString) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        nfcEnable = false;
        getDeviceDataById(nfcString);
        getRecordListById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.read_card:
                nfcEnable = true;
                alertDialog.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            DevicelogsortEntity item = (DevicelogsortEntity) parent.getItemAtPosition(position);
            if (item != null) {
                String leibie = deviceLeibie.getText().toString();
                String xinghao = deviceXinghao.getText().toString();
                String guige = deviceGuige.getText().toString();
                String bh = deviceId.getText().toString();
                if (StringUtils.isEmpty(leibie) || StringUtils.isEmpty(xinghao) ||
                        StringUtils.isEmpty(guige)) {
                    alert(R.string.please_choose_device);
                    return;
                }
                RecordDeviceUseDetailListActivity.start(mActivity, leibie, xinghao, currentGuiGe, bh, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickGuige(DevicesortEntity guige) {
        currentGuiGe = guige;
        getRecordListByGG(guige);
    }

    @Override
    public void clickSBBH(String sbbh) {
        getRecordListByBH(sbbh);
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
        DBManager.NewDbDeal(DBManager.SELECT)
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
     * 通过id获取记录列表
     *
     * @param cardId
     */
    private void getRecordListById(String cardId) {
        if (StringUtils.isEmpty(cardId)) {
            alert(R.string.get_id_err);
            return;
        }
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.Get_Record_List)
                .params(new String[]{cardId, cardId, cardId})
                .clazz(DevicelogsortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            getRecordList(result);
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 通过id获取记录列表
     *
     * @param sbbh
     */
    private void getRecordListByBH(String sbbh) {
        if (StringUtils.isEmpty(sbbh)) {
            alert(R.string.get_bh_faild);
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.Get_Record_List_by_BH)
                .params(new String[]{sbbh})
                .clazz(DevicelogsortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            getRecordList(result);
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    private void getRecordListByGG(DevicesortEntity devicesortEntity) {
        if (devicesortEntity != null && StringUtils.isEmpty(devicesortEntity.getCOOD())) {
            alert(R.string.get_bh_faild);
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.Get_Record_List_by_GG)
                .params(new Object[]{devicesortEntity.getCOOD()})
                .clazz(DevicelogsortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            getRecordList(result);
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 从数据库中发现了设备的列表
     * @param result
     */
    private void getRecordList(List<DevicelogsortEntity> result) {
        dataList.clear();
        dataList.addAll(result);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            getRecordListById(code);
        }
    }
}
