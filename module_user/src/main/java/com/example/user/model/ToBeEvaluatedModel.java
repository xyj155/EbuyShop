package com.example.user.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.user.contract.ToBeEvaluatedContract;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class ToBeEvaluatedModel implements ToBeEvaluatedContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> updateGoodsEvaluate(Map<String, RequestBody> partMap, List<MultipartBody.Part> file) {
        return RetrofitUtils.getInstance().create().updateGoodsEvaluate(partMap,file);
    }
}
