package com.lixiaoming.recycleviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.bean.NewsBean;

import java.util.List;

/**
 * Created by lixiaoming on 2017/10/19.
 * 新闻适配器
 */

public class NewsListAdapter extends CommonAdapter<NewsBean>{

    public NewsListAdapter(Context context, List<NewsBean> datas, int itemLayoutResId) {
        super(context, datas, itemLayoutResId);
    }

    @Override
    public void convert(ViewHolder viewHolder, NewsBean bean) {
        viewHolder.setText(R.id.tv_title,bean.title);
        viewHolder.setText(R.id.tv_time,bean.time);
        viewHolder.setText(R.id.tv_unit,bean.unit);
        viewHolder.setText(R.id.tv_number,bean.reading_quantity);
        ImageView iv_pic = ((ImageView) viewHolder.getView(R.id.iv_pic));
        if (bean.pic != null && !bean.pic.equals("")){
            Glide.with(context).load(bean.pic).into(iv_pic);
            iv_pic.setVisibility(View.VISIBLE);
        }else {
            iv_pic.setVisibility(View.GONE);
        }
    }
}
