package com.lixiaoming.recycleviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 17/2/7.
 */

public class StraggeredAdapter extends RecyclerView.Adapter<StraggeredAdapter.ViewHolder> {

    private List<String> data;

    private List<Integer> heights;

    private  onItemClickListener mOnItemClickListener;

    public StraggeredAdapter(List<String> data) {
        this.data = data;
        heights = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            heights.add((int) (100 + Math.random() * 300));
        }
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stagger_2, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.tv_rc.getLayoutParams();
        layoutParams.height = heights.get(position);
        holder.tv_rc.setLayoutParams(layoutParams);
        holder.tv_rc.setText(data.get(position));
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rc;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
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

    public interface  onItemClickListener{
        public void onItemClick(View view,int position);
        public void onItemLongClick(View view,int position);
    }
}
