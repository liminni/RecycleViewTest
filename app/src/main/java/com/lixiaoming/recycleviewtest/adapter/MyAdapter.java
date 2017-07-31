package com.lixiaoming.recycleviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;

import java.util.List;

/**
 * Created by lixiaoming on 17/2/7.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> data;
    private String flag ;

    public MyAdapter(String flag,List<String> data){
        this.data = data;
        this.flag = flag;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (flag){
            case "linear_V":
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc,parent,false);
                break;
            case "linear_H":
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc1,parent,false);
                break;
            case "yes"://使用分割线
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_divider,parent,false);
                break;
            case "no"://不使用分割线 使用margin
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_margin,parent,false);
                break;
            case "fixed":
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stagger_1,parent,false);
                break;
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.tv_rc.setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_rc;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_rc = ((TextView) itemView.findViewById(R.id.tv_content));
        }
    }
    public void addData(int position){
        data.add(position,"Insert one");
        notifyItemInserted(position);
    }
    public void removeData(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }
}
