package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.MyAdapter;
import com.lixiaoming.recycleviewtest.utils.DivierGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现类似 GrideView效果
 */
public class GridLayoutManagerActivity extends AppCompatActivity {
    private Context mConttext;

    private List<String> list;

    private String isUseDivider = "";
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout_manager);
        mConttext = this;
        isUseDivider = getIntent().getStringExtra("isUseDivider");
        initData();
        initView();
    }
    private void initData() {
        list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < 60; i++) {
            list.add("item " + i);
        }
    }

    private void initView() {
        RecyclerView mRecyclerView = ((RecyclerView) findViewById(R.id.recycleview));
        // 类似GrideView效果的 布局管理器
         GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(gridLayoutManager);
        if (isUseDivider.equals("yes")){
            adapter = new MyAdapter("yes",list);
            // 添加GridLayoutManager分割线
            mRecyclerView.addItemDecoration(new DivierGridItemDecoration(mConttext));
        }else if (isUseDivider.equals("no")){
            adapter = new MyAdapter("no",list);
        }
        mRecyclerView.setAdapter(adapter);
        // 设置Item 增加 删除 动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
