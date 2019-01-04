package com.example.kind.model;

import com.example.commonlib.http.BaseObserver;
import com.example.commonlib.http.RetrofitUtils;
import com.example.kind.contract.KindContract;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KindModel implements KindContract.Model {
    @Override
    public void getGoodsList(BaseObserver observer) {
        RetrofitUtils.getInstance().create()
                .getGoodsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getGoodsListItem(BaseObserver observer, int pid) {
        RetrofitUtils.getInstance().create()
                .getGoodsListByPid(String.valueOf(pid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
