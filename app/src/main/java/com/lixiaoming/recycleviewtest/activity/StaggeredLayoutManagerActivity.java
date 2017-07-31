package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.MyAdapter;
import com.lixiaoming.recycleviewtest.adapter.StraggeredAdapter;
import com.lixiaoming.recycleviewtest.utils.DivierGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流效果
 */
public class StaggeredLayoutManagerActivity extends AppCompatActivity
        implements View.OnClickListener, StraggeredAdapter.onItemClickListener {
    private Context mConttext;

    private List<String> list;

    private String fixedHeight = "";

    private MyAdapter adapter;

    private StraggeredAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_layout_manager);
        mConttext = this;
        fixedHeight = getIntent().getStringExtra("fixedHeight");
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
        TextView tv_add = ((TextView) findViewById(R.id.tv_add));
        TextView tv_delete = ((TextView) findViewById(R.id.tv_delete));

        tv_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);

        RecyclerView mRecyclerView = ((RecyclerView) findViewById(R.id.recycleview));
        // 瀑布流布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = null;
        if (fixedHeight.equals("fixed")) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
            adapter = new MyAdapter("fixed", list);
            mRecyclerView.setAdapter(adapter);
            // 添加StaggeredGridLayoutManager分割线
            mRecyclerView.addItemDecoration(new DivierGridItemDecoration(mConttext));
            // 设置布局管理器
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else if (fixedHeight.equals("unfixed")) {
            adapter1 = new StraggeredAdapter(list);
            mRecyclerView.setAdapter(adapter1);
            adapter1.setOnItemClickListener(this);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            // 设置布局管理器
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        }

        // 设置Item 增加 删除 动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.tv_add:
            if (fixedHeight.equals("fixed")) {
                adapter.addData(1);
            } else {
                adapter1.addData(1);
            }
            break;
        case R.id.tv_delete:
            if (fixedHeight.equals("fixed")) {
                adapter.removeData(1);
            } else {
                adapter1.removeData(1);
            }
            break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        adapter1.addData(2);
        Toast.makeText(mConttext,position+" click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        adapter1.removeData(2);
        Toast.makeText(mConttext,position+" long click",Toast.LENGTH_LONG).show();
    }
}
