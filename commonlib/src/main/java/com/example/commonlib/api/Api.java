package com.example.commonlib.api;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.UserGson;

import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    @GET("/SchoolShop/public/index.php/index/User/getUserList")
    Observable<BaseGson<UserGson>> getUserList();
}
