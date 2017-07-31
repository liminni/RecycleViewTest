package com.lixiaoming.recycleviewtest.utils;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lixiaoming on 17/2/20.
 */

public class DownLoadUtil {
    public Context context;
    public DownLoadUtil(Context context){
        this.context = context;
    }
    /** 下载新的版本 */
    public void downloadLastedApp(final String downloadUrl) {
        new AlertDialog.Builder(context).setTitle("更新提醒 ").setMessage("检测到有新版本！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadManager manager = new DownloadManager(context);
                        manager.showDownloadDialog(downloadUrl);
                        dialog.dismiss();
                    }
                }).show();
    }
}
