package com.example.goodscar.model;

import com.example.commonlib.base.BaseGson;
import com.example.commonlib.gson.EmptyGson;
import com.example.commonlib.http.RetrofitUtils;
import com.example.goodscar.contract.GoodsOperationContract;

import rx.Observable;

public class GoodsCarOperationModel implements GoodsOperationContract.Model {



    @Override
    public Observable<BaseGson<EmptyGson>> addGoodsInShopCarById(String userId, String goodsId,String type) {
        return RetrofitUtils.getInstance().create().addGoodsInShopCarById(userId, goodsId,type);
    }


}
