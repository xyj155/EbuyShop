package com.example.home.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.commonlib.MyApp;
import com.example.commonlib.annotation.UserType;
import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.commonactivity.BrowserActivity;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HomeIconGson;
import com.example.commonlib.gson.HomePurseAdvertisementGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.TimeGoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.loader.BannerViewHolder;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.ObservableScrollView;
import com.example.home.adapter.HomeGoodsTimerPurseAdapter;
import com.example.home.adapter.HomeHotGoodsActivityAdapter;
import com.example.home.adapter.PurseGoodsAdapter;
import com.example.home.contract.HomePageContract;
import com.example.home.presenter.HomePagePresenter;
import com.example.home.util.ComplexViewMF;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.xuyijie.home.R;
import com.xuyijie.home.R2;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.MainActivity;

import static android.app.Activity.RESULT_OK;
import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

@Route(path = RouterUtil.Home_Fragment_Main)
public class HomeFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View {


    private static final int REQUEST_CODE = 0xff;
    @BindView(R2.id.iv_news)
    ImageView ivNews;
    @BindView(R2.id.tv_member)
    ImageView tvMember;
    @BindView(R2.id.iv_school_vip)
    ImageView ivSchoolVip;
    @BindView(R2.id.iv_best)
    ImageView ivBest;
    @BindView(R2.id.iv_share)
    ImageView ivShare;

    Unbinder unbinder;
    TextView tvTime;
    @BindView(R2.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R2.id.tvSearch)
    TextView tvSearch;
    @BindView(R2.id.iv_location)
    ImageView ivLocation;
    ImageView ivBanner;

    LinearLayout rootview;
    @BindView(R2.id.iv_scan)
    ImageView ivScan;
    @BindView(R2.id.iv_message)
    ImageView ivMessage;
    private MZBannerView<BannerGson> banner;
    private PurseGoodsAdapter purseGoodsAdapter;
    private RecyclerView ryPurse, ryTimerPurse, ryHot;

    private ObservableScrollView slHome;
    private int mHeight;
    private LinearLayout llTitle;
    @BindView(R2.id.ivKind)
    TextView tvLocation;
    @BindView(R2.id.ll_flash_time)
    LinearLayout llFlashTime;
    private ImageView ivShopCar;
    private SmartRefreshLayout srHome;
    private HomeGoodsTimerPurseAdapter homeGoodsTimerPurseAdapter;
    private HomeHotGoodsActivityAdapter homeHotGoodsItemAdapter;
    private List<BannerGson> bannerGsons = new ArrayList<>();
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
//初始化AMapLocationClientOption对象

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    tvLocation.setText(amapLocation.getCity());
//可在其中解析amapLocation获取相应内容。
                    Log.i(TAG, "onLocationChanged: " + amapLocation.getCity());//城市信息);
                } else {
                    tvLocation.setText("定位失败");
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    public void initData() {
//初始化定位
        mLocationClient = new AMapLocationClient(MyApp.getInstance());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(final View view) {
        srHome = view.findViewById(R.id.srHome);
        ryHot = view.findViewById(R.id.ryHot);
        ivBanner = view.findViewById(R.id.ivBanner);
        tvTime = view.findViewById(R.id.tv_time);
        ryHot.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rootview = view.findViewById(R.id.rootview);
        ryTimerPurse = view.findViewById(R.id.ry_timerPurse);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryTimerPurse.setLayoutManager(linearLayoutManager);

        banner = view.findViewById(R.id.banner);

        ryPurse = view.findViewById(R.id.ryPurse);
        slHome = view.findViewById(R.id.slHome);
        llTitle = view.findViewById(R.id.llTitle);
        tvLocation = view.findViewById(R.id.ivKind);
        ivShopCar = view.findViewById(R.id.ivShopCar);
        marqueeView = view.findViewById(R.id.marqueeView);
        homeHotGoodsItemAdapter = new HomeHotGoodsActivityAdapter(null, getContext());
        ryHot.setAdapter(homeHotGoodsItemAdapter);
        initData();//初始化数据
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ryPurse.setLayoutManager(staggeredGridLayoutManager);
        ryPurse.setItemAnimator(new DefaultItemAnimator());
        ryPurse.setNestedScrollingEnabled(false);
        ryPurse.setFocusable(false);

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
                            llTitle.setBackgroundColor(Color.argb(0, 255, 255, 255));
                            tvLocation.setTextColor(Color.argb(255, 255, 255, 255));
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_scan_white, ivScan);
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_message_white, ivMessage);
                            tvSearch.setBackgroundResource(R.drawable.home_search_bg);
                            llTitle.setVisibility(View.VISIBLE);
                        } else if (t < mHeight) {
                            //滑动过程中，渐变
                            llTitle.setVisibility(View.VISIBLE);
                            float scale = (float) t / mHeight;//算出滑动距离比例
                            float alpha = (255 * scale);//得到透明度
                            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                            tvLocation.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_scan_black, ivScan);
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_message_black, ivMessage);
                        } else {
                            llTitle.setVisibility(View.VISIBLE);
                            llTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
                            tvLocation.setTextColor(Color.argb(255, 0, 0, 0));
                            tvSearch.setBackgroundResource(R.drawable.home_search_bg_dark);
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_scan_black, ivScan);
                            GlideUtil.loadGeneralImage(R.mipmap.ic_home_message_black, ivMessage);
                        }

                    }
                });
            }
        });
        ryPurse.setHasFixedSize(false);
        srHome.autoRefresh();
        srHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Log.i(TAG, "onRefresh: 13123");
                isFirst = true;
                page = 1;
                systemMessageGsonArrayList.clear();
                mPresenter.setPurseGoodsList("1", "0");
                mPresenter.queryPurseGoods(String.valueOf(page));
                initSkeletonView(view);
                Log.i(TAG, "onRefresh: " + page);
            }
        });
        srHome.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                Log.i(TAG, "onLoadMore: 888");
                page++;
                mPresenter.queryPurseGoods(String.valueOf(page));
                Log.i(TAG, "onLoadMore: " + page);
                isFirst = false;
            }
        });
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
                skeletonScreen.hide();
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


            }
        });

        ryHot.setNestedScrollingEnabled(false);
        ryTimerPurse.setNestedScrollingEnabled(false);
        ryPurse.setNestedScrollingEnabled(false);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                if (bannerGsons.size() != 0) {
                    Intent intent = new Intent(getContext(), BrowserActivity.class);
                    intent.putExtra("url", bannerGsons.get(position).getWeb_url());
                    startActivity(intent);
                }
            }
        });
        mPresenter.queryTimeSell();
    }

    private int page = 1;
    private SkeletonScreen skeletonScreen;
    private boolean isFirst = true;

    private void initSkeletonView(View view) {
        skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.layout_img_skeleton)
                .duration(1000)
                .angle(0)
                .color(R.color.shemberl)
                .show();
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
        marqueeView.startFlipping();
    }


    private MarqueeView<LinearLayout, MarQueenGson> marqueeView;

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
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
        homeGoodsTimerPurseAdapter.replaceData(userGson);
