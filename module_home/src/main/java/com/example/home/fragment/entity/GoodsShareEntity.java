package com.example.home.fragment.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.commonlib.gson.GoodsShareGson;

public class GoodsShareEntity implements MultiItemEntity {

    public static final int TYPE_TWO = 1;
    public static final int TYPE_ONE = 2;

    public GoodsShareEntity(int itemType) {
        this.itemType = itemType;
    }

    private int itemType;

    public GoodsShareGson seriesEntity;


    public GoodsShareGson getSeriesEntity() {
        return seriesEntity;
    }

    public void setSeriesEntity(GoodsShareGson seriesEntity) {
        this.seriesEntity = seriesEntity;
    }


    @Override
    public int getItemType() {
        return this.itemType;
    }


}

