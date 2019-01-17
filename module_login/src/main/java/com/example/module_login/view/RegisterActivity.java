package com.example.module_login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.gson.UserGson;
import com.example.module_login.R;
import com.example.module_login.contract.UserContract;
import com.example.module_login.presenter.UserPresenter;

public class RegisterActivity extends BaseActivity<UserContract.View,UserPresenter> implements UserContract.View {



    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserPresenter getPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void userLogin(UserGson userGson) {

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
}
