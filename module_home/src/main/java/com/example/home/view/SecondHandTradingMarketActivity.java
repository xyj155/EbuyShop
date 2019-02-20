package com.example.home.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.SecondHandsGoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.GlideUtil;
import com.example.commonlib.util.SharePreferenceUtil;
import com.example.commonlib.view.HidingScrollListener;
import com.example.commonlib.view.MyDialog;
import com.example.home.contract.SecondHandTradingMarketContract;
import com.example.home.presenter.SecondHandTradingMarketPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondHandTradingMarketActivity extends BaseActivity<SecondHandTradingMarketContract.View, SecondHandTradingMarketPresenter> implements SecondHandTradingMarketContract.View {

    @BindView(R2.id.fb_submit)
    FloatingActionButton fbSubmit;
    @BindView(R2.id.ry_second)
    RecyclerView rySecond;
    @BindView(R2.id.sml_second)
    SmartRefreshLayout smlSecond;
    private List<SecondHandsGoodsGson> secondHandsGoodsGsonList = new ArrayList<>();
    private SecondHandAdapter secondHandAdapter;
    private int page = 1;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public SecondHandTradingMarketPresenter getPresenter() {
        return new SecondHandTradingMarketPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_second_handtrading_market;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("闲置二手");
        rySecond.setLayoutManager(new LinearLayoutManager(SecondHandTradingMarketActivity.this));
        smlSecond.autoRefresh();

        smlSecond.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                Log.i(TAG, "onLoadMore: " + page);
                mPresenter.queryAllSecondGoods(String.valueOf(10), String.valueOf(page));
            }
        });
        smlSecond.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                Log.i(TAG, "onLoadMore: " + page);
                mPresenter.loadMoreData(String.valueOf(10), String.valueOf(page));
            }
        });
        rySecond.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                Resources resources = SecondHandTradingMarketActivity.this.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                int height = dm.heightPixels;
                fbSubmit.animate()
                        .translationY(height - fbSubmit.getHeight())
                        .setInterpolator(new AccelerateInterpolator(2))
                        .start();
            }

            @Override
            public void onShow() {
                fbSubmit.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });

    }


    @Override
    public void initData() {

    }

    @OnClick(R2.id.fb_submit)
    public void onViewClicked() {
        int  user = Integer.valueOf(String.valueOf(SharePreferenceUtil.getUser("member", "String")));
        if (user>=3){
            startActivity(new Intent(SecondHandTradingMarketActivity.this, AddSecondHandTradingActivity.class));
        }else {
            showMsgDialog("会员权限功能", "你的会员等级不够哦!是否充值", new OnItemClickListener() {
                @Override
                public void onConfirm(MyDialog dialog) {
                    startActivity(MembershipOpeningActivity.class);
                }
            });
//            ToastUtils.show("你的会员等级不够哦");
        }

    }

    @Override
    public void uploadSuccess(boolean success) {

    }

    @Override
    public void queryAllSecondGoods(List<SecondHandsGoodsGson> secondHandsGoodsGsons) {
        secondHandsGoodsGsonList.clear();
        secondHandsGoodsGsonList.addAll(secondHandsGoodsGsons);
        secondHandAdapter = new SecondHandAdapter(secondHandsGoodsGsonList);
        rySecond.setAdapter(secondHandAdapter);
        Log.i(TAG, "onLoadMore: 23232323" + page);
    }

    @Override
    public void loadMoreData(List<SecondHandsGoodsGson> secondHandsGoodsGsons) {
        secondHandsGoodsGsonList.addAll(secondHandsGoodsGsons);
        Log.i(TAG, "onLoadMore: 54353636364" + page);
        secondHandAdapter.replaceData(secondHandsGoodsGsonList);
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
        smlSecond.finishRefresh();
        smlSecond.finishLoadMore();
        mhideDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class SecondHandAdapter extends BaseQuickAdapter<SecondHandsGoodsGson, BaseViewHolder> {


        public SecondHandAdapter(@Nullable List<SecondHandsGoodsGson> data) {
            super(R.layout.ry_second_hand_item_layout, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, SecondHandsGoodsGson item) {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);
            Log.i(TAG, "convert: " + item.getGoodsPrice());
            helper.setText(R.id.tv_title, item.getGoodsName())
                    .setText(R.id.tv_price, item.getGoodsPrice())
                    .setText(R.id.tv_content, item.getGoodsReason());
            if (item.getGoodsPostMoney().equals("")) {
                helper.setVisible(R.id.tv_post_free, false);
            } else {
                helper.setVisible(R.id.tv_post_free, true);
            }

            Log.i(TAG, "convert: " + RetrofitUtils.BASE_URL + item.getGoodsPic().get(0));
            GlideUtil.loadRoundCornerAvatarImage(RetrofitUtils.BASE_URL + item.getGoodsPic().get(0), (ImageView) helper.getView(R.id.iv_goods_pic), 9);
        }
    }
}
