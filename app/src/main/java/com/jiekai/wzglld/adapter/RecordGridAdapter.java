package com.jiekai.wzglld.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.base.MyBaseAdapter;
import com.jiekai.wzglld.entity.RecordGridEntity;

import java.util.List;

/**
 * Created by laowu on 2018/1/22.
 */

public class RecordGridAdapter extends MyBaseAdapter {

    public RecordGridAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_record_device_grid, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        RecordGridEntity item = (RecordGridEntity) dataList.get(position);
        myViewHolder.avatar.setImageResource(item.getIcon());
        myViewHolder.name.setText(item.getName());
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private ImageView avatar;
        private TextView name;

        public MyViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
