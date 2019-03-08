package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.commonlib.contract.AddressEditContract;

import rx.Observable;

public class AddressEditModel implements AddressEditContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> updateAddress(String id, String username, String tel, String location, String detail) {
        return RetrofitUtils.getInstance().create().updateUserAddress(username,tel,location,detail,id);
    }
}
