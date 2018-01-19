package com.jiekai.wzglld.ui.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordDeviceUseDetailAdapter;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicelogEntity;
import com.jiekai.wzglld.entity.DevicelogsortEntity;
import com.jiekai.wzglld.entity.DevicesortEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.uiUtils.XListViewUtils;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.weight.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/14.
 * 设备记录查询->使用记录查询->单个使用记录列表
 */

public class RecordDeviceUseDetailListActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    XListView listView;
    @BindView(R.id.search_input)
    EditText searchInput;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;

    private TextView deviceLeibie;
    private TextView deviceXinghao;
    private TextView deviceGuige;
    private TextView deviceId;

    private String leibie;
    private String xinghao;
    private String guige;
    private DevicesortEntity currentGuige;
    private String id;
    private String JLMC;
    private DevicelogsortEntity devicelogsortEntity;

    private RecordDeviceUseDetailAdapter adapter;
    private List<DevicelogEntity> dataList = new ArrayList<>();

    private boolean isGuiGe = true;
    private XListViewUtils xListViewUtils;

    public static void start(Context context,
                             String leibie, String xinghao, DevicesortEntity guige, String id,
                             DevicelogsortEntity devicelogsortEntity) {
        Intent intent = new Intent(context, RecordDeviceUseDetailListActivity.class);
        intent.putExtra(IntentFlag.LEIBIE, leibie);
        intent.putExtra(IntentFlag.XINGHAO, xinghao);
        intent.putExtra(IntentFlag.GUIGE, guige);
        intent.putExtra(IntentFlag.ID, id);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.DATA, devicelogsortEntity);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_use_detail);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.detail));
        leibie = getIntent().getStringExtra(IntentFlag.LEIBIE);
        xinghao = getIntent().getStringExtra(IntentFlag.XINGHAO);
        id = getIntent().getStringExtra(IntentFlag.ID);
        if (StringUtils.isEmpty(id)) {
            currentGuige = (DevicesortEntity) getIntent().getSerializableExtra(IntentFlag.GUIGE);
            guige = currentGuige.getTEXT();
            isGuiGe = true;
            searchLayout.setVisibility(View.VISIBLE);
        } else {
            isGuiGe = false;
            searchLayout.setVisibility(View.GONE);
        }
        devicelogsortEntity = (DevicelogsortEntity) getIntent().getExtras().getSerializable(IntentFlag.DATA);

        if (devicelogsortEntity != null && !StringUtils.isEmpty(devicelogsortEntity.getJLZLMC())) {
            JLMC = devicelogsortEntity.getJLZLMC();
            title.setText(JLMC);
        }
        back.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
//        View headerView = LayoutInflater.from(this).inflate(R.layout.header_record_detail, null);
//        deviceLeibie = (TextView) headerView.findViewById(R.id.device_leibie);
//        deviceXinghao = (TextView) headerView.findViewById(R.id.device_xinghao);
//        deviceGuige = (TextView) headerView.findViewById(R.id.device_guige);
//        deviceId = (TextView) headerView.findViewById(R.id.device_id);
//        listView.addHeaderView(headerView);
//
//        deviceLeibie.setText(CommonUtils.getDataIfNull(leibie));
//        deviceXinghao.setText(CommonUtils.getDataIfNull(xinghao));
//        deviceGuige.setText(CommonUtils.getDataIfNull(guige));
//        deviceId.setText(CommonUtils.getDataIfNull(id));

        View headerView = LayoutInflater.from(this).inflate(R.layout.adapter_record_device_detail, null);
        listView.addHeaderView(headerView);

        xListViewUtils = new XListViewUtils(listView);
        if (adapter == null) {
            adapter = new RecordDeviceUseDetailAdapter(mActivity, dataList);
//            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        xListViewUtils.setMyBaseAdapter(adapter);
        if (isGuiGe) {
            getListDataByGG();
        } else {
            getListDataById(id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search:
                getListDataById(searchInput.getText().toString());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DevicelogEntity item = (DevicelogEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, RecordDeviceUseDetailListDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, item);
            startActivity(intent);
        }
    }

    private void getListDataById(String id) {
//        if (StringUtils.isEmpty(id)) {
//            alert(R.string.please_input_device_id);
//            return;
//        }

        xListViewUtils.setSqlUrl(SqlUrl.GET_LOG_LIST_BY_BH);
        xListViewUtils.clearParams();
        xListViewUtils.addParams(" AND devicelog.JLZLMC = ?", JLMC);
        xListViewUtils.addParams(" AND devicelog.SBBH like ?", "%" + id + "%");
        xListViewUtils.setClazz(DevicelogEntity.class);
//        listView.showHeaderView();
        xListViewUtils.onRefresh();
    }

    /**
     * 查询列表的记录信息
     */
    private void getListDataByGG() {
        if (StringUtils.isEmpty(guige)) {
            alert(R.string.params_empty);
            return;
        }

        xListViewUtils.setSqlUrl(SqlUrl.GET_LOG_LIST_BY_GG);
        xListViewUtils.clearParams();
        xListViewUtils.addParams(" AND devicelog.JLZLMC = ?", JLMC);
        xListViewUtils.addParams(" AND dv.GG = ?", currentGuige.getCOOD());
        xListViewUtils.setClazz(DevicelogEntity.class);
//        listView.showHeaderView();
        xListViewUtils.onRefresh();
    }
}
