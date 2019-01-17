package com.example.commonlib.commonactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlib.R;
import com.example.commonlib.R2;
import com.example.commonlib.adapter.ItemTitlePagerAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.fragment.GoodsCommentFragment;
import com.example.commonlib.fragment.GoodsDetailFragment;
import com.example.commonlib.fragment.GoodsInfoFragment;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.util.RouterUtil;
import com.example.commonlib.view.NoScrollViewPager;
import com.example.commonlib.view.ShopChooseDialog;
import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterUtil.GOODSDETAIL)
public class GoodsDetailActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> {
    public PagerSlidingTabStrip psts_tabs;
    public NoScrollViewPager vp_content;
    public TextView tv_title;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_add)
    TextView tvAdd;
    @BindView(R2.id.tv_buy)
    TextView tvBuy;
private ShopChooseDialog shopChooseDialog;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsDetailPresenter getPresenter() {
        return null;
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        Log.i(TAG, "initView: "+getIntent().getStringExtra("goodId"));
        shopChooseDialog=new ShopChooseDialog(this,getIntent().getStringExtra("goodId"));
        psts_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
        vp_content = (NoScrollViewPager) findViewById(R.id.vp_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        fragmentList.add(new GoodsInfoFragment());
        fragmentList.add(new GoodsDetailFragment());
        fragmentList.add(new GoodsCommentFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        vp_content.setOffscreenPageLimit(3);
        psts_tabs.setViewPager(vp_content);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.iv_back, R2.id.tv_add, R2.id.tv_buy})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_add) {
            shopChooseDialog.show();
        }else if (id==R.id.iv_back){
            finish();
        }
    }


}
