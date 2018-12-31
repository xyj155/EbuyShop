package com.example.commonlib.model;

import com.example.commonlib.contract.HomeContract;
import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.http.RetrofitUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeModel implements HomeContract.Model {

    @Override
    public void userLogin(BaseObserver observer) {
        RetrofitUtils.getInstance().create()
                .getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
