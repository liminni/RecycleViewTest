package com.lixiaoming.recycleviewtest.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * 介绍：星标城市列表 HeaderView Bean 作者：zhangxutong 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601 时间： 2016/11/28.
 */

public class XingBiaoHeaderBean extends BaseIndexPinyinBean {

    private List<String> cityList;

    // 悬停ItemDecoration显示的Tag
    private String suspensionTag;

    public XingBiaoHeaderBean() {

    }

    public XingBiaoHeaderBean(List<String> cityList, String suspensionTag, String indexBarTag) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
        this.setBaseIndexTag(indexBarTag);
    }

    public List<String> getCityList() {
        return cityList;
    }

    public XingBiaoHeaderBean setCityList(List<String> cityList) {
        this.cityList = cityList;
        return this;
    }

    public XingBiaoHeaderBean setSuspensionTag(String suspensionTag) {
        this.suspensionTag = suspensionTag;
        return this;
    }

    @Override
    public String getSuspensionTag() {
        return suspensionTag;
    }

    @Override
    public String getTarget() {
        return null;
    }

    @Override
    public boolean isNeedToPinyin() {
        return false;// 头部不需要拼音排序
    }
}
