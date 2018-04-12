package com.lixiaoming.recycleviewtest.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.fragment.NewsFragment;

public class FICListActivity extends AppCompatActivity {

    private Context mContext;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficlist);
        mContext = this;
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsFragment fragment = new NewsFragment();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}
