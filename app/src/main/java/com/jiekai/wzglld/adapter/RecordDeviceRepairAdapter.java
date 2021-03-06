package com.jiekai.wzglld.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.adapter.base.MyBaseAdapter;
import com.jiekai.wzglld.config.Config;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.TimeUtils;

import java.util.List;

/**
 * Created by laowu on 2018/1/14.
 * 设备入库记录查询的列表
 */

public class RecordDeviceRepairAdapter extends MyBaseAdapter {

    public RecordDeviceRepairAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_repair_device_detail, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DevicestoreEntity item = (DevicestoreEntity) dataList.get(position);
        myViewHolder.id.setText(CommonUtils.getDataIfNull(String.valueOf(item.getSBBH())));
        myViewHolder.operatorPeople.setText(CommonUtils.getDataIfNull(item.getCzrname()));
        myViewHolder.operatorTime.setText(TimeUtils.dateToStringYYYYmmddHHMMSS(item.getCZSJ()));
        if (Config.repair_weixiu.equals(item.getLB())) {
            myViewHolder.repairType.setText("维修");
        } else if (Config.repair_daxiu.equals(item.getLB())) {
            myViewHolder.repairType.setText("大修");
        } else if (Config.repair_fanchang.equals(item.getLB())) {
            myViewHolder.repairType.setText("返厂");
        }
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
        private TextView id;    //设备自编码
        private TextView repairType;    //维修类型
        private TextView operatorPeople;   //操作人
        private TextView operatorTime;  //操作时间
        private TextView checkResult;  //审核结果

        public MyViewHolder(View view) {
            id = (TextView) view.findViewById(R.id.device_id);
            repairType = (TextView) view.findViewById(R.id.repair_type);
            operatorPeople = (TextView) view.findViewById(R.id.operator_people);
            operatorTime = (TextView) view.findViewById(R.id.operator_time);
            checkResult = (TextView) view.findViewById(R.id.check_result);
        }
    }
}
