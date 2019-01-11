package com.example.commonlib.commonactivity;

import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.GoodsSortedAdapter;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsSortedContract;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.presenter.GoodsSortedPresenter;
import com.example.commonlib.util.RouterUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterUtil.GOODSSORTED)
public class GoodsListSortedActivity extends BaseActivity<GoodsSortedContract.View, GoodsSortedPresenter> implements GoodsSortedContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.rb_hot)
    RadioButton rbHot;
    @BindView(R2.id.rb_news)
    RadioButton rbNews;
    @BindView(R2.id.rb_price)
    RadioButton rbPrice;
    @BindView(R2.id.iv_price_sorted)
    ImageView ivPriceSorted;
    @BindView(R2.id.rg_sorted)
    RadioGroup rgSorted;
    @BindView(R2.id.ry_sorted)
    RecyclerView rySorted;
    @BindView(R2.id.sl_sorted)
    SmartRefreshLayout slSorted;
    private GoodsSortedAdapter goodsSortedAdapter;
    private boolean isSetPrice = false;
    private boolean isSetNews = false;
    private boolean isSetHot = false;
    private String isSetDesc = "1";
    private String sortedType = "1";


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsSortedPresenter getPresenter() {
        return new GoodsSortedPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_list_sorted;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("商品列表");
        getWindow().setEnterTransition(new Explode().setDuration(400));
        getWindow().setExitTransition(new Explode().setDuration(400));
        rbHot.setChecked(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rySorted.setLayoutManager(staggeredGridLayoutManager);
        rySorted.setItemAnimator(new DefaultItemAnimator());
        mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
        Log.i(TAG, "initView: " + getIntent().getStringExtra("kind"));
        goodsSortedAdapter = new GoodsSortedAdapter(GoodsListSortedActivity.this, null);
        goodsSortedAdapter.setEmptyView(View.inflate(GoodsListSortedActivity.this, R.layout.common_empty, null));
        rySorted.setAdapter(goodsSortedAdapter);
        slSorted.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Log.i(TAG, "onRefresh: sortedType="+sortedType+"+++++++++isSetDesc="+isSetDesc);
                mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
            }
        });
    }


    @Override
    public void initData() {

        rbNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_unselected).into(ivPriceSorted);
                sortedType = "2";
                Log.i(TAG, "onRadioCheck: ");
                if (isSetHot) {
                    isSetHot = false;
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="1";
                } else {
                    isSetHot = true;
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="0";
                }
            }
        });
        rbHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onRadioCheck: ");
                Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_unselected).into(ivPriceSorted);
                sortedType = "1";
                if (isSetNews) {
                    isSetNews = false;
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="1";
                } else {
                    isSetNews = true;
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="0";
                }
            }
        });
        rbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedType = "3";
                if (isSetPrice) {
                    isSetPrice = false;
                    Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_ascend).into(ivPriceSorted);
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="0";
                } else {
                    isSetPrice = true;
                    Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_categroy_goods_price_descend).into(ivPriceSorted);
                    mPresenter.getGoodsListByKind(getIntent().getStringExtra("kind"), sortedType, isSetDesc);
                    isSetDesc ="1";
                }
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void setGoodsListByKind(List<GoodsGson> listByKind) {
        goodsSortedAdapter.replaceData(listByKind);
        slSorted.finishRefresh();
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
        hideDlalog();
        slSorted.finishRefresh();
    }
}
