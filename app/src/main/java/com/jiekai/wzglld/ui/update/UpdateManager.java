package com.jiekai.wzglld.ui.update;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.UpdateEntity;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.ftputils.FtpCallBack;
import com.jiekai.wzglld.utils.ftputils.FtpManager;

import java.io.File;
import java.util.List;

/**
 * Created by LaoWu on 2018/3/12.
 * 升级工具类
 */

public class UpdateManager {
    private String localPath = Environment.getExternalStorageDirectory().getPath()+"/liu/";
    private Activity activity;
    private UpdateEntity updateData;
    private DialogFragment dialogFragment;

    public UpdateManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * 比较服务器版本和本地版本
     */
    private void compareRemoteVersion(Context context) {
        int localVersion = getLocalVersion(context);
        if (updateData != null
                && updateData.getVERSION() != -1 && localVersion != -1
                && updateData.getVERSION() > localVersion) {
            //显示有更新的界面
            dialogFragment = new DialogFragment(){
                @Nullable
                @Override
                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
                    View view = inflater.inflate(R.layout.update_dialog_have_new_apk, container);
                    view.findViewById(R.id.enter).setOnClickListener(lisen);
                    view.findViewById(R.id.cancle).setOnClickListener(lisen);
                    return view;
                }

                private View.OnClickListener lisen = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.enter:
                                String path = updateData.getPATH();
                                int nameAndPath = path.lastIndexOf("/");
                                if (nameAndPath != -1) {
                                    String remotName = path.substring(nameAndPath+1, path.length());
                                    String remotPath = path.substring(0, nameAndPath);
                                    downloadApk(localPath, remotPath, remotName);
                                }
                                break;
                            case R.id.cancle:
                                dialogFragment.dismiss();
                                break;
                        }
                    }
                };
            };
            dialogFragment.show(activity.getFragmentManager(), "刘海鹏");
        }
    }

    /**
     * 获取远程版本号
     */
    public void getRemoteVersion() {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_UPDATE_VERSION)
                .clazz(UpdateEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {

                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            updateData = (UpdateEntity) result.get(0);
                            compareRemoteVersion(activity);
                        }
                    }
                });
    }

    /**
     * 获取本地版本号
     */
    private int getLocalVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 下载远程服务器版本
     */
    private void downloadApk(String localFilePath, String remoteFilePath, String remoteFileName) {
        if (StringUtils.isEmpty(localFilePath)) {
            return;
        }
        FtpManager.getInstance().downloadFile(localFilePath, remoteFilePath, remoteFileName, new FtpCallBack() {
            @Override
            public void ftpStart() {

            }

            @Override
            public void ftpSuccess(String remotePath) {
                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }
                Toast.makeText(activity, "下载成功", Toast.LENGTH_SHORT).show();
                installApk(activity, remotePath);
            }

            @Override
            public void ftpFaild(String error) {

            }
        });
    }

    /**
     * 安装APK文件
     */
    public static void installApk(Context context, String filePath) {
        if (filePath == null || filePath.equals("")) {
            return;
        }
        File apkfile = new File(filePath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
