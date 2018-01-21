package com.jiekai.wzglld.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;

/**
 * Created by laowu on 2018/1/21.
 */

public class TestFragment extends MyNFCBaseFragment {
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bind_device, container, false);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {

    }

    @Override
    protected void getNfcData(String nfcString) {

    }
}
