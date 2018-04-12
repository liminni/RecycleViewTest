package com.lixiaoming.recycleviewtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2018/4/10.
 * 菜单分页adapter
 */

public class MenuFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public MenuFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        if (fragments == null) {
            this.fragments = new ArrayList<Fragment>();
        } else {
            this.fragments = fragments;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
