package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.XingBiaoAdapter;
import com.lixiaoming.recycleviewtest.bean.XingBiaoBean;
import com.lixiaoming.recycleviewtest.bean.XingBiaoHeaderBean;
import com.lixiaoming.recycleviewtest.utils.CommonAdapter;
import com.lixiaoming.recycleviewtest.utils.HeaderRecyclerAndFooterWrapperAdapter;
import com.lixiaoming.recycleviewtest.utils.ViewHolder;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;



public class XingbiaoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;

    // 设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceData;

    // 头部数据集
    private List<XingBiaoHeaderBean> mHeaderData;

    // 主体部分数据
    private List<XingBiaoBean> mBodyData;

    // 主体部分的adapter
    private XingBiaoAdapter mAdapter;

    // 头部Adapter
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;

    // 侧边索引栏
    private IndexBar mIndexBar;

    // 显示指示器DialogText
    private TextView mTvSideBarHint;

    private RecyclerView mRv;

    // 自定义ItemDecoration，显示悬浮标题
    private SuspensionDecoration mDecoration;

    private LinearLayoutManager manager;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xingbiao);
        mContext = this;
        initData();
        initView();
    }

    private void initData() {
        mSourceData = new ArrayList<>();
        mBodyData = new ArrayList<>();
        mHeaderData = new ArrayList<>();
        List<String> xingBiaoCities = new ArrayList<>();
        mHeaderData.add(new XingBiaoHeaderBean(xingBiaoCities, "星标城市", "★"));

        mSourceData.addAll(mHeaderData);

    }

    private void initView() {
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout));
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(this);
        manager = new LinearLayoutManager(mContext);
        mRv = ((RecyclerView) findViewById(R.id.rv));
        mIndexBar = ((IndexBar) findViewById(R.id.indexBar));
        mTvSideBarHint = ((TextView) findViewById(R.id.tvSideBarHint));
        mRv.setLayoutManager(manager);
        initAdapter();
        mRv.setAdapter(mHeaderAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(mContext, mSourceData)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,
                        getResources().getDisplayMetrics()))
                .setColorTitleBg(Color.parseColor("#d5d5d5"))
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                        getResources().getDisplayMetrics()))
                .setColorTitleFont(Color.parseColor("#000000"))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderData.size()));
        // mRv.addItemDecoration(new DividerItemDecoration(mContext,
        // DividerItemDecoration.VERTICAL_LIST));

        mIndexBar.setmPressedShowTextView(mTvSideBarHint).setNeedRealIndex(false).setmLayoutManager(manager)
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderData.size());

        initData(getResources().getStringArray(R.array.provinces));
    }

    private void initData(final String[] stringArray) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBodyData.clear();
                for (int i = 0; i < stringArray.length; i++) {
                    XingBiaoBean bean = new XingBiaoBean();
                    bean.setCity(stringArray[i]);// 设置城市名
                    mBodyData.add(bean);
                }
                // step1:排序
                mIndexBar.getDataHelper().sortSourceDatas(mBodyData);
                // step2:设置数据源
                mAdapter.setDatas(mBodyData);
                mHeaderAdapter.notifyDataSetChanged();
                // step3:给侧边索引栏设置数据源
                mSourceData.addAll(mBodyData);
                mIndexBar.setmSourceDatas(mSourceData).invalidate();
                mDecoration.setmDatas(mSourceData);

            }
        }, 300);
        // 加载头部数据
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                XingBiaoHeaderBean bean = mHeaderData.get(0);
                List<String> cities = new ArrayList<String>();
                cities.add("北京");
                cities.add("天津");
                cities.add("湖南");
                cities.add("河北");
                cities.add("青海");
                bean.setCityList(cities);
                mHeaderAdapter.notifyItemRangeChanged(0, 1);
            }
        }, 500);
    }

    private void initAdapter() {
        mAdapter = new XingBiaoAdapter(mContext, R.layout.meituan_item_select_city, mBodyData);
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                case R.layout.meituan_item_header:
                    XingBiaoHeaderBean xingBiaoHeaderBean = ((XingBiaoHeaderBean) o);
                    RecyclerView recyclerView = ((RecyclerView) holder.getView(R.id.rvCity));
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    recyclerView.setLayoutManager(manager);
                    // recyclerView.addItemDecoration(new
                    // DividerItemDecoration(mContext,
                    // DividerItemDecoration.VERTICAL_LIST));
                    recyclerView.setAdapter(new CommonAdapter<String>(mContext, R.layout.xingbiao_head_item,
                            xingBiaoHeaderBean.getCityList()) {
                        @Override
                        public void convert(ViewHolder holder, final String cityName) {
                            holder.setText(R.id.tvCity, cityName);
                            holder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(mContext, "星标城市：" + cityName, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    break;
                }
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header, mHeaderData.get(0));
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
        for (int i = 0; i < 5; i++) {
            mBodyData.add(new XingBiaoBean("东京"));
            mBodyData.add(new XingBiaoBean("大阪"));
        }
        // 先排序
        mIndexBar.getDataHelper().sortSourceDatas(mBodyData);
        mSourceData.clear();
        mSourceData.addAll(mHeaderData);
        mSourceData.addAll(mBodyData);

        mHeaderAdapter.notifyDataSetChanged();
        mIndexBar.invalidate();
    }

    @Override
    public void onRefresh() {
        Log.d("fate","下拉刷新");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    mBodyData.add(new XingBiaoBean("鞍山"));
                    mBodyData.add(new XingBiaoBean("常州"));
                }
                XingBiaoHeaderBean xingBiaoHeaderBean = mHeaderData.get(0);
                xingBiaoHeaderBean.getCityList().add("内蒙古");
                // 先排序
                mIndexBar.getDataHelper().sortSourceDatas(mBodyData);
                mSourceData.clear();
                mSourceData.addAll(mHeaderData);
                mSourceData.addAll(mBodyData);
                 if (swipeRefreshLayout.isRefreshing()){
                     swipeRefreshLayout.setRefreshing(false);
                 }
                mHeaderAdapter.notifyDataSetChanged();
                mIndexBar.setmSourceDatas(mSourceData).invalidate();
            }
        });
    }
}
