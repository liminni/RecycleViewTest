package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.utils.RSAUtils;

public class RSAActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private TextView tv_content;

    private Button btn_jiami;

    private TextView tv_jiami_result;

    private Button btn_jiemi;

    private TextView tv_jiemi_result;


    private String JIA_MI_CONTENT = "{\"code\":\"0\",\"message\":\"登录成功\",\"memberDetail\":{\"userid\":\"sunjd\",\"usertype\":\"guest\",\"name\":\"孙俊东\",\"phonenum\":\"\",\"email\":\"\",\"company\":\"\",\"duty\":\"\",\"sex\":\"\",\"group\":\"\",\"imageurl\":\"http://172.20.95.128/file/upload/defaultImage/default.png\",\"hotel\":\"\",\"roomnum\":\"\",\"mark\":\"\"}}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);
        mContext = this;
        initView();
        RSAUtils rsaUtils = new RSAUtils(mContext);
        rsaUtils.getKey();

    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_jiami = (Button) findViewById(R.id.btn_jiami);
        tv_jiami_result = (TextView) findViewById(R.id.tv_jiami_result);
        btn_jiemi = (Button) findViewById(R.id.btn_jiemi);
        tv_jiemi_result = (TextView) findViewById(R.id.tv_jiemi_result);
        tv_content.setText(JIA_MI_CONTENT);

        btn_jiami.setOnClickListener(this);
        btn_jiemi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_jiami:
            try {
                String encryptWithBase64 = RSAUtils.encryptWithBase64(JIA_MI_CONTENT);
                tv_jiami_result.setText(encryptWithBase64);
                Log.d("fate",encryptWithBase64);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btn_jiemi:
            try {
                String decryp_str = tv_jiami_result.getText().toString();
                String decryptWithBase64 = RSAUtils.decryptWithBase64(aa);
                tv_jiemi_result.setText(decryptWithBase64);
                Log.d("fate",decryptWithBase64);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }
    private String aa = "HGEmMUv8MsRPD0HLIDa2K+V0qlHSIcXmenCBLn9+dyHqDDDlj08PRcqz1kSWIOdiIq5aLHkDP6e/\n" +
            "ufcM16VZqGVzPNuLv08sH1dxarmHk8g2FquC/nAE0NGkZIpkfnux0jxtn1PovJlf7SCc8vpfugaE\n" +
            "ISGZzJSjvL9Qi2PayCdWGTbor9iuuaAYGLP1VyQi/ZeoSvrcOmLc8MwO3Ok8u4+z2sKtJlckXfNt\n" +
            "wF07OjNF+fiIZVBdfQkI6ZDJx6NN59wRVEn7E3C6y3qVkazUsCKPfdMtO2RFHJ8CI2cK73cWcFky\n" +
            "pQ1/8qbw5gK1Z8wYVf4JJWYM9a5+ONzj/ZLuH2yC79i0ZAfOVAOAg/Ol/bdS4oqd77HDqzx8Lnis\n" +
            "+8je/PGODVnDmxnpnVN5ROpGD480WZp6Z7cRTRN0mO1Mz+eH2ib4bkjvGVzu5d1wGwgz2TFcXAzK\n" +
            "Yau73C/1SVvUEqipTGgL2TBFvgRsPmm8g6p9DwxrIQ2+y8S2qsgVc5hr";
    private String test = "hD4QS/WGHa2JSKdKRvEMeRkTVsaUyO/svYQ+cznGccbODtfqXpnDnwX43cwZue2eX9fPHDmpZLPP7dXyhT2h+UFEQhGuHq/VomXgcv0eFbSJYxs/OFclk8GffKYD74xaj4sSkxkTabm7gzSOt5LaR7BjwwhgWWn3m/wLf0QDU/g=";

}
