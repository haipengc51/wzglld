package com.jiekai.wzglld.ui;

import android.content.Intent;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.KeeperAdapter;
import com.jiekai.wzglld.entity.KeeperEntity;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceInActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceInspectionActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceMoveActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceOutActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceRepairActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceScrapActivity;
import com.jiekai.wzglld.ui.record.RecordDeviceUseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/7.
 * 库管的主界面
 */

public class KeeperMainActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {
    private final static int LOGOUT = 0;
    @BindView(R.id.grid_view)
    GridView gridView;

    private long mBackPressedTime = 0;

    private List<KeeperEntity> dataList = new ArrayList<KeeperEntity>() {};
    private KeeperAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_keeper_main);
    }

    @Override
    public void initData() {
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity_new.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_output), DeviceOutputActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_out_history), DeviceOutPutHistoryActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_input), DeviceInputActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_repair), DeviceRepairActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.pan_ku), PanKuActivity.class));

//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_detail), DeviceDetailSimpleActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_use_record), DeviceUseRecordActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.device_scrap), DeviceScrapActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.record_check_result), RecordHistoryActivity.class));
//        dataList.add(new KeeperEntity(getResources().getString(R.string.logout), LogOutActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.query_device_info), QueryDeviceInfoActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_use_record_find), RecordDeviceUseActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_in), RecordDeviceInActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_out), RecordDeviceOutActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_scrap), RecordDeviceScrapActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_repair), RecordDeviceRepairActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_move), RecordDeviceMoveActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.record_inspection), RecordDeviceInspectionActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_scrap), DeviceScrapActivity.class));
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            adapter = new KeeperAdapter(KeeperMainActivity.this, dataList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);
        }
//        AppContext.getUnCheckedData(this, userData.getUSERID());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KeeperEntity keeperEntity = (KeeperEntity) parent.getItemAtPosition(position);
        if (keeperEntity != null) {
            if (keeperEntity.getName().equals(getResources().getString(R.string.logout))) {
                isAnimation = false;
                startActivityForResult(new Intent(mActivity, keeperEntity.getActivity()), LOGOUT);
            } else {
                isAnimation = true;
                startActivity(new Intent(KeeperMainActivity.this, keeperEntity.getActivity()));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                isAnimation = false;
                finish();
            } else {
                mBackPressedTime = curTime;
                alert(R.string.click_again_finish);
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGOUT && resultCode == RESULT_OK) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            finish();
        }
    }
}
