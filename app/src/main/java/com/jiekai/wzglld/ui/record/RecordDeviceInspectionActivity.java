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
import com.jiekai.wzglld.adapter.RecordDeviceInspectionAdapter;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DeviceinspectionEntity;
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
 */

public class RecordDeviceInspectionActivity extends NFCBaseActivity implements View.OnClickListener,
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

    private RecordDeviceInspectionAdapter adapter;
    private List<DeviceinspectionEntity> dataList = new ArrayList();

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_inspection);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_inspection));
        back.setVisibility(View.VISIBLE);

        View headerView = LayoutInflater.from(this).inflate(R.layout.header_record_type_choose, null);
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
            adapter = new RecordDeviceInspectionAdapter(mActivity, dataList);
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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
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
        DeviceinspectionEntity item = (DeviceinspectionEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, RecordDeviceInspectionDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, item);
            startActivity(intent);
        }
    }

    @Override
    public void clickSBBH(String sbbh) {
        getDeviceInspectionListBySBBH(sbbh);
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
                            getDeviceInspectionListBySBBH(pankuDataEntity.getBH());
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    private void getDeviceInspectionListBySBBH(String sbbh) {
        if (StringUtils.isEmpty(sbbh)) {
            alert(R.string.please_choose_device);
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_INSPECTION_RECORD)
                .params(new String[]{sbbh})
                .clazz(DeviceinspectionEntity.class)
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
                            dataList.clear();
                            dataList.addAll(result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            getDeviceDataById(code);
        }
    }
}
