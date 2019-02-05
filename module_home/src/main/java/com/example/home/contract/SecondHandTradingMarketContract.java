package com.example.home.contract;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.base.BaseView;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.gson.SecondHandsGoodsGson;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public interface SecondHandTradingMarketContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addSecondHanding(Map<String, RequestBody> partMap,
                                                         List<MultipartBody.Part> file);

        Observable<BaseGson<SecondHandsGoodsGson>>queryAllSecondGoods(String limit,String page);
    }

    interface View extends BaseView {
        void uploadSuccess(boolean success);

        void queryAllSecondGoods(List<SecondHandsGoodsGson> secondHandsGoodsGsons);

        void loadMoreData(List<SecondHandsGoodsGson> secondHandsGoodsGsons);
    }

    interface Presenter {
        void addSecondHanding(Map<String, RequestBody> partMap,
                              List<MultipartBody.Part> file);
        void queryAllSecondGoods(String limit,String page);
        void loadMoreData(String limit,String page);
    }
}
