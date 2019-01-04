package com.example.commonlib.api;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.gson.UserGson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    @GET("/StuShop/public/index.php/index/User/getUserList")
    Observable<BaseGson<UserGson>> getUserList();

    @GET("/StuShop/public/index.php/index/Goods/getGoodsKindList")
    Observable<BaseGson<KindItemGson>> getGoodsList();

    @GET("/StuShop/public/index.php/index/Goods/getGoodsListByPid")
    Observable<BaseGson<KindItemGson>> getGoodsListByPid(@Query("pid") String pid);
}
