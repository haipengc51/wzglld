package com.jiekai.wzglld.ui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordDeviceInAdapter;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.entity.PankuDataEntity;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.popup.FiltratePopUtils;
import com.jiekai.wzglld.ui.uiUtils.TypeUtils;
import com.jiekai.wzglld.ui.uiUtils.XListViewUtils;
import com.jiekai.wzglld.utils.CommonUtils;
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

public class RecordDeviceInActivity extends NFCBaseActivity implements View.OnClickListener,
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

    private View headerView;
    private Dialog filtrateDialog;

    private TypeUtils typeUtils;
    private AlertDialog alertDialog;

    private RecordDeviceInAdapter adapter;
    private List<DevicestoreEntity> dataList = new ArrayList();
    private XListViewUtils xListViewUtils;

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_in);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_in));
        back.setVisibility(View.VISIBLE);
        menu.setVisibility(View.VISIBLE);

        headerView = LayoutInflater.from(this).inflate(R.layout.header_record_type_choose, null);
        deviceLeibie = (TextView) headerView.findViewById(R.id.device_leibie);
        deviceXinghao = (TextView) headerView.findViewById(R.id.device_xinghao);
        deviceGuige = (TextView) headerView.findViewById(R.id.device_guige);
        deviceId = (TextView) headerView.findViewById(R.id.device_id);
        readCard = (TextView) headerView.findViewById(R.id.read_card);
        saoMa = (TextView) headerView.findViewById(R.id.sao_ma);
        filtrate = (TextView) headerView.findViewById(R.id.filtrate);

        filtrateDialog = new Dialog(mActivity);
        filtrateDialog.setContentView(headerView);

        back.setOnClickListener(this);
        menu.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        filtrate.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        typeUtils = new TypeUtils(mActivity, deviceLeibie, deviceXinghao, deviceGuige, deviceId);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();

        xListViewUtils = new XListViewUtils(listView);
        if (adapter == null) {
            adapter = new RecordDeviceInAdapter(mActivity, dataList);
            listView.setOnItemClickListener(this);
        }
        xListViewUtils.setMyBaseAdapter(adapter);
        filtrate();
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
            case R.id.menu:
//                Window dialogWindow = filtrateDialog.getWindow();
//                WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
//                layoutParams.y = 0;//v.getTop() + v.getHeight();
//                dialogWindow.setAttributes(layoutParams);
                filtrateDialog.show();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.read_card:
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.filtrate:
                filtrate();
                dismissFiltrateDialog();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DevicestoreEntity item = (DevicestoreEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, RecordDeviceInDetailActivity.class);
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
                            typeUtils.setCurrentDeviceCode(pankuDataEntity.getBH());
                            filtrate();
                            dismissFiltrateDialog();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    private void filtrate() {
        DevicesortEntity leibie = typeUtils.getLeibie();
        DevicesortEntity xinghao = typeUtils.getXinghao();
        DevicesortEntity guige = typeUtils.getGuige();
        String sbbh = typeUtils.getCurrentDeviceCode();

        xListViewUtils.setSqlUrl(SqlUrl.GetDeviceINPage);
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
        xListViewUtils.addParams(" AND devicestore.LB = ?", "1");
        xListViewUtils.setClazz(DevicestoreEntity.class);
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
            getDeviceDataById(code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissFiltrateDialog();
    }
}
