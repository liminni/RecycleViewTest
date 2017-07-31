package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;

public class TestPopWindowActivity extends AppCompatActivity {
   private Context mContext;
    private LinearLayout conversation_ll;
    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pop_window);
        mContext = this;
        Button btn = ((Button) findViewById(R.id.btn));
        conversation_ll = ((LinearLayout) findViewById(R.id.conversation_ll));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopuWindow();
                tv_test.setText("hahaha");
            }
        });


    }
    private PopupWindow popupWindow;

    public void showPopuWindow() {
        popupWindow = new PopupWindow(mContext);
        View view = LayoutInflater.from(this).inflate(R.layout.conversation_longclick_layout, null);
        TextView tv_copy = ((TextView) view.findViewById(R.id.tv_copy));
        TextView tv_delete = ((TextView) view.findViewById(R.id.tv_delete));
        TextView tv_retransmission = ((TextView) view.findViewById(R.id.tv_retransmission));
        LinearLayout conversation_root_layout = ((LinearLayout) view.findViewById(R.id.conversation_root_layout));


        tv_retransmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"转发",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);//如果出现点击back键或者触摸屏幕popWindow未关闭，则需要设置popWindows获取焦点
        popupWindow.showAtLocation(conversation_ll, Gravity.CENTER, 0, 0);

        // 给conversation_root_layout添加监听，捕获KEYCODE_BACK，然后关闭popupWindow
        conversation_root_layout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });

        // 点击其他地方，关闭popupWindow
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });

    }
}
