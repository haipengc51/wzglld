package com.jiekai.wzglld.ui.fragment.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/2.
 */

public abstract class MyNFCBaseFragment extends Fragment {
    public MyBaseActivity mActivity;
    private ProgressDialog progressDialog = null;
    private EditText deviceId;
    private String deviceIdCache;

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

    public void setDeviceId(EditText editText) {
        this.deviceId = editText;
        this.deviceId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (enableNfc) {
                    deviceIdCache = StringUtils.replaceBlank(deviceId.getText().toString());
                    if (deviceIdCache.length() == 16) {
                        getNfcData(deviceIdCache);
                    }
                    Toast.makeText(mActivity, deviceIdCache, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
