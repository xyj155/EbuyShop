package com.example.user.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.EmptyContract;
import com.example.commonlib.presenter.EmptyPresenter;
import com.example.commonlib.view.ScaleTransitionPagerTitleView;
import com.example.user.adapter.UserGoodsStatusFragmentAdapter;
import com.example.user.view.fragment.CollectingGoodsFragment;
import com.example.user.view.fragment.SubstitutePaymentFragment;
import com.example.user.view.fragment.SubstituteShipmentFragment;
import com.example.user.view.fragment.SubstitutesEvaluatedFragment;
import com.xuyijie.user.R;
import com.xuyijie.user.R2;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFormStatusActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {

    @BindView(R2.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    private String title[] = new String[]{"待付款", "代发货", "待收货", "待评价"};
    private UserGoodsStatusFragmentAdapter userGoodsStatusAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_form_status;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        fragmentList.add(new SubstitutePaymentFragment());
        fragmentList.add(new SubstituteShipmentFragment());
        fragmentList.add(new CollectingGoodsFragment());
        fragmentList.add(new SubstitutesEvaluatedFragment());
        userGoodsStatusAdapter = new UserGoodsStatusFragmentAdapter(getSupportFragmentManager(), fragmentList, title);
        initMagicIndicator();
        switch (getIntent().getIntExtra("index", 8)) {
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                break;
            case 4:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void initData() {

    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(title[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setMaxCircleRadius(10);
                indicator.setColors(Color.parseColor("#ffd321"), Color.parseColor("#ffd321"), Color.parseColor("#ffd321"));
                return indicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        initToolBar().setToolBarTitle("订单信息");
        magicIndicator.setNavigator(commonNavigator);
        viewPager.setAdapter(userGoodsStatusAdapter);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

}
