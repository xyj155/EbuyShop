package com.example.home.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
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
import com.example.home.entity.GoodsShareEntity;
import com.example.home.presenter.GoodsSharePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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

    private int page = 1;
    List<GoodsShareEntity> list = new ArrayList<>();

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("晒单");
        rslShare.autoRefresh();
        rbSortedAll.setChecked(true);
        final GoodsShareEntity goodsShareEntity = new GoodsShareEntity(GoodsShareEntity.TYPE_TWO);

        rslShare.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<GoodsShareEntity> data = goodsShareAdapter.getData();
                data.clear();
                goodsShareAdapter.notifyDataSetChanged();
                page = 1;
                list.add(goodsShareEntity);
                mPresenter.getUserShareCommentList("1", "1");
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getUserShareCommentList("1", String.valueOf(page));
                refreshLayout.finishLoadMore();
            }
        });
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
        goodsShareAdapter = new GoodsShareAdapter(null);
//        goodsShareAdapter.set(View.inflate(GoodsOrderShareActivity.this,R.layout.common_header,null));
        ryShare.setAdapter(goodsShareAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick(R2.id.iv_close)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void loadUserShareCommentList(List<GoodsShareGson> goodsShareGsonList) {
        rslShare.finishRefresh();
        rslShare.finishLoadMore();
        for (int i = 0; i < goodsShareGsonList.size(); i++) {
            GoodsShareEntity entity = new GoodsShareEntity(GoodsShareEntity.TYPE_ONE);
            entity.setSeriesEntity(goodsShareGsonList.get(i));
            list.add(entity);
        }
        goodsShareAdapter.addData(list);
        list.clear();
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

    @OnCheckedChanged({R2.id.rb_sorted_all, R2.id.rb_sorted_picture, R2.id.rb_sorted_video, R2.id.rb_sorted_goods, R2.id.rb_sorted_bad})
    public  void onCheckedChanged(CompoundButton buttonView, boolean isChecked)  {
//        switch (view.getId()) {
//            case R.id.rb_sorted_all:
//                break;
//            case R.id.rb_sorted_picture:
//                break;
//            case R.id.rb_sorted_video:
//                break;
//            case R.id.rb_sorted_goods:
//                break;
//            case R.id.rb_sorted_bad:
//                break;
//        }
    }
}
