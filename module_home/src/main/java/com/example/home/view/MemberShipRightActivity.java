package com.example.home.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseActivity;
import com.example.home.contract.MemberShipRightContract;
import com.example.home.presenter.MemberShipRightPresenter;
import com.example.home.view.fragment.MemberCouponFragment;
import com.example.home.view.fragment.MemberGoodsFragment;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberShipRightActivity extends BaseActivity<MemberShipRightContract.View, MemberShipRightPresenter> {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.mi_member_ship)
    MagicIndicator miMemberShip;
    @BindView(R2.id.ry_membership)
    ViewPager ryMembership;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public MemberShipRightPresenter getPresenter() {
        return null;
    }

    private String[] title = {"会员商品", "优惠券"};

    private void initMagicIndicator2() {
        miMemberShip.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.85f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }


            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(title[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#bfbfbf"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setTextSize(16);
                TextPaint tp = simplePagerTitleView.getPaint();
                tp.setFakeBoldText(true);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ryMembership.setCurrentItem(index);
//                        mFragmentContainerHelper.handlePageSelected(index);
                        Log.i(TAG, "onClick: " + index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 40));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#ffd321"));
                return indicator;
            }
        });
        miMemberShip.setNavigator(commonNavigator);
        ViewPagerHelper.bind(miMemberShip, ryMembership);

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_member_ship_right;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("会员尊享");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MemberGoodsFragment());
        fragmentList.add(new MemberCouponFragment());
        MemberShipRightFragmentAdapter userGoodsStatusFragmentAdapter = new MemberShipRightFragmentAdapter(getSupportFragmentManager(), fragmentList);

        ryMembership.setAdapter(userGoodsStatusFragmentAdapter);
        initMagicIndicator2();
    }

    @Override
    public void initData() {

    }

    public class MemberShipRightFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private String[] title = {"会员商品", "优惠券"};

        public MemberShipRightFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
