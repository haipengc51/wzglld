package com.jiekai.wzglld.ui.update;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglld.R;

/**
 * Created by LaoWu on 2018/3/13.
 * 当有更新的时候弹出框
 */

public class UpdateHaveUpdateDialog extends BaseDialogFragment {
    private String updataInfo;
    private HaveUpdateInterface updateInterface;

    public UpdateHaveUpdateDialog() {
    }

    public UpdateHaveUpdateDialog(boolean mIsOutCanback, boolean mIsKeyCanback, String updataInfo, HaveUpdateInterface updateInterface) {
        super(mIsOutCanback, mIsKeyCanback);
        this.updataInfo = updataInfo;
        this.updateInterface = updateInterface;
    }

    @Override
    protected View bindView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.update_dialog_have_new_apk, container);
        view.findViewById(R.id.enter).setOnClickListener(lisen);
        view.findViewById(R.id.cancle).setOnClickListener(lisen);
        ((TextView)view.findViewById(R.id.content)).setText(updataInfo);
        return view;
    }

    private View.OnClickListener lisen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.enter:
                    updateInterface.enterDownLoad();
                    hideDialog();
                    break;
                case R.id.cancle:
                    updateInterface.cancleDownLoad();
                    hideDialog();
                    break;
            }
        }
    };
}
