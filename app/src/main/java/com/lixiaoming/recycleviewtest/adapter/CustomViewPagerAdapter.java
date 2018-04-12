package com.lixiaoming.recycleviewtest.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by lixiaoming on 17/4/1.
 */

public class CustomViewPagerAdapter extends PagerAdapter {
    private List<ImageView> data;
    private ViewPager viewPager;
    public CustomViewPagerAdapter(List<ImageView> data, ViewPager viewPager){
        this.data = data;
        this.viewPager = viewPager;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    //是否复用当前view对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    //初始化每个条目要显示的内容
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //使用position % 数据源 .size()，防止下标越界异常
        int new_position = position % data.size();
        ImageView iv = data.get(new_position);
        //将ImageView添加到容器中
        container.addView(iv);
        return iv;
    }
    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //不需要调用父类方法
        // super.destroyItem(container, position, object);
    }
    /** 刷新数据 */
    public void refreshData() {
        notifyDataSetChanged();
        viewPager.setCurrentItem(0);
    }
}
