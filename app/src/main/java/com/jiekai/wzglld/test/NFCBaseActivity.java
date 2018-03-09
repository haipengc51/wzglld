package com.jiekai.wzglld.test;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.fragment.base.DeviceCardReaderInterface;
import com.jiekai.wzglld.utils.DeviceIdUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.nfcutils.NfcUtils;
import com.jiekai.wzglld.utils.zxing.utils.BeepManager;

/**
 * Created by laowu on 2017/12/1.
 */

public abstract class NFCBaseActivity extends MyBaseActivity implements DeviceCardReaderInterface {
    public boolean nfcEnable = false;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    protected AlertDialog deviceReadcardDialog;
    protected EditText deviceReadcardEdit;
    private String deviceIdCache;
    private BeepManager beepManager;

    /**
     * 获取到nfc卡的信息
     * @param nfcString
     */
    public abstract void getNfcData(String nfcString);
    //TODO 启动模式一应设置成singleTop，否则每次都走onCreate()

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beepManager = new BeepManager(this);
        initDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (nfcEnable) {
            getNfcData(NfcUtils.getNFCNum(intent));
        }
    }

    @Override
    public void initDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_readcard_artdialog, null);
        deviceReadcardEdit = (EditText) dialogView.findViewById(R.id.device_read_card_edit);
        deviceReadcardDialog = new AlertDialog.Builder(mActivity)
                .setView(dialogView)
                .create();

        setDeviceIdEdit(deviceReadcardEdit);
    }

    public void setDeviceIdEdit(EditText editText) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getEditTextString();
            }
        };
        deviceReadcardEdit.addTextChangedListener(new TextWatcher() {
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
        if (nfcEnable) {
            //有必要去判断读取数据的位数吗？后续如果标签的位数不是16位了呢
//            if (deviceId.getText().toString().length() >= 16)
            if (!StringUtils.isEmpty(deviceReadcardEdit.getText().toString()))
            {
                deviceIdCache = StringUtils.replaceBlank(deviceReadcardEdit.getText().toString());
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
    protected void onDestroy() {
        super.onDestroy();
        beepManager.close();
    }
}
