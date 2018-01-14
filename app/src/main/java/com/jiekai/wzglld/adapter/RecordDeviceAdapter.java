package com.jiekai.wzglld.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.base.MyBaseAdapter;
import com.jiekai.wzglld.entity.DevicelogsortEntity;

import java.util.List;

/**
 * Created by laowu on 2018/1/14.
 * 历史记录->设备使用历史记录->历史记录的种类
 */

public class RecordDeviceAdapter extends MyBaseAdapter {

    public RecordDeviceAdapter(Context context, List<DevicelogsortEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_record_device_use_list, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DevicelogsortEntity item = (DevicelogsortEntity) dataList.get(position);
        myViewHolder.textView.setText(item.getJLZLMC());
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private TextView textView;

        public MyViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.text);
        }
    }
}
