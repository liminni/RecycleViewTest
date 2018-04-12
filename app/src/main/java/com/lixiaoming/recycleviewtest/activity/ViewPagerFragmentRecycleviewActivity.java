package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.MenuFragmentPagerAdapter;
import com.lixiaoming.recycleviewtest.bean.MenuBean;
import com.lixiaoming.recycleviewtest.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用viewPager+fragment+RC实现多菜单分页
 */
public class ViewPagerFragmentRecycleviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Context mContext;

    private List<Fragment> fragments;

    private List<MenuBean> menuBeanList_first;

    private List<MenuBean> menuBeanList_second;

    private List<MenuBean> menuBeanList_total;

    private ViewPager viewPager;

    private LinearLayout indicator_ll;

    private MenuFragmentPagerAdapter menuFragmentPagerAdapter;

    private List<View> indicatorViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment_recycleview);
        initData();
        initView();
        mContext = this;
    }

    private void initData() {
        menuBeanList_total = new ArrayList<>();
        menuBeanList_first = new ArrayList<>();
        menuBeanList_second = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            MenuBean bean = new MenuBean();
            bean.menu_name = "菜单" + i;
            bean.menuID = "" + i;
            bean.menuIconRes = R.mipmap.ic_launcher;
            menuBeanList_total.add(bean);
        }
    }

    private void initView() {
        fragments = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator_ll = (LinearLayout) findViewById(R.id.indicator_ll);

        addFragments(menuBeanList_total);
        initIndicator(menuBeanList_total);

        menuFragmentPagerAdapter = new MenuFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(menuFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }
    /**初始化指示器*/
    private void initIndicator(List<MenuBean> menuBeanList) {
        indicatorViews = new ArrayList<>();
        indicatorViews.clear();
        indicator_ll.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40,8);
        params.setMargins(8,0,8,0);
        if (menuBeanList.size() > 10 && menuBeanList.size() <= 20){
            indicator_ll.setVisibility(View.VISIBLE);
            View indocator_view_first = new View(this);
            indocator_view_first.setBackgroundResource(R.drawable.indicator_blue);
            indocator_view_first.setLayoutParams(params);
            View indocator_view_second = new View(this);
            indocator_view_second.setBackgroundResource(R.drawable.indicator_gray);
            indocator_view_second.setLayoutParams(params);
            indicator_ll.addView(indocator_view_first);
            indicator_ll.addView(indocator_view_second);
            indicatorViews.add(indocator_view_first);
            indicatorViews.add(indocator_view_second);
        }else {
            indicator_ll.setVisibility(View.GONE);
            View indocator_view_first = new View(this);
            indocator_view_first.setBackgroundResource(R.drawable.indicator_blue);
            indocator_view_first.setLayoutParams(params);
            indicator_ll.addView(indocator_view_first);
            indicatorViews.add(indocator_view_first);
        }
    }

    private void addFragments(List<MenuBean> menuBeanList) {
        fragments.clear();
     if (menuBeanList.size() > 10 && menuBeanList.size() <= 20){
         for (int i = 0; i < menuBeanList.size(); i++) {
             if (i < 10){
                 menuBeanList_first.add(menuBeanList.get(i));
             }else {
                 menuBeanList_second.add(menuBeanList.get(i));
             }
         }
         MenuFragment menuFragment_first = new MenuFragment(menuBeanList_first);
         MenuFragment menuFragment_second = new MenuFragment(menuBeanList_second);
         fragments.add(menuFragment_first);
         fragments.add(menuFragment_second);
     }else {
         MenuFragment menuFragment = new MenuFragment(menuBeanList);
         fragments.add(menuFragment);
     }
//     menuFragmentPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == position){
                indicatorViews.get(i).setBackgroundResource(R.drawable.indicator_blue);
            }else {
                indicatorViews.get(i).setBackgroundResource(R.drawable.indicator_gray);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
