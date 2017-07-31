package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.MyAdapter;
import com.lixiaoming.recycleviewtest.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * LinerLayoutManager 横向 纵向 listview 效果
 */
public class LinearLayoutManageActivity extends AppCompatActivity {

    private Context mConttext;

    private List<String> list;

    private String orientation = "";
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_manage);
        mConttext = this;
        orientation = getIntent().getStringExtra("orientation");
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
        // 横向，纵向 类似listview效果 布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // 类似GrideView效果的 布局管理器
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        // 瀑布流布局管理器
        // StaggeredGridLayoutManager staggeredGridLayoutManager = new
        // StaggeredGridLayoutManager(4,
        // StaggeredGridLayoutManager.HORIZONTAL);
        if (orientation.equals("vertical")){
            // 设置LinearLayoutManager 的方向
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            // 1.添加LinearLayoutManager分割线
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mConttext, DividerItemDecoration.VERTICAL_LIST));
            adapter = new MyAdapter("linear_V",list);
        }else if (orientation.equals("horizonal")){
            // 设置LinearLayoutManager 的方向
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            // 1.添加LinearLayoutManager分割线
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mConttext, DividerItemDecoration.HORIZONTAL_LIST));
            adapter = new MyAdapter("linear_H",list);
        }

        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        // 设置Item 增加 删除 动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2.添加GridLayoutManager分割线
//        mRecyclerView.addItemDecoration(new DivierGridItemDecoration(mConttext));
    }
}
