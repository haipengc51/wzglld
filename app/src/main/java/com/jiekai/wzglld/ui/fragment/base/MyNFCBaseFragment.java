package com.jiekai.wzglld.ui.fragment.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.jiekai.wzglld.utils.DeviceIdUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.zxing.utils.BeepManager;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/2.
 */

public abstract class MyNFCBaseFragment extends Fragment implements DeviceCardReaderInterface {
    public MyBaseActivity mActivity;
    private ProgressDialog progressDialog = null;
    private EditText deviceId;
    private String deviceIdCache;
    private BeepManager beepManager;

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
        beepManager = new BeepManager(getActivity());
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

    public void setDeviceIdEdit(EditText editText) {
        this.deviceId = editText;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getEditTextString();
            }
        };
        this.deviceId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 500);
            }
        });
    }

    public void getEditTextString() {
        if (enableNfc) {
            //有必要去判断读取数据的位数吗？后续如果标签的位数不是16位了呢
//            if (deviceId.getText().toString().length() >= 16)
            if (!StringUtils.isEmpty(deviceId.getText().toString()))
            {
                deviceIdCache = StringUtils.replaceBlank(deviceId.getText().toString());
//                if (deviceIdCache.length() == 16)
                {
                    deviceIdCache = DeviceIdUtils.reverseDeviceId(deviceIdCache);
                    getNfcData(deviceIdCache);
                    beepManager.playBeepSoundAndVibrate();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        beepManager.close();
    }
}
