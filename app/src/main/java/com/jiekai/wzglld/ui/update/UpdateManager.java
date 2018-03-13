package com.jiekai.wzglld.ui.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.jiekai.wzglld.config.ShareConstants;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.UpdateEntity;
import com.jiekai.wzglld.utils.JSONHelper;
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

public class UpdateManager implements HaveUpdateInterface {
    private String localPath = Environment.getExternalStorageDirectory().getPath()+"/liu/";
    private Activity activity;
    private UpdateEntity updateData;
    private UpdateHaveUpdateDialog haveUpdateDialog;
    private UpdateLoadingDialog loadingDialog;

    private String alreadyLoadingApkPath = null;
    private boolean isAlreadayLoaddingApk = false;  //已经下载完成Apk了吗

    public UpdateManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * 比较服务器版本和本地版本
     */
    private void compareRemoteVersion(Context context) {
        int localVersion = getLocalVersion(context);

        UpdateEntity historyData = getLoadingHistroyData();
//        if (historyData != null && historyData.getVERSION() >= updateData.getVERSION()
//                && !StringUtils.isEmpty(historyData.getLocalPath())) {
//            String localPath =  historyData.getLocalPath();
//            File file = new File(localPath);
//            if (file.exists()) {
//                alreadyLoadingApkPath = localPath;
//                isAlreadayLoaddingApk = true;
//                haveUpdateDialog = new UpdateHaveUpdateDialog(false, false, "您已经下载完毕了更新文件，可以直接更新！", this);
//                haveUpdateDialog.show(activity.getFragmentManager(), "have_update");
//                return;
//            }
//        }
        if (updateData != null
                && updateData.getVERSION() != -1 && localVersion != -1
                && updateData.getVERSION() > localVersion) {
                    isAlreadayLoaddingApk = false;
                    haveUpdateDialog = new UpdateHaveUpdateDialog(false, false, updateData.getINFO(), this);
                    haveUpdateDialog.show(activity.getFragmentManager(), "have_update");
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
            Toast.makeText(activity, "路径错误", Toast.LENGTH_SHORT).show();
            return;
        }
        FtpManager.getInstance().downloadFile(localFilePath, remoteFilePath, remoteFileName, new FtpCallBack() {
            @Override
            public void ftpStart() {
                loadingDialog = new UpdateLoadingDialog(false, false);
                loadingDialog.show(activity.getFragmentManager(), "loading_dialog");
            }

            @Override
            public void ftpProgress(long allSize, long currentSize, int process) {
                loadingDialog.setProgressBar(allSize, currentSize, process);
            }

            @Override
            public void ftpSuccess(String remotePath) {
                updateData.setLocalPath(remotePath);
                loadingDialog.hideDialog();
                installApk(activity, remotePath);
                saveLoadingData();
            }

            @Override
            public void ftpFaild(String error) {
                loadingDialog.hideDialog();
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
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

    private void saveLoadingData() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ShareConstants.UPDATE_LOADING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String loadingData = JSONHelper.toJSONString(updateData);
        editor.putString(ShareConstants.UPDATE_LOADING, loadingData);
        editor.commit();
    }

    private UpdateEntity getLoadingHistroyData() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ShareConstants.UPDATE_LOADING, Context.MODE_PRIVATE);
        String updata = sharedPreferences.getString(ShareConstants.UPDATE_LOADING, "");
        if (updata != null && updata.length() != 0) {
            return JSONHelper.fromJSONObject(updata, UpdateEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public void enterDownLoad() {
        if (isAlreadayLoaddingApk) {
            installApk(activity, alreadyLoadingApkPath);
        } else {
            String path = updateData.getPATH();
            int nameAndPath = path.lastIndexOf("/");        ///View/AppImage/app/lingdao.apk
            if (nameAndPath != -1) {
                String remotName = path.substring(nameAndPath + 1, path.length());
                String remotPath = path.substring(0, nameAndPath);
                downloadApk(localPath, remotPath, remotName);
            } else {
                Toast.makeText(activity, "路径错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void cancleDownLoad() {
        if ("1".equals(updateData.getFORCE())) {
            System.exit(0);
        }
    }
}
