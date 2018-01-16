package com.jiekai.wzglld.ui.uiUtils;

import android.view.View;
import android.widget.AdapterView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.base.MyBaseAdapter;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.dbutils.DbDeal;
import com.jiekai.wzglld.weight.XListView;
import com.jiekai.wzglld.weight.XListViewFooter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laowu on 2018/1/15.
 * 上拉加载下拉刷新控件的工具类
 */

public class XListViewUtils implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView xListView;
    public boolean isOnRefresh = true;
    private int index = 1;
    private int size = 20;
    private MyBaseAdapter myBaseAdapter;
    private String sqlUrl;
    private Map<String, Object> params = new HashMap<>();
    private Class clazz;
    private boolean isLoading = false;

    public XListViewUtils(XListView xListView) {
        this.xListView = xListView;
        init();
    }

    public void setSqlUrl(String sqlUrl) {
        this.sqlUrl = sqlUrl;
    }

    public void clearParams() {
        params.clear();
    }

    public void addParams(String name, Object values) {
        params.put(name, values);
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setMyBaseAdapter(MyBaseAdapter adapter) {
        this.myBaseAdapter = adapter;
        xListView.setAdapter(adapter);
    }

    private void init() {
        if (isOnRefresh) {
            xListView.setPullRefreshEnable(true);
        } else {
            xListView.setPullRefreshEnable(false);
        }

        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(true);

        xListView.setOnItemClickListener(this);
        params.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
        xListView.setRefreshTime();
        index = 1;
        myBaseAdapter.dataList.clear();
        requestData();
    }

    @Override
    public void onLoadMore() {
        requestData();
    }

    @Override
    public void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime();
    }

    private void requestData() {
        xListView.mFooterView.setState(2);
        if (isLoading) {
            return;
        }
        isLoading = true;

        DbDeal dbDeal = DBManager.dbDeal(DBManager.SELECT);

        StringBuilder builder = new StringBuilder();
        builder.append(sqlUrl);
        Object[] objects = new Object[params.size() + 2];
        int paramsId = 0;
        for (String name: params.keySet()) {
            builder.append(name);
            objects[paramsId] = params.get(name);
            paramsId++;
        }
        builder.append(" limit ?, ?");
        objects[paramsId] = (index-1)*size;
        paramsId++;
        objects[paramsId] = size;

        dbDeal.sql(builder.toString());
        dbDeal.params(objects);
        dbDeal.clazz(clazz);
        dbDeal.execut(new DbCallBack() {
            @Override
            public void onDbStart() {

            }

            @Override
            public void onError(String err) {
                isLoading = false;
                onLoad();
            }

            @Override
            public void onResponse(List result) {
                try {
                    isLoading = false;
                    onLoad();
                    if (result != null && result.size() > 0) {
                        myBaseAdapter.dataList.addAll(result);
                        myBaseAdapter.notifyDataSetChanged();
                        index++;
                        if (result.size() < size) {
                            xListView.mFooterView.mHintView.setText(R.string.no_data_xlist);
                            xListView.mFooterView.mProgressBar.setVisibility(View.GONE);
                            xListView.mFooterView.setClickable(false);
                        } else {
                            xListView.mFooterView.setState(XListViewFooter.STATE_NORMAL);
                            xListView.mFooterView.setClickable(true);
                        }
                    } else {
                        xListView.mFooterView.mProgressBar.setVisibility(View.GONE);
                        xListView.mFooterView.mHintView.setText(R.string.no_data_xlist);
                        xListView.mFooterView.setClickable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onDestroy() {
        index = 1;
    }
}
