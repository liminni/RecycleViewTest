package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.MenuRecyclerViewAdapter;
import com.lixiaoming.recycleviewtest.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class TextGridlayoutActivity extends AppCompatActivity {
    /**首页menu的adapter*/
    private MenuRecyclerViewAdapter menuRecyclerViewAdapter;

    /**首页menu的数据源*/
    private List<String> menuDatas;

    /**菜单RecyclerView*/
    private RecyclerView recyclerView_menu;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_gridlayout);
        mContext = this;
        recyclerView_menu = ((RecyclerView) findViewById(R.id.recyclerView_menu));
        initRecyclerViewMenu();
    }
    /**初始化 menu recyclerview的数据*/
    private void initMenuData() {
        menuDatas = new ArrayList<>();
        menuDatas.clear();
        for (int i = 0; i < 8; i++) {
            menuDatas.add("资讯"+i);
        }
    }

    /**初始化menu RecyclerView*/
    private void initRecyclerViewMenu() {
        initMenuData();
        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(mContext,4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        //设置布局管理器
        recyclerView_menu.setLayoutManager(gridLayoutManager);
        recyclerView_menu.setNestedScrollingEnabled(false);
        menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(menuDatas);
        recyclerView_menu.setAdapter(menuRecyclerViewAdapter);
    }
}
