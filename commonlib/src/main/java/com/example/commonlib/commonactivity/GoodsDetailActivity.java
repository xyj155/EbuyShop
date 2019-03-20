package com.example.commonlib.commonactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.contract.GoodsStyleContract;
import com.example.commonlib.gson.GoodsDetailGson;
import com.example.commonlib.gson.GoodsStyleGson;
import com.example.commonlib.gson.SubmitOrderGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.loader.GoodsBannerViewHolder;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.presenter.GoodsStylePresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.ShopChooseDialog;
import com.example.commonlib.view.SlideDetailsLayout;
import com.example.commonlib.view.WebViewMod;
import com.example.commonlib.view.previewphoto.PhotoPreviewActivity;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

@Route(path = RouterUtil.GOODSDETAIL)
public class GoodsDetailActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> implements GoodsDetailContract.View, SlideDetailsLayout.OnSlideDetailsListener, GoodsStyleContract.View {
    @BindView(R2.id.banner_goods)
    MZBannerView bannerGoods;
    @BindView(R2.id.tv_banner_size)
    TextView tvBannerSize;
    @BindView(R2.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R2.id.tv_new_price)
    TextView tvNewPrice;
    @BindView(R2.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R2.id.tv_current_goods)
    TextView tvCurrentGoods;
    @BindView(R2.id.ll_current_goods)
    LinearLayout llCurrentGoods;
    @BindView(R2.id.ry_ensure)
    RecyclerView ryEnsure;
    @BindView(R2.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R2.id.tv_good_comment)
    TextView tvGoodComment;
    @BindView(R2.id.iv_comment_right)
    ImageView ivCommentRight;
    @BindView(R2.id.iv_share)
    ImageView ivShare;
    @BindView(R2.id.ll_comment)
    LinearLayout llComment;
    @BindView(R2.id.ry_comment)
    RecyclerView ryComment;
    @BindView(R2.id.tv_commentpic_count)
    TextView tvCommentpicCount;
    @BindView(R2.id.iv_commentpic_right)
    ImageView ivCommentpicRight;
    @BindView(R2.id.ll_comment_pic)
    LinearLayout llCommentPic;
    @BindView(R2.id.ry_comment_pic)
    RecyclerView ryCommentPic;
    @BindView(R2.id.ry_recommend)
    RecyclerView ryRecommend;
    @BindView(R2.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R2.id.sv_goods_info)
    NestedScrollView svGoodsInfo;
    @BindView(R2.id.sv_switch)
    SlideDetailsLayout svSwitch;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.ll_title)
    FrameLayout llTitle;
    WebViewMod webView;
    @BindView(R2.id.tv_service)
    TextView tvService;
    @BindView(R2.id.cb_collection)
    CheckBox cbCollection;
    @BindView(R2.id.tv_shopcar)
    public TextView tvShopcar;
    @BindView(R2.id.tv_time)
    TextView tvTime;
    @BindView(R2.id.rl_timer)
    RelativeLayout rlTimer;
    @SuppressLint("HandlerLeak")
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                tvTime.setText((mHour < 10 ? "0" + mHour : mHour) + ":" + (mMin < 10 ? "0" + mMin : mMin) + ":" + mSecond);
                if (mDay == 0 && mHour == 0 && mMin == 0 && mSecond == 0) {

                }
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

    private long mDay = 10;
    private long mHour = 10;
    private long mMin = 30;
    private long mSecond = 00;
    private boolean isRun = true;

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

    private ServiceAdapter serviceAdapter = new ServiceAdapter(null);
    private GoodsCommentPicAdapter goodsCommentPicAdapter = new GoodsCommentPicAdapter(null);
    private GoodsCommentAdapter goodsCommentAdapter = new GoodsCommentAdapter(null);
    @BindView(R2.id.tv_add)
    TextView tvAdd;
    @BindView(R2.id.tv_buy)
    TextView tvBuy;
    @BindView(R2.id.tv_describe)
    TextView tvDescribe;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    private ShopChooseDialog shopChooseDialogBuy, shopChooseDialogChoose;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public GoodsDetailPresenter getPresenter() {
        return new GoodsDetailPresenter(this);
    }

    private int bannerHeight;

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    private boolean hasMeasured = false;

    @SuppressLint("NewApi")
    @Override
    public void initView() {
        ButterKnife.bind(this);
        webView = findViewById(R.id.webView);
        ViewTreeObserver viewTreeObserver = bannerGoods.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    bannerHeight = bannerGoods.getHeight();
                    hasMeasured = true;
                }
                return true;
            }
        });
        svSwitch.setOnSlideDetailsListener(this);
        mPresenter.setGoodsDetailById(getIntent().getStringExtra("goodsId"), String.valueOf(SharePreferenceUtil.getUser("uid", "String")));
        ryEnsure.setLayoutManager(new GridLayoutManager(GoodsDetailActivity.this, 3));
        ryEnsure.setNestedScrollingEnabled(false);
        ryEnsure.setAdapter(serviceAdapter);
        ryRecommend.setLayoutManager(new GridLayoutManager(GoodsDetailActivity.this, 3));
        ryRecommend.setNestedScrollingEnabled(false);
        ryRecommend.setAdapter(goodsDetailPurseAdapter);
        ryCommentPic.setLayoutManager(new GridLayoutManager(GoodsDetailActivity.this, 4));
        ryCommentPic.setNestedScrollingEnabled(false);
        ryCommentPic.setAdapter(goodsCommentPicAdapter);
        goodsCommentPicAdapter.setEmptyView(View.inflate(GoodsDetailActivity.this, R.layout.common_empty_comment_layout, null));
        ryComment.setLayoutManager(new LinearLayoutManager(GoodsDetailActivity.this));
        ryComment.setAdapter(goodsCommentAdapter);
        svGoodsInfo.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int l, int t, int oldl, int oldt) {
                Log.i(TAG, "onScrollChange: " + bannerHeight);
                if (t <= 0) {
                    tvTitle.setTextColor(0x00000000);
                    llTitle.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else if (t > 0 && t < bannerHeight) {
                    tvTitle.setVisibility(View.VISIBLE);
                    float scale = (float) t / bannerHeight;
                    float alpha = 255 * scale;
                    tvTitle.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                    llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else {
                    tvTitle.setTextColor(0xff000000);
                    llTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
                }
            }
        });
        boolean isTimer = getIntent().getBooleanExtra("isTimer", false);
        if (isTimer) {
            rlTimer.setVisibility(View.VISIBLE);
//            tvTime.setText(getIntent().getStringExtra("time").substring(0, 10) + "  00:00:00");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = null;
            try {
                String endTime = getIntent().getStringExtra("endTime");
                if (endTime != null) {
                    d1 = df.parse(endTime);

                    Date d2 = new Date(System.currentTimeMillis());
//                Date d2 = df.parse(getIntent().getStringExtra("time"));
                    Log.i(TAG, "initView:endTime " + getIntent().getStringExtra("endTime"));
                    Log.i(TAG, "initView:time " + df.format(d2));
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
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initData() {

    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {
        createDialog("");
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

    @Override
    public void onStatusChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            svGoodsInfo.setSmoothScrollingEnabled(false);
        } else {
            svGoodsInfo.setSmoothScrollingEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class ServiceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ServiceAdapter(@Nullable List<String> data) {
            super(R.layout.abc_goods_detail_service_tag, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_service_name, item);
        }
    }


    private GoodsDetailPurseAdapter goodsDetailPurseAdapter = new GoodsDetailPurseAdapter(null);

    private class GoodsDetailPurseAdapter extends BaseQuickAdapter<GoodsDetailGson.PurseGoodsListBean, BaseViewHolder> {

        public GoodsDetailPurseAdapter(@Nullable List<GoodsDetailGson.PurseGoodsListBean> data) {
            super(R.layout.abc_goods_detail_purse_goods_list_item_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsDetailGson.PurseGoodsListBean item) {
            helper.setText(R.id.tv_goods_name, item.getGoodsName())
                    .setText(R.id.tv_price, "￥" + item.getGoodsPrice())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(GoodsDetailActivity.this, GoodsDetailActivity.class);
                            view.putExtra("goodsId", String.valueOf(item.getId()));
                            startActivity(view);
                        }
                    });
            RoundedCorners roundedCorners = new RoundedCorners(6);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(GoodsDetailActivity.this).asBitmap().load(item.getGoodsPic()).apply(options).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }

    private String imToken;
    private String shopId;
    private String shopName;
    private String shopIcon;

    @Override
    public void loadGoodsDetail(final GoodsDetailGson goodsGson) {
        Log.i(TAG, "loadGoodsDetail: "+goodsGson);
        shopIcon = goodsGson.getShopIcon();
        tvShopcar.setText(goodsGson.getShopCount() + "");
        if (goodsGson.isCollection()) {
            cbCollection.setChecked(true);
        } else {
            cbCollection.setChecked(false);
        }
        if (goodsGson.getToken() != null) {
            imToken = goodsGson.getToken();
            shopId = goodsGson.getShopTel();
            Log.i(TAG, "loadGoodsDetail: " + goodsGson.getImToken());
            Log.i(TAG, "loadGoodsDetail: " + goodsGson.getToken());
            shopName = goodsGson.getShopName();
        }

        tvCount.setText("销量：" + goodsGson.getOrderNum());
        tvCommentCount.setText("( " + goodsGson.getComment() + " )");
        tvBannerSize.setText("1/" + goodsGson.getGoodsPicUrl().size());
        Log.i(TAG, "loadGoodsDetail: " + goodsGson.getGoodsPicUrl().size());
        tvDescribe.setText("商品描述：" + goodsGson.getGoodsDescribe());
        final List<String> goodsPicUrl = goodsGson.getGoodsPicUrl();
        bannerGoods.setPages(goodsPicUrl, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new GoodsBannerViewHolder();
            }
        });
        bannerGoods.setDuration(200);
        bannerGoods.setIndicatorVisible(true);
        bannerGoods.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvBannerSize.setText((position + 1) + "/" + goodsGson.getGoodsPicUrl().size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bannerGoods.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Log.i(TAG, "onPageClick: " + i + view);
                Intent intent = new Intent(GoodsDetailActivity.this, PhotoPreviewActivity.class);
                intent.putExtra(PhotoPreviewActivity.EXTRA_PHOTOS, (ArrayList) goodsPicUrl);
                startActivity(intent);
            }
        });
        bannerGoods.start();
        tvGoodsTitle.setText(goodsGson.getGoodsName());
        tvNewPrice.setText(goodsGson.getGoodsPrice());
        tvOldPrice.setText("原价：￥" + goodsGson.getOriginalPrice());
        serviceAdapter.replaceData(goodsGson.getGoodsService());
        goodsDetailPurseAdapter.replaceData(goodsGson.getPurseGoodsList());
        goodsCommentPicAdapter.replaceData(goodsGson.getGoodsCommentPic());
        tvCommentpicCount.setText(goodsGson.getGoodsCommentPic().size() + "");
        initWebView();

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnekeyShare oks = new OnekeyShare();
                oks.disableSSOWhenAuthorize();
                oks.setTitle(goodsGson.getGoodsName());
                oks.setText(goodsGson.getGoodsDescribe());
                oks.setImageUrl(goodsGson.getGoodsPic());
                oks.setSite(getString(R.string.app_name));
                oks.show(GoodsDetailActivity.this);
            }
        });
        tvCommentpicCount.setText("( " + goodsGson.getGoodsCommentPic().size() + " )");
        cbCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCollection.isChecked()) {
                    mPresenter.addUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(goodsGson.getId()), "0");
                } else {
                    mPresenter.addUserCollection(String.valueOf(SharePreferenceUtil.getUser("uid", "String")), String.valueOf(goodsGson.getId()), "1");
                }
            }
        });
    }

    private void initWebView() {
        Log.i(TAG, "loadGoodsDetail: " + RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/index/goodsDetail?id=" + getIntent().getStringExtra("goodsId"));


        WebSettings webSettings = webView.getSettings();

        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webSettings.setBuiltInZoomControls(false);
        webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
        webView.setVerticalScrollBarEnabled(true);//允许垂直滚动
        // 设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(RetrofitUtils.BASE_URL + "/StuShop/public/index.php/index/index/goodsDetail?id=" + getIntent().getStringExtra("goodsId"));
        webView.setFocusable(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished: " + url);
                if (view.getContentHeight() == 0) {
                    view.loadUrl(url);
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "onPageStarted: " + url);
            }
        });
    }

    @Override
    public void insertUserOrder(SubmitOrderGson goodsGson) {
        Gson gson = new Gson();
        Intent intent = new Intent(GoodsDetailActivity.this, GoodsPaymentActivity.class);
        intent.putExtra("orderNum", goodsGson.getOrderNum());
        HashSet<String> h = new HashSet<>(goodsIdList);
        goodsIdList.clear();
        goodsIdList.addAll(h);//去重
        intent.putExtra("goodsArray", gson.toJson(goodsIdList));
        startActivity(intent);
        goodsIdList.clear();
    }

    @Override
    public void addCollectionSuccess(boolean success) {

    }

    private class GoodsCommentPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GoodsCommentPicAdapter(@Nullable List<String> data) {
            super(R.layout.abc_goods_detail_comment_pic_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(GoodsDetailActivity.this).asBitmap().load(RetrofitUtils.BASE_URL + item).apply(options).into((ImageView) helper.getView(R.id.iv_pic));
        }
    }

    private class GoodsCommentAdapter extends BaseQuickAdapter<GoodsDetailGson.CommentBean, BaseViewHolder> {

        public GoodsCommentAdapter(@Nullable List<GoodsDetailGson.CommentBean> data) {
            super(R.layout.abc_goods_detail_comment_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsDetailGson.CommentBean item) {
            helper.setText(R.id.tv_username, item.getUsername())
                    .setText(R.id.tv_comment, item.getComment());
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            Glide.with(GoodsDetailActivity.this).asBitmap().load(item.getAvatar()).apply(options).into((ImageView) helper.getView(R.id.iv_user_avatar));
        }
    }

    private GoodsDetailPresenter submitOrderPresenter = new GoodsDetailPresenter(this);
    private boolean isBuy = false;

    @OnClick({R2.id.tv_service, R2.id.tv_shopcar, R2.id.tv_add, R2.id.tv_buy, R2.id.iv_close, R2.id.ll_comment})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_add) {
            GoodsStylePresenter goodsStylePresenter = new GoodsStylePresenter(this);
            goodsStylePresenter.queryGoodsStyle(getIntent().getStringExtra("goodsId"));
            isBuy = false;

        } else if (id == R.id.tv_buy) {
            GoodsStylePresenter goodsStylePresenter = new GoodsStylePresenter(this);
            goodsStylePresenter.queryGoodsStyle(getIntent().getStringExtra("goodsId"));
            isBuy = true;
        } else if (id == R.id.iv_close) {
            finish();
        } else if (id == R.id.ll_current_goods) {

        } else if (id == R.id.ll_comment) {
            Intent intent = new Intent(GoodsDetailActivity.this, GoodsCommentActivity.class);
            intent.putExtra("goodsId", getIntent().getStringExtra("goodsId"));
            startActivity(intent);
        } else if (id == R.id.tv_shopcar) {

        } else if (id == R.id.tv_service) {
            if (imToken != null) {
                if (!imToken.isEmpty()) {
                    Log.i(TAG, "onViewClicked: " + imToken);
                    Log.i(TAG, "onViewClicked: " + shopName);
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {
//                            return new UserInfo(shopId, shopName, Uri.parse(shopIcon));
                            if (s.equals(SharePreferenceUtil.getUser("username","String"))){
                                return new UserInfo(String.valueOf(SharePreferenceUtil.getUser("username","String")), (String) SharePreferenceUtil.getUser("username","String"), Uri.parse(String.valueOf(SharePreferenceUtil.getUser("avatar","String"))));
                            }else {
                                return new UserInfo(shopId, shopName, Uri.parse(shopIcon));
                            }
                        }
                    }, true);
                    RongIM.getInstance().startConversation(GoodsDetailActivity.this, Conversation.ConversationType.PRIVATE,shopId,shopName);
//                    RongIM.getInstance().setMessageAttachedUserInfo(true);

                }
            }

        }
    }

    @Override
    public void loadGoodsStyle(List<GoodsStyleGson> goodsGson) {
        if (isBuy) {
            shopChooseDialogChoose = new ShopChooseDialog(GoodsDetailActivity.this, getIntent().getStringExtra("goodsId"), true, goodsGson);
            shopChooseDialogChoose.show();
            shopChooseDialogChoose.setOnSubmitOrderListener(new ShopChooseDialog.onSubmitOrderListener() {
                @Override
                public void onSubmitListener(String goodsId, String count) {
                    goodsIdList.clear();
                    Gson gson = new Gson();
                    for (int i = 0; i < Integer.valueOf(count); i++) {
                        goodsIdList.add(goodsId);
                    }
                    submitOrderPresenter.insertUserOrder((String) SharePreferenceUtil.getUser("uid", "String"), gson.toJson(goodsIdList));
                }
            });
        } else {
            shopChooseDialogBuy = new ShopChooseDialog(GoodsDetailActivity.this, getIntent().getStringExtra("goodsId"), false, goodsGson);
            shopChooseDialogBuy.show();
            shopChooseDialogChoose.setOnSubmitOrderListener(new ShopChooseDialog.onSubmitOrderListener() {
                @Override
                public void onSubmitListener(String goodsId, String count) {
                    goodsIdList.clear();
                    Gson gson = new Gson();
                    for (int i = 0; i < Integer.valueOf(count); i++) {
                        goodsIdList.add(goodsId);
                    }
                    submitOrderPresenter.insertUserOrder((String) SharePreferenceUtil.getUser("uid", "String"), gson.toJson(goodsIdList));
                }
            });
        }

    }

    @Override
    public void addGoodsInShopCar(boolean isSuccess) {

    }

    private List<String> goodsIdList = new ArrayList<>();

}
