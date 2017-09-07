package com.lixiaoming.recycleviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.lixiaoming.recycleviewtest.R;

public class TestWebviewActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);
        webview = ((WebView) findViewById(R.id.webview));
        initWebView();
    }

    private void initWebView() {

    }
}
