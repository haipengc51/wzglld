package com.jiekai.wzglld.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.base.MyBaseAdapter;
import com.jiekai.wzglld.entity.DevicelogEntity;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.TimeUtils;

import java.util.List;

/**
 * Created by laowu on 2018/1/14.
 */

public class RecordDeviceUseDetailAdapter extends MyBaseAdapter {

    public RecordDeviceUseDetailAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_record_device_detail, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DevicelogEntity item = (DevicelogEntity) dataList.get(position);
        myViewHolder.id.setText(CommonUtils.getDataIfNull(String.valueOf(item.getID())));
        myViewHolder.operatorPeople.setText(CommonUtils.getDataIfNull(item.getCzrname()));
        myViewHolder.operatorTime.setText(TimeUtils.dateToStringYYYYmmddHHMMSS(item.getJLSJ()));
        if ("1".equals(item.getSHYJ())) {
            myViewHolder.checkResult.setText("通过");
        } else if ("0".equals(item.getSHYJ())) {
            myViewHolder.checkResult.setText("未通过");
        } else {
            myViewHolder.checkResult.setText("待审核");
        }
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private TextView id;    //序号
        private TextView operatorPeople;   //操作人
        private TextView operatorTime;  //操作时间
        private TextView checkResult;  //审核结果

        public MyViewHolder(View view) {
            id = (TextView) view.findViewById(R.id.xuhao);
            operatorPeople = (TextView) view.findViewById(R.id.operator_people);
            operatorTime = (TextView) view.findViewById(R.id.operator_time);
            checkResult = (TextView) view.findViewById(R.id.check_result);
        }
    }
}
