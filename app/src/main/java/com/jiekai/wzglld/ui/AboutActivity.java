package com.jiekai.wzglld.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/23.
 */

public class AboutActivity extends MyBaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.lltop)
    LinearLayout lltop;

    @Override
    public void initView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.about_wzgl));
        tvVersionName.setText("V" + CommonUtils.getVersionName(mActivity));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initOperation() {

    }
}
