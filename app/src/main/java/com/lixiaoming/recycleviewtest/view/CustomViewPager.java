package com.lixiaoming.recycleviewtest.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.adapter.CustomViewPagerAdapter;
import com.lixiaoming.recycleviewtest.bean.HeadViewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 17/4/1.
 */

public class CustomViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener {

    // 本地数据--type
    public static String IMG_FORMLOCAL = "LOCAL";

    // 网络数据源--type
    public static String IMG_FORMNETWORK = "NETWORK";

    // 数据源的type
    public String TYPE = "";

    // 本地数据源
//    private List<Integer> local_imgData = new ArrayList<>();
//
//    // 网络数据源
//    private List<String> network_imgData = new ArrayList<>();

    // title的数据源
//    private List<String> titleData = new ArrayList<>();

    private List<ImageView> list = new ArrayList<>();

    private List<HeadViewBean> datas = new ArrayList<>();

    private ViewPager viewPager;

    private LinearLayout ll_bottom_bg;

    private TextView tv_title;

    private LinearLayout ll_dot;

    private CustomViewPagerAdapter adapter;

    public boolean isSwitch = false;// 默认不切换

    private int previousPosition = 0;// 默认为0
    /**小圆点的背景*/
    private int dotBackgeoundRes = -1001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1001:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;
            }
        }
    };

    public CustomViewPager(Context context) {
        super(context);
        initView(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        /** 此处需要注意的是，自定义view inflate 布局的时候，是2个参数，第一个参数为resid，第二个为this */
        LayoutInflater.from(context).inflate(R.layout.custom_viewpager, this);
        viewPager = ((ViewPager) findViewById(R.id.custom_vp));
        ll_bottom_bg = ((LinearLayout) findViewById(R.id.custom_ll_bottom_bg));
        tv_title = ((TextView) findViewById(R.id.custom_tv_title));
        ll_dot = ((LinearLayout) findViewById(R.id.custom_ll_dot));
    }

    private void initData(Context context) {
        list.clear();
        int size = getSize(getDataType());
        ImageView iv;
        View dot_view;
        for (int i = 0; i < size; i++) {
            // 给广告位的imgview设置数据
            iv = new ImageView(context);
            ViewPager.LayoutParams imgParams = new ViewPager.LayoutParams();
            imgParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            imgParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            iv.setLayoutParams(imgParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            setImagesrc(context, iv, getDataType(), i);

            list.add(iv);
            // 设置小圆点
            dot_view = new View(context);
            dot_view.setBackgroundResource(getDotBackgroundResouce());
            // 设置小圆点的宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            // 设置小圆点的间距
            if (i != 0) {
                params.leftMargin = 15;
            }
            dot_view.setLayoutParams(params);
            // 设置小圆点的默认状态
            dot_view.setEnabled(false);
            // 将小圆点加到LinearLayout
            ll_dot.addView(dot_view);
        }
        // 根据数据源初始化viewPager的显示
        initViewPager();
    }

    public void initViewPager() {// 必须先设置数据源
        adapter = new CustomViewPagerAdapter(list,viewPager);
        viewPager.setAdapter(adapter);
        // 设置当前要显示的条目
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % list.size());
        viewPager.setCurrentItem(item);
        // 初始化第一次显示的小圆点和文字
        ll_dot.getChildAt(previousPosition).setEnabled(true);
        tv_title.setText(datas.get(previousPosition).title);
        // 这是viewPager的滑动监听事件
        viewPager.addOnPageChangeListener(this);

        // 实现自动切换的功能
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isSwitch) {
                    /***
                     * Thread.sleep()和SystemClock.sleep()方法的区别 Thread.sleep()
                     * 调用的时候可能会出现InterrupttedException SystemClock.sleep()
                     * 会忽略中断异常 如果使用的过程中不存在interrupt的使用,可以使用SystemClock.sleep()
                     */
                    SystemClock.sleep(2000);
                    handler.sendEmptyMessage(1001);// 发消息切换viewPager
                }
                Log.d("fate","停止循环了");
            }
        }).start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        // 用position % 数据源 .size()
        int new_position = position % list.size();
        // 设置新的界面被选中的对应小圆点的状态
        ll_dot.getChildAt(new_position).setEnabled(true);
        // 设置前一个界面对应小圆点的状态
        ll_dot.getChildAt(previousPosition).setEnabled(false);
        tv_title.setText(datas.get(new_position).title);
        previousPosition = new_position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /** 获取数据源长度 */
    public int getSize(String type) {
        int size = 0;
        switch (type) {
        case "LOCAL":
            size = datas.size();
            break;
        case "NETWORK":
            size = datas.size();
            break;
        }
        return size;
    }

    /** 设置数据类型 */
    public void setDataType(String type) {
        this.TYPE = type;
    }

    /** 获取数据类型 */
    public String getDataType() {
        return TYPE;
    }

    /** 根据不同的数据类型设置ImageView的显示 */
    public void setImagesrc(Context context, ImageView img, String type, int position) {
        switch (type) {
        case "LOCAL":
            img.setBackgroundResource(datas.get(position).img);
            break;
        case "NETWORK":
            Glide.with(context).load(datas.get(position).pic).into(img);
            break;
        }
    }

    /** 设置数据类型 本地数据 drawable文件夹下的数据 */
    public void setLocal_imgData(Context context, List<HeadViewBean> datas) {
        this.datas = datas;
        TYPE = IMG_FORMLOCAL;
        initData(context);

    }

    /** 设置数据类型 需要网络加载的数据 */
    public void setNetwork_imgData(Context context, List<HeadViewBean> datas) {
        this.datas = datas;
        TYPE = IMG_FORMNETWORK;
        initData(context);
    }

    /** 设置Title是否可见 */
    public void setTitleVisible(int visible) {
        tv_title.setVisibility(visible);
    }
    /**设置Title的字体*/
    public void setTitleTextSize(float sp){
        tv_title.setTextSize(sp);
    }
    /**设置titile的文本颜色*/
    public void setTitleTextColor(int color){
        tv_title.setTextColor(color);
      }
    /** 设置小圆点的可见性*/
    public void setDotLinearVisible(int visible) {
        ll_dot.setVisibility(visible);
    }
    /**设置位于ViewPager底部的LinearLayout是否可见*/
    public void setBottomLinearLayoutVisiable(int visible){
        ll_bottom_bg.setVisibility(visible);
    }
    /** 设置位于ViewPager底部的LinearLayout的背景 resid */
    public void setBottomLinearLayoutBackgroundResource(int resid) {
        ll_bottom_bg.setBackgroundResource(resid);
    }

    /** 设置位于ViewPager底部的LinearLayout的背景 drawable */
    public void setBottomLinearLayoutBackground(Drawable drawable) {
        ll_bottom_bg.setBackground(drawable);
    }

    /** 设置位于ViewPager底部的LinearLayout的背景 color */
    public void setBottomLinearLayoutBackgroundColor(int color) {
        ll_bottom_bg.setBackgroundColor(color);
    }

    // 设置ViewPager的高度 默认210dp
    public void setViewPagerHeight(Context context,int height) {
        //先将dp值转为px然后再进行设置
        float dip = dip2px(context, height);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,(int) dip);
        viewPager.setLayoutParams(params);
    }
    /**设置viewPager底部的LinearLayout的高度*/
    public void setBottomLinearlayoutHeight(Context context,int height) {
        //先将dp值转为px然后再进行设置
        float dip = dip2px(context, height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dip);
        ll_bottom_bg.setLayoutParams(params);
    }
    /**设置小圆点所在LinearLayout的高度*/
    public void  setDotLinearLayoutHeight(Context context,int height){
        //先将dp值转为px然后再进行设置
        float dip = dip2px(context, height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dip);
        ll_dot.setLayoutParams(params);
    }

    /**
     * 改变小圆点的位置
     * @param context 上下文环境
     * @param gravity 小圆点相对于父布局的位置
     * @param margin_left  左边的margin
     * @param margin_top   上边的margin
     * @param margin_right 右边的margin
     * @param margin_bottom 下边的margin
     */
    public void changeDotPosition(Context context,int gravity,int margin_left,int margin_top,int margin_right,int margin_bottom){
        int left = (int)dip2px(context,margin_left);
        int top = (int)dip2px(context,margin_top);
        int right = (int)dip2px(context,margin_right);
        int bottom = (int)dip2px(context,margin_bottom);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(left,top,right,bottom);
        ll_dot.setLayoutParams(params);
        ll_dot.setGravity(gravity);
    }
    /**
     * 根据手机的分辨率将dp转为px（像素）
     */
    public static float dip2px(Context context, float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率将px(像素)转为dp
     */
    public static float px2dip(Context context, float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue,
                context.getResources().getDisplayMetrics());
    }
    /**获取当前显示的item*/
    public int getCurrentItem(){
        int position = viewPager.getCurrentItem() % list.size();
        return position;
    }
    /**设置ViewPager的点击事件*/
    public void setViewPagerOnTouchListener(OnTouchListener listener){
        viewPager.setOnTouchListener(listener);
    }

    public void setDotBackgroundResouce(int resId){
      this.dotBackgeoundRes = resId;
    }
    public int getDotBackgroundResouce(){
        return dotBackgeoundRes;
    }
    /**
     * 刷新数据
     */
    public void refreshData(List<HeadViewBean> datas) {
        this.datas.clear();
        if (datas != null)
            this.datas.addAll(datas);
        adapter.refreshData();
    }
}
