package com.example.commonlib.commonactivity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.commonlib.R;
import com.example.commonlib.adapter.ItemTitlePagerAdapter;
import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.GoodsDetailContract;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.fragment.GoodsCommentFragment;
import com.example.commonlib.fragment.GoodsDetailFragment;
import com.example.commonlib.fragment.GoodsInfoFragment;
import com.example.commonlib.presenter.GoodsDetailPresenter;
import com.example.commonlib.presenter.GoodsSortedPresenter;
import com.example.commonlib.view.NoScrollViewPager;
import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;


public class GoodsDetailActivity extends BaseActivity<GoodsDetailContract.View, GoodsDetailPresenter> {
    public PagerSlidingTabStrip psts_tabs;
    public NoScrollViewPager vp_content;
    public TextView tv_title;

    private List<Fragment> fragmentList = new ArrayList<>();

    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsCommentFragment goodsCommentFragment;


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
}
