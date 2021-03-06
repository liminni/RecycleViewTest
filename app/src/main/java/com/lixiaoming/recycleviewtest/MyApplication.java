package com.lixiaoming.recycleviewtest;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.lixiaoming.recycleviewtest.utils.SSLSocketFactoryCompat;
import com.wanjian.cockroach.Cockroach;

import java.io.InputStream;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by lixiaoming on 2017/6/22.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.COCKROACH_DEBUG) {
            install();
        }

        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getClient()));

    }

    private void install() {
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{ 你的异常处理逻辑 }catch(Throwable e){ }
            // ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                // 开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                // 由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new
                // Handler(Looper.getMainLooper())，
                // 所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                // new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            Toast.makeText(MyApplication.this,
                                    "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT)
                                    .show();
                            // throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });

    }

    public synchronized static OkHttpClient getClient() {
        OkHttpClient okHttpClient = null;
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            try {
                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                final X509TrustManager trustAllCert = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[] {};
                    }
                };
                final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
//                okHttpClient.setSslSocketFactory(sslSocketFactory);
                 builder.sslSocketFactory(sslSocketFactory, trustAllCert);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
             okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
