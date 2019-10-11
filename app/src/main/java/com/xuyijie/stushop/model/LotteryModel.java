package com.xuyijie.stushop.model;

import com.example.commonlib.gson.Lottery;
import com.example.commonlib.http.RetrofitUtil;
import com.xuyijie.stushop.contract.LotteryContract;

import rx.Observable;

public class LotteryModel implements LotteryContract.Model {
    @Override
    public Observable<Lottery> queryLottery(String appId) {
        return RetrofitUtil.getInstance().create().queryLottery();
    }
}
