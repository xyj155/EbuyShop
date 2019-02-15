package com.example.home.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.NewestShelfGson;
import com.example.home.contract.NewUpperShelfContract;
import com.example.home.presenter.NewUpperShelfPresenter;
import com.xuyijie.home.R;
import com.xuyijie.home.R2;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewUpperShelfActivity extends BaseActivity<NewUpperShelfContract.View, NewUpperShelfPresenter> implements NewUpperShelfContract.View {


    @BindView(R2.id.mg_newest)
    MagicIndicator magicIndicator;
    @BindView(R2.id.vp_newest)
    ViewPager vpNewest;

    private UserGoodsStatusFragmentAdapter userGoodsStatusFragmentAdapter;
    private List<NewestShelfGson.TimeBean> titleList = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public NewUpperShelfPresenter getPresenter() {
        return new NewUpperShelfPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_new_upper_shelf;
    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle("新上架");
//        mFragmentContainerHelper.handlePageSelected(1, false);

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DATE));
        mPresenter.newUpperShelf(year + "-" + month + "-" + day);

    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    private void initMagicIndicator2() {
        magicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.85f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }


            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                Log.i(TAG, "getTitleView: " + titleList.get(index).getDate().substring(5, titleList.get(index).getDate().length()));
                SpannableString spannableString = new SpannableString(titleList.get(index).getDate().substring(5, titleList.get(index).getDate().length()) + titleList.get(index).getWeek());
                AbsoluteSizeSpan what = new AbsoluteSizeSpan(34);
                spannableString.setSpan(what, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                simplePagerTitleView.setText(spannableString);
                simplePagerTitleView.setNormalColor(Color.parseColor("#bfbfbf"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setTextSize(16);
                TextPaint tp = simplePagerTitleView.getPaint();
                tp.setFakeBoldText(true);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpNewest.setCurrentItem(index);
//                        mFragmentContainerHelper.handlePageSelected(index);
                        Log.i(TAG, "onClick: "+index);
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
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vpNewest);
//        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

    }

    private List<Fragment> fragmentList = new ArrayList<>();



    @Override
    public void loadDateList(NewestShelfGson timeBeans) {

        for (int i = timeBeans.getTime().size()-1; i >0 ; i--) {
            titleList.add(timeBeans.getTime().get(i));
            Log.i(TAG, "loadDateList: "+timeBeans.getTime().get(i).getDate());
            NewestFragment newestFragment = new NewestFragment();
            Bundle bundle = new Bundle();
            bundle.putString(NewestFragment.EXTRA_TEXT, timeBeans.getTime().get(i).getDate());
            newestFragment.setArguments(bundle);
            fragmentList.add(newestFragment);
        }
        userGoodsStatusFragmentAdapter = new UserGoodsStatusFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vpNewest.setAdapter(userGoodsStatusFragmentAdapter);
        initMagicIndicator2();
    }


    public class UserGoodsStatusFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<NewestShelfGson.TimeBean> title;

        public UserGoodsStatusFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<NewestShelfGson.TimeBean> title) {
            super(fm);
            this.fragmentList = fragmentList;
            this.title = title;
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
