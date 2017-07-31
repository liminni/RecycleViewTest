package com.lixiaoming.recycleviewtest.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * 介绍：星标里的城市bean 作者：zhangxutong 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601 时间： 2016/11/28.
 */

public class XingBiaoBean extends BaseIndexPinyinBean {

    private String city;// 城市名字

    public XingBiaoBean() {

    }

    public XingBiaoBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public XingBiaoBean setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getTarget() {
        return city;
    }
}
