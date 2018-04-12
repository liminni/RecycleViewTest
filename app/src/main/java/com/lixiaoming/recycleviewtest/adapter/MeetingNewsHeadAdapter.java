package com.lixiaoming.recycleviewtest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.bean.NewsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 轮播图适配器 */
@SuppressLint({ "UseSparseArrays", "InflateParams" })
public class MeetingNewsHeadAdapter extends PagerAdapter {
    List<NewsBean> data; // 正常的数据

    List<NewsBean> tmpdata; // 为了手动循环滑动而添加的临时数据

    Map<Integer, Bitmap> maps = new HashMap<Integer, Bitmap>();

    ViewPager mViewPager;

    Context context;

    public MeetingNewsHeadAdapter(Context context, List<NewsBean> data, ViewPager mViewPager) {
        this.tmpdata = data;
        this.data = data;
        this.context = context;
        this.mViewPager = mViewPager;
    }

    /** 刷新数据 */
    public void refreshData() {
        maps.clear();
        notifyDataSetChanged();
        mViewPager.setCurrentItem(0);
    }

    @Override
    public int getCount() {
        return tmpdata.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        // 在此不执行销毁操作
        ((ViewPager) view).removeView((View) object);
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.scroll_headview_item, null);
        final ImageView imgv = (ImageView) v.findViewById(R.id.scroll_headview_item_icon);
        TextView title = (TextView) v.findViewById(R.id.scroll_headview_item_title);
        view.addView(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳转事件 数据源为tmpdata
            }
        });

        NewsBean item = ((NewsBean) tmpdata.get(position));
        title.setText(item.title.trim());
        if (maps.get(position) != null) {
            imgv.setImageBitmap(maps.get(position));
        } else {
            Glide.with(context).load(item.pic).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    maps.put(position, resource);
                    if (resource != null)
                        imgv.setImageBitmap(resource);
                }
            });
        }

        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
