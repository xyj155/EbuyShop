package com.example.home.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.MemberGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.home.contract.MembershipOpeningContract;

import rx.Observable;

public class MembershipOpeningModel implements MembershipOpeningContract.Model {
    @Override
    public Observable<BaseGson<MemberGson>> queryMemberPrice() {
        return RetrofitUtils.getInstance().create().queryMemberPrice();
    }

    @Override
    public Observable<BaseGson<EmptyGson>> submitUserMemberShip(String userId, String memberRank) {
        return RetrofitUtils.getInstance().create().submitUserMemberShip(userId,memberRank);
    }
}
