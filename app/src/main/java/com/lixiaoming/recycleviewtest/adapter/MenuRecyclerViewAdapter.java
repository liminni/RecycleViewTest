package com.lixiaoming.recycleviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;

import java.util.List;

/**
 * Created by lixiaoming on 2017/9/22.
 * 首页-菜单-adapter
 */

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder>{

    private List<String> data;

    public MenuRecyclerViewAdapter(List<String> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_recyclerview_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.menu_name_tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView menu_name_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            menu_name_tv = ((TextView) itemView.findViewById(R.id.menu_name_tv));
        }
    }
}
