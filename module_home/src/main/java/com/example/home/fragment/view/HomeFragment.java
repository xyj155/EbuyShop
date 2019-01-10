package com.example.home.fragment.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.annotation.ContentView;
import com.example.commonlib.annotation.UserAnnotation;
import com.example.commonlib.annotation.UserType;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.loader.BannerViewHolder;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.UserBindFactory;
import com.example.commonlib.view.ObservableScrollView;
import com.example.home.fragment.PurseGoodsAdapter;
import com.example.home.fragment.adapter.HomeGoodsTimerPurseAdapter;
import com.example.home.fragment.adapter.HomeHotGoodsActivityAdapter;
import com.example.home.fragment.contract.HomePageContract;
import com.example.home.fragment.entity.ComplexItemEntity;
import com.example.home.fragment.presenter.HomePagePresenter;
import com.example.home.fragment.util.ComplexViewMF;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

@Route(path = RouterUtil.Home_Fragment_Main)
public class HomeFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View {


    @BindView(R2.id.iv_news)
    TextView ivNews;
    @BindView(R2.id.tv_goods_news)
    TextView tvGoodsNews;
    @BindView(R2.id.iv_school_vip)
    TextView ivSchoolVip;
    @BindView(R2.id.iv_best)
    TextView ivBest;
    @BindView(R2.id.iv_share)
    TextView ivShare;
    Unbinder unbinder;
    private MZBannerView<String> banner;
    private PurseGoodsAdapter purseGoodsAdapter;
    private RecyclerView ryPurse, ryTimerPurse, ryHot;

    private ObservableScrollView slHome;
    private int mHeight;
    private LinearLayout llTitle;
    private TextView tvSearch;

    private ImageView ivKind, ivShopCar, ivBanner;
    private SmartRefreshLayout srHome;
    private HomeGoodsTimerPurseAdapter homeGoodsTimerPurseAdapter;
    private HomeHotGoodsActivityAdapter homeHotGoodsItemAdapter;

    @Override
    public void initData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {
        srHome = view.findViewById(R.id.srHome);
        ryHot = view.findViewById(R.id.ryHot);
        ivBanner = view.findViewById(R.id.ivBanner);
        Glide.with(getActivity()).asBitmap().load("https://img.zcool.cn/community/01f2f95a0c2334a801204a0eac24d2.png@1280w_1l_2o_100sh.png").into(ivBanner);
        ryHot.setLayoutManager(new GridLayoutManager(getContext(), 4));

        ryTimerPurse = view.findViewById(R.id.ry_timerPurse);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryTimerPurse.setLayoutManager(linearLayoutManager);

        banner = view.findViewById(R.id.banner);
        ryPurse = view.findViewById(R.id.ryPurse);
        slHome = view.findViewById(R.id.slHome);
        llTitle = view.findViewById(R.id.llTitle);
        tvSearch = view.findViewById(R.id.tvSearch);
        ivKind = view.findViewById(R.id.ivKind);
        ivShopCar = view.findViewById(R.id.ivShopCar);
        marqueeView = view.findViewById(R.id.marqueeView);
        homeHotGoodsItemAdapter = new HomeHotGoodsActivityAdapter(null, getContext());
        ryHot.setAdapter(homeHotGoodsItemAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ryPurse.setLayoutManager(staggeredGridLayoutManager);
        initData();//初始化数据
        ryPurse.setItemAnimator(new DefaultItemAnimator());
        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545032090&di=659174bccea53c9461eed981cecfc15e&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01274c5841176fa801219c77c4ecd6.png%402o.png");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369676&di=12100c24b51fa3ee49f97394d4739de9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016ad958eddb0ba8012049ef9f8413.jpg%401280w_1l_2o_100sh.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369676&di=dc08ba14ee8a3fed755f36bde08647ea&imgtype=0&src=http%3A%2F%2Fpic101.nipic.com%2Ffile%2F20160621%2F23040058_143914199524_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369673&di=d529c1a0f188d5e097977c8678d7ccbb&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c9db56c5706832f875520f596bba.png%401280w_1l_2o_100sh.png");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544437369673&di=2d3458515c5badccdb40accde18f5b77&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01538558abd2a0a801219c773a1c5d.jpg%401280w_1l_2o_100sh.jpg");
        banner.setPages(images, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.setDuration(200);
        banner.setIndicatorVisible(true);
        banner.start();
        ryPurse.setNestedScrollingEnabled(false);
        ryPurse.setFocusable(false);
        purseGoodsAdapter = new PurseGoodsAdapter(null, getContext());
        ryPurse.setAdapter(purseGoodsAdapter);
        homeGoodsTimerPurseAdapter = new HomeGoodsTimerPurseAdapter(null, getContext());
        ryTimerPurse.setAdapter(homeGoodsTimerPurseAdapter);
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
        mPresenter.setPurseGoodsList("1", "0");
//        mPresenter.setTimerGoodsList("0");
        srHome.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                Log.i(TAG, "onHeaderPulling: " + headerHeight + "----------" + extendHeight + "---------" + percent);
                llTitle.setVisibility(View.GONE);

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {
                llTitle.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                llTitle.setVisibility(View.VISIBLE);
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
//                if (oldState.opening) {
//                    llTitle.setVisibility(View.VISIBLE);
//                } else {
//                    llTitle.setVisibility(View.GONE);
//                }
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
        datas.add(new ComplexItemEntity("我的的的武大大爱的啊大大", ""));
        datas.add(new ComplexItemEntity("我发达爱的撒旦啊的的武大大爱的啊大大", ""));
        datas.add(new ComplexItemEntity("阿大声道阿达大爱的", ""));
        datas.add(new ComplexItemEntity("阿达达到阿达的阿达阿达阿打算", ""));
        datas.add(new ComplexItemEntity("打撒旦打发顺丰阿达阿达阿达啊", ""));

        MarqueeFactory<LinearLayout, ComplexItemEntity> marqueeFactory = new ComplexViewMF(getContext());
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

        ryHot.setNestedScrollingEnabled(false);
        ryTimerPurse.setNestedScrollingEnabled(false);
        ryPurse.setNestedScrollingEnabled(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();//开始轮播
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
    public HomePagePresenter initPresenter() {
        return new HomePagePresenter(this);
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog(msg);
    }


    @Override
    public void hideDialog() {
        dialogCancel();
        Log.i(TAG, "hideDialog: ");
    }


    @Override
    public void loadTimeGoodsList(List<GoodsGson> userGson) {
        purseGoodsAdapter.replaceData(userGson);
        Log.i(TAG, "loadTimeGoodsList: " + userGson.size());
    }

    @Override
    public void loadPurseGoodsList(List<GoodsGson> userGson) {
        homeGoodsTimerPurseAdapter.replaceData(userGson);
        Log.i(TAG, "loadTimeGoodsList: " + userGson.size());
    }

    @Override
    public void loadHomeActivity(List<HotPurseActivityGson> userGson) {
        homeHotGoodsItemAdapter.replaceData(userGson);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R2.id.iv_news, R2.id.tv_goods_news, R2.id.iv_school_vip, R2.id.iv_best, R2.id.iv_share})
    public void onViewClicked(View view) {
        int id = view.getId();
        Log.i(TAG, "onViewClicked: ");
        if (id == R.id.iv_news) {
            Log.i(TAG, "onViewClicked: ");

        } else if (id == R.id.tv_goods_news) {

        } else if (id == R.id.iv_school_vip) {

        } else if (id == R.id.iv_best) {

        } else if (id == R.id.iv_share) {
            loginWraper(UserType.ISPERMITED, GoodsOrderShareActivity.class);
        }
    }
}
