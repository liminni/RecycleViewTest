package com.lixiaoming.recycleviewtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.bean.XingBiaoBean;
import com.lixiaoming.recycleviewtest.utils.CommonAdapter;
import com.lixiaoming.recycleviewtest.utils.ViewHolder;

import java.util.List;


/**
 * Created by lixiaoming on 2017/7/24.
 */

public class XingBiaoAdapter extends CommonAdapter<XingBiaoBean> {
    private Context context;

    public XingBiaoAdapter(Context context, int layoutId, List<XingBiaoBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final XingBiaoBean xingBiaoBean) {
        holder.setText(R.id.tvCity,xingBiaoBean.getCity());
        holder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fate","====点击了："+xingBiaoBean.getCity());
                Toast.makeText(context,"====点击了："+xingBiaoBean.getCity(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
