package com.jiekai.wzglld.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.RecordHistoryAdapter;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicelogEntity;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.dbutils.DbDeal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LaoWu on 2018/1/5.
 * 现场申请--申请记录
 */

public class RecordHistoryActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    ListView listView;

    private View headerView;
    private RecordHistoryAdapter adapter;
    private List<DevicelogEntity> dataList = new ArrayList<>();

    private DbDeal dbDeal = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_history);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_check_result));
        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_record_history, null);
            listView.addHeaderView(headerView);
            adapter = new RecordHistoryAdapter(mActivity, dataList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        setHeaderViewVisible(View.GONE);
        getData();
    }

    @Override
    public void cancleDbDeal() {
        if (dbDeal != null) {
            dbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DevicelogEntity entity = (DevicelogEntity) parent.getItemAtPosition(position);
        if (entity != null) {
            Intent intent = new Intent(mActivity, RecordHistoryDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, entity);
            startActivity(intent);
        }
    }

    private void getData() {
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GET_RECORD_CHECK_LIST)
                .params(new String[]{userData.getUSERID()})
                .clazz(DevicelogEntity.class)
                .execut(mContext, new DbCallBack() {
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
                            adapter.notifyDataSetChanged();
                            setHeaderViewVisible(View.VISIBLE);
                        } else {
                            alert(R.string.your_all_check_pass);
                            setHeaderViewVisible(View.GONE);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    private void setHeaderViewVisible(int visible) {
        if (headerView != null) {
            headerView.setVisibility(visible);
        }
    }
}
