package com.example.commonlib.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.contract.AddressOfNewReceiptContract;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;

import rx.Observable;

public class AddressOfNewReceiptModel implements AddressOfNewReceiptContract.Model {


    @Override
    public Observable<BaseGson<EmptyGson>> saveNewAddress(String username, String tel, String local, String detail, String isDefalt, String userId) {
        return RetrofitUtils.getInstance().create().saveNewAddressByUserId(username, tel, local, detail, isDefalt, userId);
    }
}
