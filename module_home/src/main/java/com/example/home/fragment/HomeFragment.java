package com.example.home.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.loader.GlideImageLoader;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.home.fragment.entity.ComplexItemEntity;
import com.example.home.fragment.util.ComplexViewMF;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

@Route(path = RouterUtil.Home_Fragment_Main)
public class HomeFragment extends BaseFragment<HomePresent> {


    private Banner banner;
    private PurseGoodsAdapter purseGoodsAdapter;
    private RecyclerView ryPurse;
    private List<GoodsGson> goodsGsons = new ArrayList<>();
    private ObservableScrollView slHome;
    private int mHeight;
    private LinearLayout llTitle;
    private TextView tvSearch;

    private ImageView ivKind, ivShopCar;
    private SmartRefreshLayout srHome;

    @Override
    public void initData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {
        srHome = view.findViewById(R.id.srHome);
        banner = view.findViewById(R.id.banner);
        ryPurse = view.findViewById(R.id.ryPurse);
        slHome = view.findViewById(R.id.slHome);
        llTitle = view.findViewById(R.id.llTitle);
        tvSearch = view.findViewById(R.id.tvSearch);
        ivKind = view.findViewById(R.id.ivKind);
        ivShopCar = view.findViewById(R.id.ivShopCar);


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ryPurse.setLayoutManager(staggeredGridLayoutManager);
        initData();//初始化数据
        ryPurse.setItemAnimator(new DefaultItemAnimator());
        banner.setBannerStyle(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545032090&di=659174bccea53c9461eed981cecfc15e&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01274c5841176fa801219c77c4ecd6.png%402o.png");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369676&di=12100c24b51fa3ee49f97394d4739de9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016ad958eddb0ba8012049ef9f8413.jpg%401280w_1l_2o_100sh.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369676&di=dc08ba14ee8a3fed755f36bde08647ea&imgtype=0&src=http%3A%2F%2Fpic101.nipic.com%2Ffile%2F20160621%2F23040058_143914199524_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369673&di=d529c1a0f188d5e097977c8678d7ccbb&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c9db56c5706832f875520f596bba.png%401280w_1l_2o_100sh.png");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369673&di=2d3458515c5badccdb40accde18f5b77&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01538558abd2a0a801219c773a1c5d.jpg%401280w_1l_2o_100sh.jpg");
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();

        GoodsGson goodsGson = new GoodsGson();
        goodsGson.setImgUrl("https://gd4.alicdn.com/imgextra/i3/3133163697/O1CN01vIKMhK1dBGjaWkcdp_!!3133163697.jpg_400x400.jpg");
        goodsGson.setPrice("¥99.00");
        goodsGson.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson);

