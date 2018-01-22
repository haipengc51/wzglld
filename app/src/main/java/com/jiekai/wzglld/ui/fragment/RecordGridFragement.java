package com.jiekai.wzglld.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordGridAdapter;
import com.jiekai.wzglld.entity.KeeperEntity;
import com.jiekai.wzglld.entity.RecordGridEntity;
import com.jiekai.wzglld.ui.KeeperMainActivity;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by laowu on 2018/1/22.
 */

public class RecordGridFragement extends MyNFCBaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.grid_view)
    GridView gridView;

    private List<RecordGridEntity> dataList = new ArrayList<RecordGridEntity>() {};
    private RecordGridAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_grid, container, false);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record));
        back.setVisibility(View.INVISIBLE);

        dataList.add(new RecordGridEntity(getResources().getString(R.string.device_use_record_find),
                RecordDeviceUseActivity.class, R.drawable.ic_device_use));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_in),
                RecordDeviceInActivity.class, R.drawable.ic_device_in));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_out),
                RecordDeviceOutActivity.class, R.drawable.ic_device_out));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_scrap),
                RecordDeviceScrapActivity.class, R.drawable.ic_device_scrap));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_repair),
                RecordDeviceRepairActivity.class, R.drawable.ic_device_repair));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_move),
                RecordDeviceMoveActivity.class, R.drawable.ic_device_move));
        dataList.add(new RecordGridEntity(getResources().getString(R.string.record_inspection),
                RecordDeviceInspectionActivity.class, R.drawable.ic_device_inspection));
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            adapter = new RecordGridAdapter(mActivity, dataList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);
        }
    }

    @Override
    protected void getNfcData(String nfcString) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            RecordGridEntity item = (RecordGridEntity) parent.getItemAtPosition(position);
            if (item != null) {
                startActivity(new Intent(mActivity, item.getActivity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