//        homeGoodsTimerPurseAdapter.replaceData(userGson);
        Log.i(TAG, "loadTimeGoodsList: " + userGson.size());

    }

    private List<GoodsGson> systemMessageGsonArrayList = new ArrayList<>();

    @Override
    public void loadPurseGoodsList(List<GoodsGson> userGson) {

        if (isFirst) {
            Log.i(TAG, "querySystemPushMessage: 33333");
            systemMessageGsonArrayList.addAll(userGson);
            purseGoodsAdapter = new PurseGoodsAdapter(systemMessageGsonArrayList, getContext());
//            purseGoodsAdapter.addData(systemMessageGsonArrayList);
            ryPurse.setAdapter(purseGoodsAdapter);

        } else {
            systemMessageGsonArrayList.addAll(userGson);
            if (purseGoodsAdapter != null) {
//                purseGoodsAdapter.replaceData(systemMessageGsonArrayList);
                purseGoodsAdapter.notifyItemInserted(purseGoodsAdapter.getItemCount());
            }


            Log.i(TAG, "queryMoreSystemPushMessage: 22222");
        }
        Log.i(TAG, "loadTimeGoodsList: " + userGson.size());
        if (srHome != null) {
            srHome.finishRefresh();
            srHome.finishLoadMore();
        }
    }

    @Override
    public void loadHomeActivity(List<HotPurseActivityGson> userGson) {
        homeHotGoodsItemAdapter.replaceData(userGson);
        srHome.finishRefresh();
    }

    @Override
    public void loadHomeBanner(final List<BannerGson> userGson) {
        bannerGsons.addAll(userGson);
        banner.setPages(userGson, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.setDuration(200);
        banner.setIndicatorVisible(true);

        banner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected: ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "onPageScrollStateChanged: ");
            }
        });
        banner.start();

    }

    @Override
    public void loadMarqueenList(List<MarQueenGson> list) {
        MarqueeFactory<LinearLayout, MarQueenGson> marqueeFactory = new ComplexViewMF(getContext());
        marqueeFactory.setData(list);
        marqueeView.setOnItemClickListener(new OnItemClickListener<LinearLayout, MarQueenGson>() {
            @Override
            public void onItemClickListener(LinearLayout mView, MarQueenGson mData, int mPosition) {
                if (mData.getGoodId() != null) {
                    Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                    intent.putExtra("goodsId", mData.getGoodId());
                    startActivity(intent);
                } else if (mData.getPackName() != null) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.xuyijie.ebuyshop", mData.getPackName()));
                    startActivity(intent);
                } else if (mData.getUrl() != null) {
                    Intent intent = new Intent(getContext(), BrowserActivity.class);
                    intent.putExtra("url", mData.getUrl());
                    startActivity(intent);
                }

            }
        });
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }

    private long mDay = 10;
    private long mHour = 10;
    private long mMin = 30;
    private long mSecond = 0;
    private boolean isRun = true;

    @Override
    public void loadTimer(TimeGoodsGson.TimeBean timeBean) {
        Log.i(TAG, "loadTimer: " + timeBean);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = df.parse(timeBean.getEndTime());
            Date d2 = df.parse(timeBean.getCurrentTime());
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long second = (diff % (1000 * 60)) / 1000;
            mDay = days;
            mHour = hours;
            mMin = minutes;
            mSecond = second;
            startRun();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadHomePageIcon(List<HomeIconGson> timeBean) {
        Log.i(TAG, "loadHomePageIcon: " + timeBean.get(0).getIconUrl());
        boolean b = !timeBean.isEmpty();
        if (timeBean.size() > 0 || b) {
            GlideUtil.loadGeneralImage(timeBean.get(0).getIconUrl(), ivNews);
            GlideUtil.loadGeneralImage(timeBean.get(1).getIconUrl(), tvMember);
            GlideUtil.loadGeneralImage(timeBean.get(2).getIconUrl(), ivSchoolVip);
            GlideUtil.loadGeneralImage(timeBean.get(3).getIconUrl(), ivShare);
            GlideUtil.loadGeneralImage(timeBean.get(4).getIconUrl(), ivBest);
        }
    }


    @Override
    public void queryHomePurseGoodsAdvertisement(final HomePurseAdvertisementGson homePurseAdvertisement) {
//        Glide.with(getActivity()).asBitmap().load("https://img.zcool.cn/community/01f2f95a0c2334a801204a0eac24d2.png@1280w_1l_2o_100sh.png").into(ivBanner);
        GlideUtil.loadGeneralImage(homePurseAdvertisement.getGoodsPicture(), ivBanner);
        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("url", RetrofitUtils.BASE_URL + homePurseAdvertisement.getWebUrl());
                startActivity(intent);
            }
        });
        tvActivityName.setText(homePurseAdvertisement.getTitle());

    }

    @SuppressLint("HandlerLeak")
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                Log.i(TAG, "handleMessage: " + " " + (mHour < 10 ? "0" + mHour : mHour) + "   " + (mMin < 10 ? "0" + mMin : mMin) + "   " + mSecond);
                if (tvTime != null)
                    tvTime.setText(" " + (mHour < 10 ? "0" + mHour : mHour) + "   " + (mMin < 10 ? "0" + mMin : mMin) + "   " + mSecond);
            }
        }
    };


    /**
     * 开启倒计时
     */
    Runnable target = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (isRun) {
                try {
                    Thread.sleep(1000); // sleep 1000ms
                    Message message = Message.obtain();
                    message.what = 1;
                    timeHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void startRun() {
        new Thread(target).start();
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                }
            }
        }
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

    @OnClick({R2.id.iv_scan,R2.id.iv_message, R2.id.ivBanner, R2.id.tv_member, R2.id.tvSearch, R2.id.iv_news, R2.id.ll_flash_time, R2.id.iv_school_vip, R2.id.iv_best, R2.id.iv_share, R2.id.ivKind})
    public void onViewClicked(View view) {
        int id = view.getId();
        Log.i(TAG, "onViewClicked: ");
        if (id == R.id.iv_news) {
            Log.i(TAG, "onViewClicked: ");
            startActivity(new Intent(getContext(), NewUpperShelfActivity.class));
        } else if (id == R.id.iv_school_vip) {
            startActivity(new Intent(getContext(), SnacksActivity.class));
        } else if (id == R.id.iv_best) {
            startActivity(new Intent(getContext(), SecondHandTradingMarketActivity.class));
        } else if (id == R.id.iv_share) {
            loginWraper(UserType.ISPERMITED, GoodsOrderShareActivity.class);
        } else if (id == R.id.ivKind) {
            startActivity(new Intent(getContext(), GoodsKindSortedActivity.class));
        } else if (id == R.id.ll_flash_time) {
            startActivity(new Intent(getContext(), TimeFlashSaleActivity.class));
        } else if (id == R.id.tvSearch) {
            startActivity(new Intent(getContext(), SearchGoodsActivity.class));
            ((Activity) mContext).overridePendingTransition(R.anim.bottom_to_top_anim_in,
                    R.anim.bottom_to_top_anim_out);
        } else if (id == R.id.tv_member) {
            String user = (String) SharePreferenceUtil.getUser("member", "String");
            Log.i(TAG, "onViewClicked: " + user);
            if (user.equals("0")) {
                startActivity(new Intent(getContext(), MembershipOpeningActivity.class));
            } else {
                startActivity(new Intent(getContext(), MemberShipRightActivity.class));
            }

        } else if (R.id.iv_message == id) {
            ARouter.getInstance().build(RouterUtil.MESSAGE_Fragment_Main).navigation();
        }else if (R.id.iv_scan==id){
//            Intent intent = new Intent(getContext(), CaptureActivity.class);
//            startActivityForResult(intent, REQUEST_CODE);
            Intent intent = new Intent(getContext(), CaptureActivity.class);
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getContext(), "解析结果:" + content, Toast.LENGTH_LONG).show();
            }
        }
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }
}
