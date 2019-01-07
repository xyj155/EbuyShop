package com.example.commonlib.api;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.KindItemGson;
import com.example.commonlib.gson.UserGson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    @GET("/StuShop/public/index.php/index/User/getUserList")
    Observable<BaseGson<UserGson>> getUserList();

    //获取商品种类
    @GET("/StuShop/public/index.php/index/Goods/getGoodsKindList")
    Observable<BaseGson<KindItemGson>> getGoodsList();

    //通过商品id查询商品列表
    @GET("/StuShop/public/index.php/index/Goods/getGoodsListByPid")
    Observable<BaseGson<KindItemGson>> getGoodsListByPid(@Query("pid") String pid);


    @GET("/StuShop/public/index.php/index/Goods/queryPurseGoods")
    Observable<BaseGson<GoodsGson>> queryPurseGoods(@Query("isPurse") String isPurse);


    @GET("/StuShop/public/index.php/index/Goods/getHomeActivity")
    Observable<BaseGson<HotPurseActivityGson>> getHomeActivity();


    @GET("/StuShop/public/index.php/index/Goods/getGoodsListByKind")
    Observable<BaseGson<GoodsGson>> getGoodsListByKind(@Query("kind")String kind,@Query("type")String type,@Query("isasc")String isasc);

}
