package com.jiekai.wzglld.ui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordDeviceRepairAdapter;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.entity.PankuDataEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.uiUtils.TypeUtils;
import com.jiekai.wzglld.ui.uiUtils.XListViewUtils;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.DeviceIdUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.zxing.CaptureActivity;
import com.jiekai.wzglld.weight.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2018/1/14.
 */

public class RecordDeviceRepairActivity extends NFCBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    XListView listView;

    private TextView deviceLeibie;
    private TextView deviceXinghao;
    private TextView deviceGuige;
    private TextView deviceId;
    private TextView readCard;
    private TextView saoMa;
    private TextView filtrate;

    private View filtrateView;
    private Dialog filtrateDialog;
    private XListViewUtils xListViewUtils;

    private TypeUtils typeUtils;

    private RecordDeviceRepairAdapter adapter;
    private List<DevicestoreEntity> dataList = new ArrayList();

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_repair);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_repair));
        back.setVisibility(View.VISIBLE);
        menu.setVisibility(View.VISIBLE);

        filtrateView = LayoutInflater.from(this).inflate(R.layout.header_record_type_choose, null);
        deviceLeibie = (TextView) filtrateView.findViewById(R.id.device_leibie);
        deviceXinghao = (TextView) filtrateView.findViewById(R.id.device_xinghao);
        deviceGuige = (TextView) filtrateView.findViewById(R.id.device_guige);
        deviceId = (TextView) filtrateView.findViewById(R.id.device_id);
        readCard = (TextView) filtrateView.findViewById(R.id.read_card);
        saoMa = (TextView) filtrateView.findViewById(R.id.sao_ma);
        filtrate = (TextView) filtrateView.findViewById(R.id.filtrate);

        filtrateDialog = new Dialog(mActivity);
        filtrateDialog.setContentView(filtrateView);

        View headerView = LayoutInflater.from(this).inflate(R.layout.adapter_repair_device_detail, null);
        listView.addHeaderView(headerView);

        back.setOnClickListener(this);
        menu.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        filtrate.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        typeUtils = new TypeUtils(mActivity, deviceLeibie, deviceXinghao, deviceGuige, deviceId);

        xListViewUtils = new XListViewUtils(listView);
        if (adapter == null) {
            adapter = new RecordDeviceRepairAdapter(mActivity, dataList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        xListViewUtils.setMyBaseAdapter(adapter);
        filtrate(false);
    }

    @Override
    public void getNfcData(String nfcString) {
        if (deviceReadcardDialog != null && deviceReadcardDialog.isShowing()) {
            deviceReadcardDialog.dismiss();
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
            case R.id.menu:
                filtrateDialog.show();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.read_card:
                nfcEnable = true;
                deviceReadcardDialog.show();
                DeviceIdUtils.setEditSoftKeywordShow(this, deviceReadcardEdit, false);
                break;
            case R.id.filtrate:
                filtrate(true);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DevicestoreEntity item = (DevicestoreEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, RecordDeviceRepairDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, item);
            startActivity(intent);
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
                            typeUtils.setCurrentDeviceCode(pankuDataEntity.getBH());
                            filtrate(false);
                            dismissFiltrateDialog();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 通过二维码获取设备信息
     * @param id
     */
    private void getDeviceDataBySAOMA(String id) {
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
                            typeUtils.setCurrentDeviceCode(pankuDataEntity.getBH());
                            filtrate(false);
                            dismissFiltrateDialog();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 执行分页的筛选操作
     * @param isClickIn 是否点击的，如果是点击就判断点击是否选择了条件，如果没有提示用户
     */
    private void filtrate(boolean isClickIn) {
        DevicesortEntity leibie = typeUtils.getLeibie();
        DevicesortEntity xinghao = typeUtils.getXinghao();
        DevicesortEntity guige = typeUtils.getGuige();
        String sbbh = typeUtils.getCurrentDeviceCode();

        if (isClickIn && leibie == null && xinghao == null && guige == null && StringUtils.isEmpty(sbbh)) {
            alert(R.string.please_choose_filtrate);
            return;
        } else {
            dismissFiltrateDialog();
        }

        xListViewUtils.setSqlUrl(SqlUrl.GetDeviceRepairPage);
        xListViewUtils.clearParams();
        if (!StringUtils.isEmpty(sbbh)) {
            xListViewUtils.addParams(" AND devicestore.SBBH = ?", sbbh);
        } else if (guige != null) {
            xListViewUtils.addParams(" AND dv.GG = ?", guige.getCOOD());
        } else if (xinghao != null) {
            xListViewUtils.addParams(" AND dv.XH = ?", xinghao.getCOOD());
        } else if (leibie != null) {
            xListViewUtils.addParams(" AND dv.LB = ?", leibie.getCOOD());
        }
        xListViewUtils.addParams(" AND (devicestore.LB = ?", "3");
        xListViewUtils.addParams(" OR devicestore.LB = ?", "4");
        xListViewUtils.addParams(" OR devicestore.LB = ?)", "5");
        xListViewUtils.setClazz(DevicestoreEntity.class);
//        listView.showHeaderView();
        xListViewUtils.onRefresh();
    }

    private void dismissFiltrateDialog(){
        if (filtrateDialog != null && filtrateDialog.isShowing()) {
            filtrateDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            getDeviceDataBySAOMA(code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissFiltrateDialog();
    }
}
