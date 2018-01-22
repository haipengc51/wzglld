package com.jiekai.wzglld;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.fragment.QueryDeviceInfoFragment;
import com.jiekai.wzglld.ui.fragment.TabBarFragment;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;

public class MainActivity extends NFCBaseActivity {
    private TabBarFragment tabFragment;
    private long mBackPressedTime;
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private static final int HANDLER_CHENGE_UPDATE = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_CHENGE_UPDATE:     //延时检测是否有更新
//                    new UpdateUtil(BzMainActivity.this, false).isUpdate();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(HANDLER_CHENGE_UPDATE, 3000 * 1);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        tabFragment = (TabBarFragment) getSupportFragmentManager().findFragmentById(R.id.tab_fragment);
        tabFragment.onTabSelected(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {

    }

    @Override
    public void getNfcData(String nfcString) {
        MyNFCBaseFragment baseFragment = tabFragment.getCurrentFragment();
        baseFragment.setNfcData(nfcString);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tabFragment.baseFragment == null) {
                return false;
            }
            //TODO 是否跳转到第一个页面，如果是的话才退出
            if (tabFragment.baseFragment.getClass().equals(QueryDeviceInfoFragment.class)) {
                long curTime = SystemClock.uptimeMillis();
                if ((curTime - mBackPressedTime) < (3 * 1000)) {
                    isAnimation = false;
                    finish();
                } else {
                    mBackPressedTime = curTime;
                    Toast.makeText(this, R.string.click_again_finish, Toast.LENGTH_SHORT).show();
                }
            } else {
                tabFragment.onTabSelected(0);
            }
        }
        return false;
    }
}
