package com.jiekai.wzglld.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.ui.uiUtils.EditTextPasswordInput;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.dbutils.DbDeal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2018/1/23.
 */

public class ChangePasswordActivity extends MyBaseActivity implements View.OnClickListener, EditTextPasswordInput.EditTextPasswordInputTextChange, View.OnFocusChangeListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.acount)
    TextView acount;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.old_scret)
    TextView oldScret;
    @BindView(R.id.et_original_password)
    EditText etOriginalPassword;
    @BindView(R.id.old_password_input_cancle)
    ImageView oldPasswordInputCancle;
    @BindView(R.id.old_password_visible)
    ImageView oldPasswordVisible;
    @BindView(R.id.new_scret)
    TextView newScret;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.new_password_input_cancle)
    ImageView newPasswordInputCancle;
    @BindView(R.id.new_password_visible)
    ImageView newPasswordVisible;
    @BindView(R.id.new_scret_agin)
    TextView newScretAgin;
    @BindView(R.id.et_new_password_agin)
    EditText etNewPasswordAgin;
    @BindView(R.id.new_password_input_cancle_agin)
    ImageView newPasswordInputCancleAgin;
    @BindView(R.id.new_password_visible_agin)
    ImageView newPasswordVisibleAgin;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;

    private boolean passwordVisibleFlag = false;
    private boolean passwordAgainVisileFlag = false;
    private boolean passwordAgainVisileAginFlag = false;
    private DbDeal dbDeal = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.change_password));

        oldPasswordVisible.setOnClickListener(this);
        oldPasswordInputCancle.setOnClickListener(this);
        etOriginalPassword.setOnFocusChangeListener(this);
        etOriginalPassword.addTextChangedListener(new EditTextPasswordInput(mActivity, etOriginalPassword, oldPasswordInputCancle,oldPasswordVisible, this));

        newPasswordVisible.setOnClickListener(this);
        newPasswordInputCancle.setOnClickListener(this);
        etNewPassword.setOnFocusChangeListener(this);
        etNewPassword.addTextChangedListener(new EditTextPasswordInput(mActivity,  etNewPassword,newPasswordInputCancle,newPasswordVisible, this));

        newPasswordVisibleAgin.setOnClickListener(this);
        newPasswordInputCancleAgin.setOnClickListener(this);
        etNewPasswordAgin.setOnFocusChangeListener(this);
        etNewPasswordAgin.addTextChangedListener(new EditTextPasswordInput(mActivity,  etNewPasswordAgin, newPasswordInputCancleAgin, newPasswordVisibleAgin, this));

        btnConfirm.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        tvAccount.setText(userData.getUSERID());
    }

    @Override
    public void cancleDbDeal() {
        if (dbDeal != null) {
            dbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                changePassword();
                break;
            case R.id.old_password_input_cancle:
                etOriginalPassword.setText("");
                break;
            case R.id.new_password_input_cancle:
                etNewPassword.setText("");
                break;
            case R.id.new_password_input_cancle_agin:
                etNewPasswordAgin.setText("");
                break;
            case R.id.old_password_visible:
                if (passwordVisibleFlag) {
                    etOriginalPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    oldPasswordVisible.setImageResource(R.drawable.ic_password_gone);
                } else {
                    etOriginalPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    oldPasswordVisible.setImageResource(R.drawable.ic_password_visible);
                }
                passwordVisibleFlag = !passwordVisibleFlag;
                etOriginalPassword.postInvalidate();
                etOriginalPassword.setSelection(etOriginalPassword.getText().length());
                break;
            case R.id.new_password_visible:
                if (passwordAgainVisileFlag) {
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPasswordVisible.setImageResource(R.drawable.ic_password_gone);
                } else {
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPasswordVisible.setImageResource(R.drawable.ic_password_visible);
                }
                passwordAgainVisileFlag = !passwordAgainVisileFlag;
                etNewPassword.postInvalidate();
                etNewPassword.setSelection(etNewPassword.getText().length());
                break;
            case R.id.new_password_visible_agin:
                if (passwordAgainVisileAginFlag) {
                    etNewPasswordAgin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPasswordVisibleAgin.setImageResource(R.drawable.ic_password_gone);
                } else {
                    etNewPasswordAgin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPasswordVisibleAgin.setImageResource(R.drawable.ic_password_visible);
                }
                passwordAgainVisileAginFlag = !passwordAgainVisileAginFlag;
                etNewPasswordAgin.postInvalidate();
                etNewPasswordAgin.setSelection(etNewPassword.getText().length());
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.old_password_visible:
                if (hasFocus && etOriginalPassword.getText().length() != 0) {
                    oldPasswordVisible.setVisibility(View.VISIBLE);
                    oldPasswordInputCancle.setVisibility(View.VISIBLE);
                } else {
                    oldPasswordVisible.setVisibility(View.GONE);
                    oldPasswordInputCancle.setVisibility(View.GONE);
                }
                break;
            case R.id.new_password_visible:
                if (hasFocus && etNewPassword.getText().length() != 0) {
                    newPasswordVisible.setVisibility(View.VISIBLE);
                    newPasswordInputCancle.setVisibility(View.VISIBLE);
                } else {
                    newPasswordVisible.setVisibility(View.GONE);
                    newPasswordInputCancle.setVisibility(View.GONE);
                }
                break;
            case R.id.new_password_visible_agin:
                if (hasFocus && etNewPasswordAgin.getText().length() != 0) {
                    newPasswordVisibleAgin.setVisibility(View.VISIBLE);
                    newPasswordInputCancleAgin.setVisibility(View.VISIBLE);
                } else {
                    newPasswordVisibleAgin.setVisibility(View.GONE);
                    newPasswordInputCancleAgin.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void editTextPasswordInputTextChange() {

    }

    private void changePassword() {
        String oldPassword = etOriginalPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String newPasswordAgin = etNewPasswordAgin.getText().toString();
        if (StringUtils.isEmpty(oldPassword)) {
            alert(R.string.please_input_old_password);
            return;
        }
        if (StringUtils.isEmpty(newPassword)) {
            alert(R.string.please_input_new_password);
            return;
        }
        if (StringUtils.isEmpty(newPasswordAgin)) {
            alert(R.string.please_input_agin_password);
            return;
        }
        if (!oldPassword.equals(userData.getPASSWORD())) {
            alert(R.string.old_password_err);
            return;
        }
        if (!newPassword.equals(newPasswordAgin)) {
            alert(R.string.password_different);
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.UPDATA);
                dbDeal.sql(SqlUrl.CHANGE_PASSWORD)
                .params(new String[]{newPassword, userData.getUSERID()})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_db));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.password_change_success);
                        setResult(RESULT_OK);
                        finish();
                        dismissProgressDialog();
                    }
                });
    }
}
