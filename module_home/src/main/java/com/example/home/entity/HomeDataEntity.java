package com.example.home.entity;

import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;

import java.util.List;

public class HomeDataEntity {
    private List<GoodsGson> timePurseGoodsList;
    private List<GoodsGson> purseGoodsList;
    private List<HotPurseActivityGson> hotPurseActivityGsons;
    private List<BannerGson> bannerGsons;
    private List<MarQueenGson> marQueenGsonList;

    public List<MarQueenGson> getMarQueenGsonList() {
        return marQueenGsonList;
    }

    public void setMarQueenGsonList(List<MarQueenGson> marQueenGsonList) {
        this.marQueenGsonList = marQueenGsonList;
    }

    public HomeDataEntity(List<GoodsGson> timePurseGoodsList, List<GoodsGson> purseGoodsList, List<HotPurseActivityGson> hotPurseActivityGsons, List<BannerGson> bannerGsons, List<MarQueenGson> marQueenGsonList) {
        this.timePurseGoodsList = timePurseGoodsList;
        this.purseGoodsList = purseGoodsList;
        this.hotPurseActivityGsons = hotPurseActivityGsons;
        this.bannerGsons = bannerGsons;

        this.marQueenGsonList = marQueenGsonList;
    }

    public List<BannerGson> getBannerGsons() {
        return bannerGsons;
    }

    public void setBannerGsons(List<BannerGson> bannerGsons) {
        this.bannerGsons = bannerGsons;
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
