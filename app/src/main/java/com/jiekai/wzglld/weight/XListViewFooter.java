/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.jiekai.wzglld.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzglld.R;

public class XListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private Context mContext;

    private View mContentView;
    public View mProgressBar;
    public TextView mHintView;
    private TextView isLast;//“这是最后一条”提示

    public XListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
        isLast = (TextView) moreView.findViewById(R.id.isLast);
    }

    public void setState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.xlistview_header_hint_loading);
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.loading);
        } else {
            mHintView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mHintView.setText(R.string.xlistview_footer_hint_normalee);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0) {
            return;
        }
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
//        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
//        lp.height = 0;
//        mContentView.setLayoutParams(lp);
        mContentView.setVisibility(GONE);
//        isLast.setVisibility(VISIBLE);
    }

    /**
     * show footer
     */
    public void show() {
//        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
//        lp.height = LayoutParams.WRAP_CONTENT;
//        mContentView.setLayoutParams(lp);
        mContentView.setVisibility(VISIBLE);
//        isLast.setVisibility(GONE);
    }
}
