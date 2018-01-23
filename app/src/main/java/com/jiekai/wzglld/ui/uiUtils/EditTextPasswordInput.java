package com.jiekai.wzglld.ui.uiUtils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by laowu on 2016/8/1.
 * 用于EditText输入手机号的各种检测
 * 判断了输入不能含有特殊字符
 * 密码不能大于多少位
 * 输入密码后后面取消输入和密码可见的显示和隐藏
 */
public class EditTextPasswordInput implements TextWatcher {
    private static final String haveExChar = "输入不能含有特殊字符";
    private static final String moreNum = "手机号不得大于11位数";
    private static final int MAXPHONENUM = 11;

    private Toast toast = null;
    private Context context;
    private EditText editText;
    private ImageView editCancle;
    private ImageView editVisible;
    private String editTextOld;
    private EditTextPasswordInputTextChange textPasswordInputTextChange;

    public interface EditTextPasswordInputTextChange {
        public void editTextPasswordInputTextChange();
    }

    /**
     *
     * @param context
     * @param editText
     * @param editCancle
     * @param editVisible
     */
    public EditTextPasswordInput(Context context, EditText editText, ImageView editCancle, ImageView editVisible, EditTextPasswordInputTextChange textPasswordInputTextChange) {
        this.context = context;
        this.editText = editText;
        this.editCancle = editCancle;
        this.editVisible = editVisible;
        this.textPasswordInputTextChange = textPasswordInputTextChange;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String password = editText.getText().toString();
//        if (Check.isExChar(password)) {
//            if (password.length() > 1) {
//                editText.setText(editTextOld);
//                password = editTextOld;
//                editText.setSelection(editText.getText().length());
//            }
//            toastText("输入不能含有特殊字符");
//        }
//        if (password.length() >= Constants.MINPASSWORD && password.length() <= Constants.MAXPASSWORD) {
//
//        } else {
//            if (password.length() > Constants.MAXPASSWORD) {
//                editText.setText(editTextOld);
//                password = editTextOld;
//                editText.setSelection(editText.getText().length());
//                toastText(String.format(context.getString(R.string.password_long), Constants.MAXPASSWORD));
//            }
//        }
        if (password.length() != 0) {
            editVisible.setVisibility(View.VISIBLE);
            editCancle.setVisibility(View.VISIBLE);
        } else {
            editVisible.setVisibility(View.GONE);
            editCancle.setVisibility(View.GONE);
        }
        editTextOld = password;

        textPasswordInputTextChange.editTextPasswordInputTextChange();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void toastText(String msg) {
        if(toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 30);
        toast.show();
    }
}
