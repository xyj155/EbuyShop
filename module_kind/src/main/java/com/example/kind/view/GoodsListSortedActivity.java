package com.example.kind.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.HomePresent;
import com.example.commonlib.util.RouterUtil;
import com.xuyijie.kind.R;
import com.xuyijie.kind.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

@Route(path = RouterUtil.GOODSSORTED)
public class GoodsListSortedActivity extends BaseActivity<HomeContract.View, HomePresent> {


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

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public HomePresent getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_list_sorted;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("商品列表");
//        rbHot = findViewById(R2.id.rb_hot);
//        rbNews = findViewById(R2.id.rb_news);
//        rbPrice = findViewById(R2.id.rb_price);
    }

    private boolean isSetPrice = false;

    @Override
    public void initData() {

        rbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSetPrice) {
                    isSetPrice = false;
                    Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_ascend).into(ivPriceSorted);
                } else {
                    isSetPrice = true;
                    Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_categroy_goods_price_descend).into(ivPriceSorted);
                }
            }
        });
    }

    @OnCheckedChanged({R2.id.rb_hot, R2.id.rb_news})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        int id = view.getId();
        Log.i(TAG, "onRadioCheck: "+id);
        if (id == R.id.rb_hot) {
            Log.i(TAG, "onRadioCheck: ");
            Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_unselected).into(ivPriceSorted);
        } else if (id == R.id.rb_news) {
            Log.i(TAG, "onRadioCheck: +++");
            Glide.with(GoodsListSortedActivity.this).asBitmap().load(R.mipmap.mall_category_goods_price_unselected).into(ivPriceSorted);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
