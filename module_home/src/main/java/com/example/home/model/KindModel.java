package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.KindContract;


import rx.Observable;

public class KindModel implements KindContract.Model {
//    @Override
//    public void getGoodsList(BaseObserver<BaseGson<GoodsGson>> observer) {
//        RetrofitUtils.getInstance().create()
//                .getGoodsList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }
//
//    @Override
//    public void getGoodsListItem(BaseObserver<BaseGson<GoodsGson>> observer, int pid) {
//        RetrofitUtils.getInstance().create()
//                .getGoodsListByPid(String.valueOf(pid))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }

    @Override
    public Observable<BaseGson<KindItemGson>> getGoodsList() {
        return  RetrofitUtils.getInstance().create()
                .getGoodsList();
    }

    @Override
    public Observable<BaseGson<KindItemGson>> getGoodsListItem(int pid) {
        return  RetrofitUtils.getInstance().create()
                .getGoodsListByPid(String.valueOf(pid));
    }
}
