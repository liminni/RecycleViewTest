package com.lixiaoming.recycleviewtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.bean.NewsBean;

import java.util.List;

/**
 * Created by lixiaoming on 2017/10/18.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;

    private List<NewsBean> datas;

    public NewsAdapter(Context context, List<NewsBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            holder.tv_title = ((TextView) convertView.findViewById(R.id.tv_title));
            holder.tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
            holder.tv_number = ((TextView) convertView.findViewById(R.id.tv_number));
            holder.tv_unit = ((TextView) convertView.findViewById(R.id.tv_unit));
            holder.iv_pic = ((ImageView) convertView.findViewById(R.id.iv_pic));
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }
        holder.tv_title.setText(datas.get(position).title);
        holder.tv_time.setText(datas.get(position).time);
        holder.tv_number.setText(datas.get(position).reading_quantity);
        holder.tv_unit.setText(datas.get(position).unit);
        if (datas.get(position).pic != null && !datas.get(position).pic.equals("")) {
            Glide.with(context).load(datas.get(position).pic).into(holder.iv_pic);
        } else {
            holder.iv_pic.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title, tv_time, tv_number, tv_unit;

        ImageView iv_pic;
    }
}
