package com.example.home.fragment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.base.BasePresenter;
import com.example.commonlib.base.BaseView;
import com.example.home.fragment.contract.HomePageContract;
import com.example.home.fragment.presenter.HomePagePresenter;
import com.xuyijie.home.R;

public class SchoolVipActivity extends BaseActivity<HomePageContract.View,HomePagePresenter> {


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public HomePagePresenter getPresenter() {
        return null;
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_school_vip;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
