package com.lixiaoming.recycleviewtest.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.bean.MenuBean;

import java.util.List;

/**
 * Created by lixiaoming on 2018/4/10.
 * 菜单fragment
 */

public class MenuFragment extends Fragment{

    private Context mContext;

    private View view;

    private List<MenuBean> menuBeanList;

    public MenuFragment(){}

    @SuppressLint("ValidFragment")
    public MenuFragment(List<MenuBean> menuBeanList){
         this.menuBeanList = menuBeanList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_menu,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView textView = ((TextView) view.findViewById(R.id.textView));
        textView.setText("菜单个数"+menuBeanList.size());
    }
}
