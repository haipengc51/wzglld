package com.jiekai.wzglld.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordDeviceApplyAdapter;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DeviceapplyEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.uiUtils.XListViewUtils;
import com.jiekai.wzglld.utils.dbutils.DbDeal;
import com.jiekai.wzglld.weight.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LaoWu on 2018/2/7.
 */

public class RecordDeviceApplayActivity extends MyBaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    XListView listView;

    private XListViewUtils xListViewUtils;

    private RecordDeviceApplyAdapter adapter;
    private List<DevicestoreEntity> dataList = new ArrayList();

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_apply);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_apply));
        back.setVisibility(View.VISIBLE);
        menu.setVisibility(View.INVISIBLE);

        View headerView = LayoutInflater.from(this).inflate(R.layout.adapter_record_device_apply_detail, null);
        listView.addHeaderView(headerView);

        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        xListViewUtils = new XListViewUtils(listView);
        if (adapter == null) {
            adapter = new RecordDeviceApplyAdapter(mActivity, dataList);
            listView.setOnItemClickListener(this);
        }
        xListViewUtils.setMyBaseAdapter(adapter);
        filtrate(false);
    }

    @Override
    public void cancleDbDeal() {
        DbDeal dbDeal = xListViewUtils.getDbDeal();
        if (dbDeal != null) {
            dbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceapplyEntity item = (DeviceapplyEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, DeviceApplayDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, item);
            startActivity(intent);
        }
    }

    /**
     * 执行分页的筛选操作
     * @param isClickIn 是否点击的，如果是点击就判断点击是否选择了条件，如果没有提示用户
     */
    private void filtrate(boolean isClickIn) {

        xListViewUtils.setSqlUrl(SqlUrl.GetDeviceApplyINPage);
        xListViewUtils.clearParams();
        xListViewUtils.setClazz(DeviceapplyEntity.class);
//        listView.showHeaderView();
        xListViewUtils.onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
