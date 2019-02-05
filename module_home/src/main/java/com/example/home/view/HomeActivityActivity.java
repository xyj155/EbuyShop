package com.example.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.commonactivity.GoodsDetailActivity;
import com.example.commonlib.gson.GoodsGson;
import com.example.home.contract.HomeActivityContract;
import com.example.home.presenter.HomeActivityPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivityActivity extends BaseActivity<HomeActivityContract.View, HomeActivityPresenter> implements HomeActivityContract.View {


    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.ry_goods)
    RecyclerView ryGoods;
    @BindView(R2.id.cb_model)
    CheckBox cbModel;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public HomeActivityPresenter getPresenter() {
        return new HomeActivityPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_home_activity;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("goods"));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String name = getIntent().getStringExtra("name");
        if (name != null) {
            mPresenter.queryHomeActivityByName(name);
        }
        cbModel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setLvAdapter();
                } else {
                    setGvAdapter();

                }
            }
        });

        goodsGrideAdapter = new GoodsGrideAdapter(null, HomeActivityActivity.this);
        goodsListAdapter = new GoodsListAdapter(null, HomeActivityActivity.this);
        setGvAdapter();
    }

    private void setGvAdapter() {
        ryGoods.setLayoutManager(new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL));
        ryGoods.setAdapter(goodsGrideAdapter);
    }


    private void setLvAdapter() {
        ryGoods.setLayoutManager(new LinearLayoutManager(HomeActivityActivity.this, LinearLayoutManager.VERTICAL, false));
        ryGoods.setAdapter(goodsListAdapter);
    }


    @Override
    public void initData() {

    }

    private GoodsGrideAdapter goodsGrideAdapter;
    private GoodsListAdapter goodsListAdapter;

    @Override
    public void queryHomeActivityByName(List<GoodsGson> list) {
        if (goodsGrideAdapter != null) {
            goodsGrideAdapter.replaceData(list);
        }
        if (goodsListAdapter != null) {
            goodsListAdapter.replaceData(list);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    public class GoodsGrideAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
        private Context context;

        public GoodsGrideAdapter(@Nullable List<GoodsGson> data, Context context) {
            super(R.layout.ry_home_pursegoods_item, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsGson item) {
            helper.setText(R.id.tvPrice, item.getGoodsPrice())
                    .setText(R.id.tvGoodsName, item.getGoodsName())
                    .setText(R.id.tvTotal, item.getGoodsLocation())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(context, GoodsDetailActivity.class);
                            view.putExtra("goodsId", String.valueOf(item.getId()));
                            context.startActivity(view);
                        }
                    });
            Glide.with(context).asBitmap().load(item.getGoodsPic()).into((ImageView) helper.getView(R.id.ivCover));
        }
    }

    public class GoodsListAdapter extends BaseQuickAdapter<GoodsGson, BaseViewHolder> {
        private Context context;

        public GoodsListAdapter(@Nullable List<GoodsGson> data, Context context) {
            super(R.layout.ry_purse_goods_vertical_layout, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodsGson item) {
            helper.setText(R.id.tv_goods_price, item.getGoodsPrice())
                    .setText(R.id.tv_goods_name, item.getGoodsName())
                    .setText(R.id.tv_location, item.getGoodsLocation())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(context, GoodsDetailActivity.class);
                            view.putExtra("goodsId", String.valueOf(item.getId()));
                            context.startActivity(view);
                        }
                    });
            Glide.with(context).asBitmap().load(item.getGoodsPic()).into((ImageView) helper.getView(R.id.iv_goods_pic));
        }
    }
}
