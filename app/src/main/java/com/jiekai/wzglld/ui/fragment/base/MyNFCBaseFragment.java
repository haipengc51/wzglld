package com.jiekai.wzglld.ui.fragment.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.base.MyBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/2.
 */

public abstract class MyNFCBaseFragment extends Fragment {
    public MyBaseActivity mActivity;
    private ProgressDialog progressDialog = null;

    public boolean enableNfc = false;

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void initData();
    public abstract void initOperation();
    /**
     * 得到nfc的信息
     * @param nfcString
     */
    protected abstract void getNfcData(String nfcString);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MyBaseActivity) getActivity();
        View view = initView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        initData();
        initOperation();
        return view;
    }

    public void alert(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void alert(String resMsg) {
        Toast.makeText(getActivity(), resMsg, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(String msg) {
        dismissProgressDialog();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setTitle(getResources().getString(R.string.please_wait));
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setNfcData(String nfcString) {
        if (enableNfc) {
            getNfcData(nfcString);
        }
    }
}
