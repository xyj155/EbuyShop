package com.example.home.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.GoodsShareGson;
import com.example.commonlib.view.HidingScrollListener;
import com.example.home.adapter.GoodsShareAdapter;
import com.example.home.contract.GoodsShareContract;
import com.example.home.presenter.GoodsSharePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsOrderShareActivity extends BaseActivity<GoodsShareContract.View, GoodsSharePresenter> implements GoodsShareContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_share)
    RecyclerView ryShare;
    @BindView(R2.id.rsl_share)
    SmartRefreshLayout rslShare;
    @BindView(R2.id.ll_sorted)
    LinearLayout llSorted;
    @BindView(R2.id.rb_sorted_all)
    RadioButton rbSortedAll;
    @BindView(R2.id.rb_sorted_picture)
    RadioButton rbSortedPicture;
    @BindView(R2.id.rb_sorted_video)
    RadioButton rbSortedVideo;
    @BindView(R2.id.rb_sorted_goods)
    RadioButton rbSortedGoods;
    @BindView(R2.id.rb_sorted_bad)
    RadioButton rbSortedBad;
    private GoodsShareAdapter goodsShareAdapter;
    private int type = 5;
    private int page = 1;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsSharePresenter getPresenter() {
        return new GoodsSharePresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_order_share;
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("晒单");
        rslShare.autoRefresh();
        rbSortedAll.setChecked(true);
        rslShare.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llSorted, View.TRANSLATION_Y, 0, -llSorted.getHeight());
                objectAnimator.setDuration(500);
                objectAnimator.start();
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llSorted, View.TRANSLATION_Y, -llSorted.getHeight(), 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
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
                page++;
                mPresenter.getMoreUserShareCommentList(String.valueOf(type), String.valueOf(page));
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                mPresenter.getUserShareCommentList(String.valueOf(type), "1");
            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

            }
        });
//        rslShare.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//
//            }
//        });
//        rslShare.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//
//            }
//        });
        ryShare.setLayoutManager(new LinearLayoutManager(GoodsOrderShareActivity.this));
        ryShare.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llSorted, View.TRANSLATION_Y, 0, -llSorted.getHeight());
                objectAnimator.setDuration(500);
                objectAnimator.start();
            }

            @Override
            public void onShow() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llSorted, View.TRANSLATION_Y, -llSorted.getHeight(), 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick(R2.id.iv_close)
    public void onViewClicked() {
        finish();
    }

    List<GoodsShareGson> goodsShareGsons = new ArrayList<>();

    @Override
    public void loadUserShareCommentList(List<GoodsShareGson> goodsShareGsonList) {
        rslShare.finishRefresh();
        rslShare.finishLoadMore();
        goodsShareGsons.addAll(goodsShareGsonList);
        goodsShareAdapter = new GoodsShareAdapter(null, GoodsOrderShareActivity.this);
        goodsShareAdapter.addData(goodsShareGsons);
        ryShare.setAdapter(goodsShareAdapter);
    }

    @Override
    public void loadMoreShareCommentList(List<GoodsShareGson> goodsShareGsonList) {
        rslShare.finishRefresh();
        rslShare.finishLoadMore();
        goodsShareGsons.addAll(goodsShareGsonList);
        goodsShareAdapter.replaceData(goodsShareGsons);
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
        mhideDialog();
        rslShare.finishRefresh();
        rslShare.finishLoadMore();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.rb_sorted_all, R2.id.rb_sorted_picture, R2.id.rb_sorted_video, R2.id.rb_sorted_goods, R2.id.rb_sorted_bad})
    public void onViewClicked(View view) {
        int i = view.getId();
        page = 1;
        goodsShareGsons.clear();
        if (i == R.id.rb_sorted_all) {
            type = 5;
            Log.i(TAG, "onLoadMore: " + page + "type==" + type);
            mPresenter.getUserShareCommentList("5", String.valueOf(page));
        } else if (i == R.id.rb_sorted_picture) {
            type = 1;
            Log.i(TAG, "onLoadMore: " + page + "type==" + type);
            mPresenter.getUserShareCommentList("1", String.valueOf(page));
        } else if (i == R.id.rb_sorted_video) {
            type = 2;
            Log.i(TAG, "onLoadMore: " + page + "type==" + type);
            mPresenter.getUserShareCommentList("2", String.valueOf(page));
        } else if (i == R.id.rb_sorted_goods) {
            type = 3;
            Log.i(TAG, "onLoadMore: " + page + "type==" + type);
            mPresenter.getUserShareCommentList("3", String.valueOf(page));
        } else if (i == R.id.rb_sorted_bad) {
            type = 4;
            Log.i(TAG, "onLoadMore: " + page + "type==" + type);
            mPresenter.getUserShareCommentList("4", String.valueOf(page));
        }
        if (goodsShareAdapter != null) {
            goodsShareAdapter.notifyDataSetChanged();
        }
    }
}
