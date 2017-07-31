package com.lixiaoming.recycleviewtest.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;

public class StartFingerActivity extends AppCompatActivity {

    private Context mContext;

    /**
     * 指纹识别
     */
    private FingerprintManagerCompat mFingerprintManager = null;

    private int SUCCESS = 301;

    private int FAIL = 302;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_finger);
        mContext = this;
        initFingerManager();

    }

    private void initFingerManager() {
        // 6.0之后才会出现指纹登录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设备SDK版本大于等于23，即6.0之上（包含6.0），则指纹登录可见
            // 检查设备上有没有指纹识别的硬件
            // 用v4包中的方法
            mFingerprintManager = FingerprintManagerCompat.from(this);
            if (!mFingerprintManager.isHardwareDetected()) {
                // Toast.makeText(mContext, "该设备不支持指纹识别",
                // Toast.LENGTH_LONG).show();
                setResultForActivty("该设备不支持指纹识别", FAIL);
                finish();
                return;
            }
            // 检查设备是否处于安全保护中
            KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (!mKeyguardManager.isKeyguardSecure()) {
                // Toast.makeText(mContext, "设备没有处于安全保护中",
                // Toast.LENGTH_SHORT).show();
                setResultForActivty("设备没有处于安全保护中", FAIL);
                finish();
                return;
            }

            // 设备是否有注册的指纹
            if (!mFingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(mContext, "设备中没有已注册的指纹,请到设置界面去录制指纹", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                setResultForActivty("设备中没有已注册的指纹,请到设置界面去录制指纹", FAIL);
                finish();
                return;
            }
            setResultForActivty("可以开启指纹开关", SUCCESS);
            finish();
        } else {
            // Toast.makeText(mContext, "您的设备系统版本不支持指纹登录",
            // Toast.LENGTH_SHORT).show();
            setResultForActivty("您的设备系统版本不支持指纹登录", FAIL);
            finish();
        }
    }

    public void setResultForActivty(String data, int resultCode) {
        Intent intent = new Intent();
        intent.putExtra("info", data);
        setResult(resultCode, intent);
    }
}
