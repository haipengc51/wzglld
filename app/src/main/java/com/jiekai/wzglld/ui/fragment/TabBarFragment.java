package com.jiekai.wzglld.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.fragment.base.MyNFCBaseFragment;

import java.util.List;

/**
 * Created by laowu on 2018/1/21.
 */

public class TabBarFragment extends MyNFCBaseFragment {
    private static final String POSITION = "position";

    ImageView tab_one;
    ImageView tab_two;
    ImageView tab_three;
    ImageView tab_four;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;
    ImageView tab_fourbg;

    TextView tab_one_text, tab_two_text, tab_three_text, tab_four_text;

    private FrameLayout tab_one_bg;
    private FrameLayout tab_two_bg;
    private FrameLayout tab_three_bg;
    private FrameLayout tab_four_bg;

    public MyNFCBaseFragment baseFragment;
//    private MyNFCBaseFragment currentFragment;
    private int currentFragmentPosition = -1;
    private MyNFCBaseFragment tagFragment;
    private MyBaseActivity myActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = (MyBaseActivity) getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSITION, currentFragmentPosition);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragement_tabbar, container, false);
        initView(mainView);

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(POSITION);
            if (position != -1) {
                this.selectView(position);
            }
        }

        return mainView;
    }

    private void initView(View mainView) {
        this.tab_one = (ImageView) mainView.findViewById(R.id.toolbar_tabone);
        this.tab_onebg = (ImageView) mainView.findViewById(R.id.toolbar_tabonebg);
        this.tab_one_bg = (FrameLayout) mainView.findViewById(R.id.tab_one_layout);
        this.tab_one_text = (TextView) mainView.findViewById(R.id.tab_one_text);
        this.tab_one_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(0);
            }
        });

        this.tab_two = (ImageView) mainView.findViewById(R.id.toolbar_tabtwo);
        this.tab_twobg = (ImageView) mainView.findViewById(R.id.toolbar_tabtwobg);
        this.tab_two_bg = (FrameLayout) mainView.findViewById(R.id.tab_two_layout);
        this.tab_two_text = (TextView) mainView.findViewById(R.id.tab_two_text);
        this.tab_two_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(1);
            }
        });

        this.tab_three = (ImageView) mainView.findViewById(R.id.toolbar_tabthree);
        this.tab_threebg = (ImageView) mainView.findViewById(R.id.toolbar_tabthreebg);
        this.tab_three_bg = (FrameLayout) mainView.findViewById(R.id.tab_three_layout);
        this.tab_three_text = (TextView) mainView.findViewById(R.id.tab_three_text);
        this.tab_three_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(2);
            }
        });

        this.tab_four = (ImageView) mainView.findViewById(R.id.toolbar_tabfour);
        this.tab_fourbg = (ImageView) mainView.findViewById(R.id.toolbar_tabfourbg);
        this.tab_four_bg = (FrameLayout) mainView.findViewById(R.id.tab_four_layout);
        this.tab_four_text = (TextView) mainView.findViewById(R.id.tab_four_text);
        this.tab_four_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(3);
            }
        });
        if (getFragmentManager().findFragmentById(R.id.content) == null) {
            onTabSelected(0);
        }
    }

    public void onTabSelected(int position) {
        this.currentFragmentPosition = position;
        baseFragment = (MyNFCBaseFragment) getFragmentManager().findFragmentByTag(String.valueOf(position));

        selectView(position);
        selectFragment(position);

        FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
        if (position < 4) {
            tagFragment = (MyNFCBaseFragment) getVisibleFragment(R.id.content);
            if (baseFragment.isAdded()) {
                if (tagFragment != null) {
                    transaction.hide(tagFragment).show(baseFragment).commitAllowingStateLoss();
                } else {
                    transaction.show(baseFragment).commitAllowingStateLoss();
                }
            } else {
                if (tagFragment != null) {
                    transaction.hide(tagFragment).add(R.id.content, baseFragment, String.valueOf(position)).commitAllowingStateLoss();
                } else {
                    transaction.add(R.id.content, baseFragment, String.valueOf(position)).commitAllowingStateLoss();
                }
            }
            tagFragment = baseFragment;
        }
    }

    public MyNFCBaseFragment getCurrentFragment() {
        return tagFragment;
    }

    public int getCurrentFragmentPosition() {
        return  this.currentFragmentPosition;
    }

    private void selectView(int position){
        this.currentFragmentPosition = position;
        switch (position) {
            case 0:
                this.tab_one.setImageResource(R.drawable.ic_find_press);
                this.tab_two.setImageResource(R.drawable.ic_record);
                this.tab_three.setImageResource(R.drawable.ic_scrap);
                this.tab_four.setImageResource(R.drawable.ic_me);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.tabar_color_press));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.tabar_color));
                break;
            case 1:
                this.tab_one.setImageResource(R.drawable.ic_find);
                this.tab_two.setImageResource(R.drawable.ic_record_press);
                this.tab_three.setImageResource(R.drawable.ic_scrap);
                this.tab_four.setImageResource(R.drawable.ic_me);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.tabar_color_press));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.tabar_color));
                break;
            case 2:
                this.tab_one.setImageResource(R.drawable.ic_find);
                this.tab_two.setImageResource(R.drawable.ic_record);
                this.tab_three.setImageResource(R.drawable.ic_scrap_press);
                this.tab_four.setImageResource(R.drawable.ic_me);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.tabar_color_press));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.tabar_color));
                break;
            case 3:
                this.tab_one.setImageResource(R.drawable.ic_find);
                this.tab_two.setImageResource(R.drawable.ic_record);
                this.tab_three.setImageResource(R.drawable.ic_scrap);
                this.tab_four.setImageResource(R.drawable.ic_me_press);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.tabar_color));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.tabar_color_press));
                break;
        }
    }

    private  void selectFragment(int position) {
        this.currentFragmentPosition = position;
        switch (position) {
            case 0:
                if (baseFragment == null) {
                    baseFragment = new QueryDeviceInfoFragment();
                }
                break;
            case 1:
                if (baseFragment == null) {
                    baseFragment = new RecordGridFragement();
                }
                break;
            case 2:
                if (baseFragment == null) {
                    baseFragment = new DeviceScrapFragment();
                }
                break;
            case 3:
                if (baseFragment == null) {
                    baseFragment = new MeFragment();
                }
                break;
        }
    }

    private Fragment getVisibleFragment(int id) {
        List<Fragment> allFragment = getFragmentManager().getFragments();
        Fragment visibleFragment;
        for (int i=0; i<allFragment.size(); i++) {
            visibleFragment = allFragment.get(i);
            if (visibleFragment.getId() == id && visibleFragment.isVisible()) {
                return visibleFragment;
            }
        }
        return null;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {

    }

    @Override
    public void cancleDbDeal() {

    }

    @Override
    protected void getNfcData(String nfcString) {

    }
}
