package com.example.goodscar;

import android.os.Bundle;
import android.util.Log;

import com.example.commonlib.base.BaseActivity;
import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.presenter.LoginPresent;

import java.util.List;


public class Main extends BaseActivity<HomeContract.View, LoginPresent> implements HomeContract.View {
    //    private LoginPresent homePresent = new LoginPresent(this);
    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        homePresent.userLogin();
    }

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }



    @Override
    public LoginPresent getPresenter() {
        return new LoginPresent(this);
    }

    @Override
    public int intiLayout() {
        return 0;
    }

    @Override
    public void initView() {
        mPresenter.userLogin();
    }

    @Override
    public void initData() {

    }


    @Override
    public void showError(String msg) {
        Log.i(TAG, "showError: " + msg);
    }

    @Override
    public void showDialog(String msg) {

    }



    @Override
    public void hideDialog() {

    }

    @Override
    public void loadUser(List<UserGson> userGson) {
        Log.i(TAG, "loadUser: " + userGson);
    }
}
