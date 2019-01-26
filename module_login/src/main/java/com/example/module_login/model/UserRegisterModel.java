package com.example.module_login.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.UserGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.util.RxPartMapUtils;
import com.example.module_login.contract.UserRegisterContract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class UserRegisterModel implements UserRegisterContract.Model {
    @Override
    public Observable<BaseGson<UserGson>> userRegister(String userId, String count, String goodsId, String age,String sex, String school, MultipartBody.Part avatar) {
        Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("username", RxPartMapUtils.toRequestBodyOfText(userId));
        partMap.put("password", RxPartMapUtils.toRequestBodyOfText(count));
        partMap.put("telphone", RxPartMapUtils.toRequestBodyOfText(goodsId));
        partMap.put("age", RxPartMapUtils.toRequestBodyOfText(age));
        partMap.put("sex", RxPartMapUtils.toRequestBodyOfText(sex));
        partMap.put("school", RxPartMapUtils.toRequestBodyOfText(school));
        return RetrofitUtils.getInstance().create().userRegister(partMap, avatar);
    }


}
