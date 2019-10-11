package com.xuyijie.stushop.contract;

import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.Lottery;

import rx.Observable;

public interface LotteryContract {
    interface Model {
        Observable<Lottery> queryLottery(String  appId);
    }

    interface View extends BaseView {
        void getLottery(Lottery lottery);
    }

    interface Presenter {
        void queryLottery(String  appId);
    }
}
