package com.jiekai.wzglld.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.Constants;
import com.jiekai.wzglld.ui.AboutActivity;
import com.jiekai.wzglld.ui.AccountInfoActivity;
import com.jiekai.wzglld.ui.ChangePasswordActivity;
import com.jiekai.wzglld.ui.LoginActivity;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;
import com.jiekai.wzglld.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by laowu on 2018/1/23.
 * 我的页面
 */

public class MeFragment extends MyNFCBaseFragment implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.account_info)
    TextView accountInfo;
    @BindView(R.id.change_password)
    TextView changePassword;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.accout_info_linear)
    LinearLayout accoutInfoLinear;

    private String userName;
    private String userPhone;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void initData() {
        back.setVisibility(View.INVISIBLE);
        title.setText(getResources().getString(R.string.me));
    }

    @Override
    public void initOperation() {
        accountInfo.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        about.setOnClickListener(this);
        logout.setOnClickListener(this);

        userName = mActivity.userData.getUSERNAME();
        userPhone = mActivity.userData.getPHONE();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPhone)) {
            accoutInfoLinear.setVisibility(View.GONE);
        } else {
            accoutInfoLinear.setVisibility(View.VISIBLE);
            name.setText(userName);
            phone.setText(userPhone);
        }
    }

    @Override
    protected void getNfcData(String nfcString) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_info:
                startActivity(new Intent(mActivity, AccountInfoActivity.class));
                break;
            case R.id.change_password:
                startActivityForResult(new Intent(mActivity, ChangePasswordActivity.class), Constants.REQUEST_LOGOUT);
                break;
            case R.id.about:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.logout:
                mActivity.clearLoginData();
                startActivity(new Intent(mActivity, LoginActivity.class));
                mActivity.finish();
                break;
        }
    }
}
