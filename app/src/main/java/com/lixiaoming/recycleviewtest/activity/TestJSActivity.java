package com.lixiaoming.recycleviewtest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.utils.MyJavascriptInterface;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class TestJSActivity extends AppCompatActivity {
    private Context mContext;
    private Activity activity;
    private XWalkView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_js);
        mContext = this;
        activity = this;
        initView();
    }

    private void initView() {
        webView = ((XWalkView) findViewById(R.id.webview));
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient());

        MyJavascriptInterface javascriptInterface = new MyJavascriptInterface(mContext,activity,webView);
        webView.addJavascriptInterface(javascriptInterface,"demo");
        webView.addJavascriptInterface(javascriptInterface,"test");
        webView.addJavascriptInterface(javascriptInterface,"NativeInterface");
        webView.addJavascriptInterface(javascriptInterface,"SysBrowser");
//        webView.loadUrl("file:///android_asset/jsAndroid.html");

        webView.setResourceClient(new XWalkResourceClient(webView) {

            @Override
            public void onLoadStarted(XWalkView view, String url) {
                super.onLoadStarted(view, url);
            }

            @Override
            public void onLoadFinished(XWalkView view, String url) {
                super.onLoadFinished(view, url);
                webView.getNavigationHistory().clear();
            }

            @Override
            public void onProgressChanged(XWalkView view, int progressInPercent) {
                super.onProgressChanged(view, progressInPercent);
                Log.d("fate",view.getUrl());
                if (view.getUrl().contains("exhibitors")){
//                    Intent intent = new Intent(mContext,XingbiaoActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }

            @Override
            public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
                // SSL Should use default handler
                callback.onReceiveValue(true);
            }

        });

        webView.setUIClient(new XWalkUIClient(webView) {
            @Override
            public void onReceivedTitle(XWalkView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onPageLoadStarted(XWalkView view, String url) {
                super.onPageLoadStarted(view, url);
            }

            @Override
            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
                super.onPageLoadStopped(view, url, status);
            }
        });
//        webView.load("file:///android_asset/www4/index.html",null);
//        webView.load("file:///android_asset/jsAndroid.html",null);
        webView.load("http://10.18.20.148:8000/#/home",null);

    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (webView != null) {
//            webView.pauseTimers();
//            webView.onHide();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (webView != null) {
//            webView.resumeTimers();
//            webView.onShow();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (webView != null) {
//            webView.onDestroy();
//        }
//    }

    private int RESULT_CANCLE = -101;

    private int RESULT_FAIL = -102;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Toast.makeText(mContext, "指纹验证成功。。。。。。",
            // Toast.LENGTH_LONG).show();
            Log.d("fate", "指纹验证成功");
        } else if (resultCode == RESULT_CANCLE) {
            // Toast.makeText(mContext, "取消了指纹验证", Toast.LENGTH_LONG).show();
            Log.d("fate", "取消了指纹验证");
        } else if (resultCode == RESULT_FAIL) {
            // Toast.makeText(mContext, "指纹验证失败", Toast.LENGTH_LONG).show();
            Log.d("fate", "指纹验证失败");
        }
    }
}