        GoodsGson goodsGson1 = new GoodsGson();
        goodsGson1.setImgUrl("https://gd1.alicdn.com/imgextra/i4/3573721295/O1CN01BClOdS1LR9Nuc7o1c_!!3573721295.jpg_400x400.jpg");
        goodsGson1.setPrice("¥99.00");
        goodsGson1.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson1);

        GoodsGson goodsGson2 = new GoodsGson();
        goodsGson2.setImgUrl("https://gd4.alicdn.com/imgextra/i4/3573721295/O1CN019k1xGy1LR9NvunXbw_!!3573721295.jpg");
        goodsGson2.setPrice("¥99.00");
        goodsGson2.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson2);

        GoodsGson goodsGson3 = new GoodsGson();
        goodsGson3.setImgUrl("https://gd1.alicdn.com/imgextra/i1/3573721295/O1CN01vbPoUm1LR9Nuc6Ofy_!!0-item_pic.jpg");
        goodsGson3.setPrice("¥99.00");
        goodsGson3.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson3);

        GoodsGson goodsGson4 = new GoodsGson();
        goodsGson4.setImgUrl("https://gd1.alicdn.com/imgextra/i3/2452623060/O1CN011yVtcD1YTWR9UyVpX_!!2452623060.jpg_400x400.jpg");
        goodsGson4.setPrice("¥99.00");
        goodsGson4.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson4);
        GoodsGson goodsGson5 = new GoodsGson();
        goodsGson5.setImgUrl("https://img.alicdn.com/imgextra/i4/253650070/O1CN01pLOz1g1CO6Clo12b5_!!0-saturn_solar.jpg_220x220.jpg_.webp");
        goodsGson5.setPrice("¥99.00");
        goodsGson5.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson5);
        GoodsGson goodsGson6 = new GoodsGson();
        goodsGson6.setImgUrl("https://img.alicdn.com/imgextra/i3/253650070/O1CN01sucKD11CO6Ct31Hki_!!0-saturn_solar.jpg_220x220.jpg_.webp");
        goodsGson6.setPrice("¥99.00");
        goodsGson6.setGoodsName("欧洲站2018冬纯棉厚女毛衣圣诞节搭配红色套头气质可爱减龄黑色");
        goodsGsons.add(goodsGson6);
        ryPurse.setNestedScrollingEnabled(false);
        ryPurse.setFocusable(false);
        purseGoodsAdapter = new PurseGoodsAdapter(goodsGsons, getContext());
        ryPurse.setAdapter(purseGoodsAdapter);

        ViewTreeObserver viewTreeObserver = banner.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = banner.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                slHome.setOnObservableScrollViewListener(new ObservableScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
                        Log.i(TAG, "onObservableScrollViewListener: " + t);
                        if (t <= 0) {
                            //顶部图处于最顶部，标题栏透明
                            llTitle.setBackgroundColor(Color.argb(0, 255, 255, 255));
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_kind).into(ivKind);
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_shopcar).into(ivShopCar);
                            tvSearch.setBackgroundResource(R.drawable.home_search_bg);
                            llTitle.setVisibility(View.VISIBLE);
                        } else if (t > 0 && t < mHeight) {
                            //滑动过程中，渐变
                            llTitle.setVisibility(View.VISIBLE);
                            float scale = (float) t / mHeight;//算出滑动距离比例
                            float alpha = (255 * scale);//得到透明度
                            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_kind_red).into(ivKind);
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_shopcar_red).into(ivShopCar);
                        } else {
                            //过顶部图区域，标题栏定色
                            llTitle.setVisibility(View.VISIBLE);
                            llTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
                            tvSearch.setBackgroundResource(R.drawable.home_search_bg_dark);
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_kind_red).into(ivKind);
                            Glide.with(getContext()).asBitmap().load(R.mipmap.home_shopcar_red).into(ivShopCar);
                        }

                    }
                });
            }
        });
        srHome.autoRefresh();
        srHome.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                Log.i(TAG, "onHeaderPulling: " + headerHeight + "----------" + extendHeight);
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
                if (!newState.opening || newState.dragging || oldState.dragging || oldState.opening) {
                    llTitle.setVisibility(View.VISIBLE);
                } else {
                    llTitle.setVisibility(View.GONE);
                }
            }
        });
        srHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srHome.finishRefresh();
                    }
                }, 500);
            }
        });
        final List<ComplexItemEntity> datas = new ArrayList<>();
        datas.add(new ComplexItemEntity("我的的的武大大爱的啊大大",""));
        datas.add(new ComplexItemEntity("我发达爱的撒旦啊的的武大大爱的啊大大",""));
        datas.add(new ComplexItemEntity("阿大声道阿达大爱的",""));
        datas.add(new ComplexItemEntity("阿达达到阿达的阿达阿达阿打算",""));
        datas.add(new ComplexItemEntity("打撒旦打发顺丰阿达阿达阿达啊",""));
        marqueeView = view.findViewById(R.id.marqueeView);
        MarqueeFactory<LinearLayout, ComplexItemEntity> marqueeFactory = new ComplexViewMF(getContext());
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }

    private MarqueeView<LinearLayout, ComplexItemEntity> marqueeView;

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresent initPresenter() {
        return null;
    }

}
