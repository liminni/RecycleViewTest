package com.lixiaoming.recycleviewtest.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.aconstant.GloableGonfig;
import com.lixiaoming.recycleviewtest.activity.FingerLoginActivity;
import com.lixiaoming.recycleviewtest.timeselector.DialogUtil;
import com.lixiaoming.recycleviewtest.voice.MediaRecorderActivity;

import org.xwalk.core.JavascriptInterface;
import org.xwalk.core.XWalkView;

/**
 * Created by lixiaoming on 17/2/9.
 */

public class MyJavascriptInterface {

    Context context;

    Activity activity;

    XWalkView webView;

    public MyJavascriptInterface(Context context, Activity activity, XWalkView webView) {
        this.context = context;
        this.activity = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public String testMethod(final String str1, final String str2) {

        String rtnStr;

        rtnStr = str1 + str2;

        // to-do
        return rtnStr;
    }

    @JavascriptInterface
    public String timeSelecor(final String str1, final String str2) {
        String rtnStr;

        rtnStr = str1 + str2 + "timeselector";
        DialogUtil util = new DialogUtil();
        util.showTimeDialog(context, activity, webView);
        return rtnStr;
    }

    @JavascriptInterface
    public void checkVersionUpdate(String downUrl) {
        // 执行下载操作
        // DownLoadUtil util = new DownLoadUtil(context);
        // util.downloadLastedApp(downUrl);
        // 只弹下载进度条
        DownloadManager manager = new DownloadManager(context);
        manager.showDownloadDialog(downUrl);
    }

    @JavascriptInterface
    public String getValue() {
        return "用户名：" + GloableGonfig.USERNAME + "，密码：" + GloableGonfig.PASSWORD;
    }

    @JavascriptInterface
    public void backJin() {
        String packageName = "com.smartdot.mobile.jinchuan";
        String className = "com.smartdot.mobile.jinchuan.activity.StartActivity";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void startRecodeVoice() {
        Toast.makeText(context, "调用录音插件", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), MediaRecorderActivity.class.getName());
        context.startActivity(intent);

        // Intent intent = new Intent(Intent.ACTION_VIEW);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // ComponentName cn = new ComponentName(packageName, className);
        // intent.setComponent(cn);
        // context.startActivity(intent);
    }
    @JavascriptInterface
    public void fingerLogin(){
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), FingerLoginActivity.class.getName());
        activity.startActivityForResult(intent,1009);

    }

}
