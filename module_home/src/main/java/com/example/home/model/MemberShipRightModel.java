package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.MemberShipRightContract;

import rx.Observable;

public class MemberShipRightModel implements MemberShipRightContract.Model {
    @Override
    public Observable<BaseGson<GoodsGson>> queryMemberShipGoods() {
        return RetrofitUtils.getInstance().create().queryMemberShipGoods();
    }
}
