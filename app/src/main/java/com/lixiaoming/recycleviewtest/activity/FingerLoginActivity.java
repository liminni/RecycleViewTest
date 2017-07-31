package com.lixiaoming.recycleviewtest.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.fingerprint.CryptoObjectHelper;
import com.lixiaoming.recycleviewtest.fingerprint.MyAuthCallback;

public class FingerLoginActivity extends AppCompatActivity {

    private Context mContext;

    public static final int MSG_AUTH_SUCCESS = 100;

    public static final int MSG_AUTH_FAILED = 101;

    public static final int MSG_AUTH_ERROR = 102;

    public static final int MSG_AUTH_HELP = 103;

    private int RESULT_CANCLE = -101;

    private int RESULT_FAIL = -102;

    private AlertDialog alertDialog;

    private MyAuthCallback myAuthCallback = null;

    /**
     * 取消指纹扫描CryptoObjectHelper
     */
    private CancellationSignal cancellationSignal = null;

    /**
     * 指纹识别
     */
    private FingerprintManagerCompat mFingerprintManager = null;

    /**
     * 验证限制次数 3次
     */
    int count = 0;
    private Handler fingerhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_AUTH_SUCCESS:
                setResultInfo(R.string.fingerprint_success);
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                cancellationSignal = null;
                setResultForActivity(RESULT_OK);
                // FIXME: 2017/4/27 需要再次登录
                break;
            case MSG_AUTH_FAILED:
                count++;
                if (count >= 3) {
                    alertDialog.dismiss();
                    setResultForActivity(RESULT_FAIL);
                    setResultInfo(R.string.above_three);
                }else {
                    setResultInfo(R.string.fingerprint_not_recognized);
                }
                break;
            case MSG_AUTH_ERROR:
                // FIXME: 2017/4/27 发生了错误，需要再次登录
                handleErrorCode(msg.arg1);
                setResultForActivity(RESULT_FAIL);
                break;
            case MSG_AUTH_HELP:
                setResultInfo(R.string.fingerprint_not_recognized);
                setResultForActivity(RESULT_FAIL);
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_login);
        mContext = this;
        initFingerManager();

    }

    // 指纹验证
    private void fingerprintVerification() {
        // TODO 使用指纹登录
        Log.d("fate", "");
        checkFinger();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请使用指纹解锁应用").setIcon(R.mipmap.ic_fp_40px).setCancelable(false).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cancellationSignal.cancel();
                        cancellationSignal = null;
                        setResultForActivity(RESULT_CANCLE);
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 指纹识别
     */
    private void checkFinger() {
        try {
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            if (cancellationSignal == null) {
                cancellationSignal = new CancellationSignal();
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                mFingerprintManager.authenticate(cryptoObjectHelper.buildCryptoObject(), 0, cancellationSignal,
                        myAuthCallback, null);
            } else {
                mFingerprintManager.authenticate(null, 0, cancellationSignal, myAuthCallback, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Fingerprint init failed! Try again!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 返回值为error，不可恢复的错误
     *
     * @param code
     */
    private void handleErrorCode(int code) {
        switch (code) {
        case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
            setResultInfo(R.string.ErrorCanceled_warning);
            break;
        case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
            setResultInfo(R.string.ErrorHwUnavailable_warning);
            break;
        case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
            setResultInfo(R.string.ErrorLockout_warning);
            break;
        case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
            setResultInfo(R.string.ErrorNoSpace_warning);
            break;
        case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
            setResultInfo(R.string.ErrorTimeout_warning);
            break;
        case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
            setResultInfo(R.string.ErrorUnableToProcess_warning);
            break;
        }
    }

    private void setResultInfo(int stringId) {

        Toast.makeText(mContext, getString(stringId), Toast.LENGTH_SHORT).show();
    }

    private void initFingerManager() {
        // 6.0之后才会出现指纹登录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设备SDK版本大于等于23，即6.0之上（包含6.0），则指纹登录可见
            // 检查设备上有没有指纹识别的硬件
            // 用v4包中的方法
            mFingerprintManager = FingerprintManagerCompat.from(this);
            if (!mFingerprintManager.isHardwareDetected()) {
                Toast.makeText(mContext, "该设备不支持指纹识别", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            // 检查设备是否处于安全保护中
            KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (!mKeyguardManager.isKeyguardSecure()) {
                // Toast.makeText(mContext, "设备没有处于安全保护中",
                // Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // 设备是否有注册的指纹
            if (!mFingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(mContext, "设备中没有已注册的指纹,请到设置界面去录制指纹", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                finish();
                return;
            }
            myAuthCallback = new MyAuthCallback(fingerhandler);
            fingerprintVerification();
        } else {
            Toast.makeText(mContext, "您的设备系统版本不支持指纹登录", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setResultForActivity(int resultCode) {
        setResult(resultCode);
        finish();
    }
}
