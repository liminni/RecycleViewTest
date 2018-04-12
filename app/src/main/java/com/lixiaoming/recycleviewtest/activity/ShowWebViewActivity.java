package com.lixiaoming.recycleviewtest.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lixiaoming.recycleviewtest.R;


public class ShowWebViewActivity extends AppCompatActivity implements OnClickListener {


    WebView webview;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showwebview);
        initView();

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.setDownloadListener(new MyWebViewDownLoadListener());
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.indexOf("tel:") >= 0) {// 页面上有数字会导致连接电话
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(intent);

                } else if (url.indexOf("mailto:") >= 0) {
                    Intent data = new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse(url));
                    data.putExtra(Intent.EXTRA_SUBJECT, "");
                    data.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(data);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                view.setVisibility(View.GONE);
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {// 为webview添加进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
            }

        });
        // webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl(url);
        webview.loadUrl("http://10.18.20.148:8000/#/home");
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 给webview添加downloadlistener 调用downloadManager下载apk文件
     */
    class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        webview.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

}
