package com.jiekai.wzglld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.test.NFCBaseActivity;
import com.jiekai.wzglld.ui.LoginActivity;
import com.jiekai.wzglld.ui.fragment.QueryDeviceInfoFragment;
import com.jiekai.wzglld.ui.fragment.TabBarFragment;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;
import com.jiekai.wzglld.ui.update.UpdateManager;

public class MainActivity extends NFCBaseActivity {
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private static final int HANDLER_CHENGE_UPDATE = 0;
    private static final String TabBarPosition = "TAB_POSITION";

    private TabBarFragment tabFragment;
    private long mBackPressedTime;
    private FragmentTransaction transaction;
    private boolean isNormal = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_CHENGE_UPDATE:     //延时检测是否有更新
                    UpdateManager updateManager = new UpdateManager(MainActivity.this);
                    updateManager.getRemoteVersion();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(HANDLER_CHENGE_UPDATE, 3000 * 1);
        isNormal = getIntent().getBooleanExtra(IntentFlag.IS_NORMAL, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TabBarPosition, tabFragment.getCurrentFragmentPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        tabFragment = (TabBarFragment) getSupportFragmentManager().findFragmentById(R.id.tab_fragment);
        if (tabFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            tabFragment = new TabBarFragment();
            transaction.add(R.id.tab_fragment, tabFragment);
            transaction.commit();
            alert("从新add了tabbar");
        }
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
            //TODO 是否跳转到第一个页面，如果是的话才退出
            if (tabFragment.baseFragment != null && QueryDeviceInfoFragment.class.equals(tabFragment.baseFragment.getClass())) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGOUT && resultCode == RESULT_OK) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        transaction.remove(tabFragment);
    }
}
