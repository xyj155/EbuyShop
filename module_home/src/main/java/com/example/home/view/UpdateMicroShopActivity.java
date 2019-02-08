package com.example.home.view;

import android.os.Bundle;

import com.example.commonlib.base.BaseActivity;
import com.example.home.contract.UpdateMicroShopContract;
import com.example.home.presenter.UpdateMicroShopPresenter;
import com.xuyijie.home.R;

public class UpdateMicroShopActivity extends BaseActivity<UpdateMicroShopContract.View, UpdateMicroShopPresenter> {

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UpdateMicroShopPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_update_micro_shop;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
