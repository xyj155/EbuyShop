package com.example.home.fragment.entity;

import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;

import java.util.List;

public class HomeDataEntity {
    private List<GoodsGson> timePurseGoodsList;
    private List<GoodsGson> purseGoodsList;
    private List<HotPurseActivityGson> hotPurseActivityGsons;

    public HomeDataEntity(List<GoodsGson> timePurseGoodsList, List<GoodsGson> purseGoodsList, List<HotPurseActivityGson> hotPurseActivityGsons) {
        this.timePurseGoodsList = timePurseGoodsList;
        this.purseGoodsList = purseGoodsList;
        this.hotPurseActivityGsons = hotPurseActivityGsons;
    }

    public List<HotPurseActivityGson> getHotPurseActivityGsons() {
        return hotPurseActivityGsons;
    }

    public void setHotPurseActivityGsons(List<HotPurseActivityGson> hotPurseActivityGsons) {
        this.hotPurseActivityGsons = hotPurseActivityGsons;
    }

    public List<GoodsGson> getTimePurseGoodsList() {
        return timePurseGoodsList;
    }

    public void setTimePurseGoodsList(List<GoodsGson> timePurseGoodsList) {
        this.timePurseGoodsList = timePurseGoodsList;
    }

    public List<GoodsGson> getPurseGoodsList() {
        return purseGoodsList;
    }

    public void setPurseGoodsList(List<GoodsGson> purseGoodsList) {
        this.purseGoodsList = purseGoodsList;
    }
}
