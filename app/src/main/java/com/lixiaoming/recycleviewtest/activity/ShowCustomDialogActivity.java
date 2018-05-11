package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;

public class ShowCustomDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_1;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_dialog);
        mContext = this;
        initView();
    }

    private void initView() {
        btn_1 = (Button) findViewById(R.id.btn_1);

        btn_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                showCustomDialog();
                break;
        }
    }

    /** 显示一个自定义view的dialog */
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(new EditText(this));
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_layout, null);
        final EditText rongGroupName_et = ((EditText) view.findViewById(R.id.rongGroupName_et));
        TextView cancel_tv = ((TextView) view.findViewById(R.id.cancel_tv));
        TextView ok_tv = ((TextView) view.findViewById(R.id.ok_tv));
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Log.d("fate","cancel");
                Toast.makeText(mContext,"cancel:"+rongGroupName_et.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Log.d("fate","ok");
                Toast.makeText(mContext,"ok:"+rongGroupName_et.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
