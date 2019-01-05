package com.example.kind.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.presenter.HomePresent;
import com.xuyijie.kind.R;

public class GoodsListSortedActivity extends BaseActivity<HomeContract.View,HomePresent> {


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

    }

    @Override
    public void initData() {

    }
}
