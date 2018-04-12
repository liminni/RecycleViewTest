package com.lixiaoming.recycleviewtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.NewsListAdapter;
import com.lixiaoming.recycleviewtest.bean.NewsBean;
import com.lixiaoming.recycleviewtest.view.MeetingNewsHeadView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2017/10/18.
 * 工商联-经济-客户端-新闻界面
 */

public class NewsFragment extends Fragment implements com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2<ListView> {

    private Context mContext;
    /**fragment的布局view*/
    private View view;
    private PullToRefreshListView pullToRefreshListView;
    /**轮播图数据源*/
    private List<NewsBean> headerDatas;
    /**新闻列表adapter*/
    private NewsListAdapter adapter;
    /**新闻列表数据源*/
    private List<NewsBean> datas;
    /**由PullToRefreshListView获取到的listview用于添加header*/
    private ListView refreshableView;
    /**新闻列表header--轮播图*/
    private MeetingNewsHeadView headView;
    /**用于分页*/
    private int page = 1;
    /**用来判断分页加载是否有数据 true/有数据 false/没有数据*/
    private boolean isMore = true;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1001:
                    //将loading调关闭
                    parserData(msg);
                    break;
                case 1002:
                    parserLoadData(msg);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_news_layout, container, false);
        datas = new ArrayList<>();
        headerDatas = new ArrayList<>();
        initData();
        initHeaderData();
        initView();
        return view;
    }

    private void initData() {
        datas.clear();
        for (int i = 0; i <5; i++) {
            NewsBean bean = new NewsBean();
            bean.title = "全国工商联“五好”县级工商联建设现场座谈会在张家港市召开"+i;
            bean.pic = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2311302072,4151472447&fm=173&s=75839A521603015D54136FA30300500E&w=640&h=400&img.JPG";
            bean.reading_quantity = "867";
            bean.time = "2017-10-18 18:57";
            bean.unit = "全国工商联";
            datas.add(bean);
        }
    }

    /** 初始化轮播图数据 */
    private void initHeaderData() {

        headerDatas.clear();
        NewsBean bean1 = new NewsBean();
        bean1.title = "TitleOne";
        bean1.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492132945&di=fbc060f8ce71c3773a77e7eb9acb82ba&imgtype=jpg&er=1&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160905%2F103-160Z512131H06.jpg";
        NewsBean bean2 = new NewsBean();
        bean2.title = "TitleTwo";
        bean2.pic = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3618727341,3653452767&fm=23&gp=0.jpg";
        NewsBean bean3 = new NewsBean();
        bean3.title = "TitleThree";
        bean3.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491538248604&di=1e680a7fed9d552235fc6e13783c16fd&imgtype=0&src=http%3A%2F%2Fimg.tuku.cn%2Ffile_thumb%2F201505%2Fm2015051022161988.jpg";
        NewsBean bean4 = new NewsBean();
        bean4.title = "TitleFour";
        bean4.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492132999&di=72187582d1a52d26c67623c2653db8db&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.qqjia.com%2Fz%2F01%2Ftu4079_5.jpg";
        NewsBean bean5 = new NewsBean();
        bean5.title = "TitleFive";
        bean5.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491538283390&di=c85f835a4b1a2a8634b6e77a553edd82&imgtype=0&src=http%3A%2F%2Fd.5857.com%2Fwm_150403%2F001.jpg";
        headerDatas.add(bean1);
        headerDatas.add(bean2);
        headerDatas.add(bean3);
        headerDatas.add(bean4);
        headerDatas.add(bean5);
    }
    /** 初始化轮播图数据 */
    private void init2HeaderData() {

        headerDatas.clear();
        NewsBean bean1 = new NewsBean();
        bean1.title = "TitleOne";
        bean1.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492132945&di=fbc060f8ce71c3773a77e7eb9acb82ba&imgtype=jpg&er=1&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160905%2F103-160Z512131H06.jpg";
        NewsBean bean2 = new NewsBean();
        bean2.title = "TitleTwo";
        bean2.pic = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3618727341,3653452767&fm=23&gp=0.jpg";
        NewsBean bean3 = new NewsBean();
        bean3.title = "TitleThree";
        bean3.pic = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491538248604&di=1e680a7fed9d552235fc6e13783c16fd&imgtype=0&src=http%3A%2F%2Fimg.tuku.cn%2Ffile_thumb%2F201505%2Fm2015051022161988.jpg";
        headerDatas.add(bean1);
        headerDatas.add(bean2);
        headerDatas.add(bean3);
    }
    private void initView() {
        pullToRefreshListView = ((PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView));
        //两个boolean型的参数，代表上啦和下啦，true表示对对应的布局进行修改，false则表示使用默认的布局
        ILoadingLayout loadingLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("正在玩儿命刷新.....");
        //往下拉到一定程度，松手
        loadingLayoutProxy.setReleaseLabel("松手开始刷新.....");
        loadingLayoutProxy.setRefreshingLabel("正在拼命加载中.....");
        adapter = new NewsListAdapter(mContext,datas,R.layout.news_item);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);
        headView = new MeetingNewsHeadView(mContext);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        View view = headView.getView();
        view.setLayoutParams(layoutParams);
        refreshableView = pullToRefreshListView.getRefreshableView();
        refreshableView.addHeaderView(view);
        headView.refreshData(headerDatas);

    }

    /**请求数据*/
    private void getData(int what){
     //1001是show loading条，1002时不显示loading
        pullToRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pullToRefreshListView.isRefreshing()){
                    pullToRefreshListView.onRefreshComplete();
                }
            }
        },1000);

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        //请求第一页的数据
        ILoadingLayout loadingLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        loadingLayoutProxy.setPullLabel("正在玩儿命刷新.....");
        //往下拉到一定程度，松手
        loadingLayoutProxy.setReleaseLabel("松手开始刷新.....");
        loadingLayoutProxy.setRefreshingLabel("正在拼命加载中.....");
        page = 1;
        datas.clear();
        initData();
        init2HeaderData();
        headView.refreshData(headerDatas);
        adapter.notifyDataSetChanged();
        getData(1001);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
         if (isMore){
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setPullLabel("正在加载更多内容...");
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载更多内容...");
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setReleaseLabel("正在加载更多内容...");
         }else {
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setPullLabel("没有更多数据了...");
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("没有更多数据了...");
             pullToRefreshListView.getLoadingLayoutProxy(false,true).setReleaseLabel("没有更多数据了...");
         }
        NewsBean bean = new NewsBean();
        bean.title = "全国工商联“五好”县级工商联建设现场座谈会在张家港市召开6666";
        bean.pic = "";
        bean.reading_quantity = "867";
        bean.time = "2017-10-18 18:57";
        bean.unit = "全国工商联";
        datas.add(bean);
        adapter.notifyDataSetChanged();
        getData(1002);
    }
    /**解析数据*/
    private void parserData(Message msg){
      String result = msg.obj.toString();
        //将result解析为实体类
        if ("200".equals("200")){
            //成功,判断请求当前page页的新闻列表的size是否为0，如不为0，则page++，isMore置为true，否则isMore置为false
            //刷新适配器
            if (pullToRefreshListView.isRefreshing()){
                pullToRefreshListView.onRefreshComplete();
            }
        }
    }
    /**解析分页数据*/
    private void parserLoadData(Message msg){
        String result = msg.obj.toString();
        if ("200".equals("200")){
            //成功,判断请求当前page页的新闻列表的size是否为0，如不为0，则page++，isMore置为true，否则isMore置为false
            //刷新适配器
            if (pullToRefreshListView.isRefreshing()) {
                pullToRefreshListView.onRefreshComplete();
            }
        }
    }
}
