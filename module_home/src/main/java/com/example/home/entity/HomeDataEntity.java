package com.example.home.entity;

import com.example.commonlib.gson.BannerGson;
import com.example.commonlib.gson.GoodsGson;
import com.example.commonlib.gson.HomeIconGson;
import com.example.commonlib.gson.HomePurseAdvertisementGson;
import com.example.commonlib.gson.HotPurseActivityGson;
import com.example.commonlib.gson.MarQueenGson;
import com.example.commonlib.gson.TimeGoodsGson;

import java.util.List;

public class HomeDataEntity {
    private List<GoodsGson> timePurseGoodsList;
    private List<GoodsGson> purseGoodsList;
    private List<HotPurseActivityGson> hotPurseActivityGsons;
    private List<BannerGson> bannerGsons;
    private List<MarQueenGson> marQueenGsonList;
    private TimeGoodsGson.TimeBean timeBean;
    private HomePurseAdvertisementGson homePurseAdvertisementGson;
private List<HomeIconGson> homeIconGsons;

    public List<HomeIconGson> getHomeIconGsons() {
        return homeIconGsons;
    }

    public void setHomeIconGsons(List<HomeIconGson> homeIconGsons) {
        this.homeIconGsons = homeIconGsons;
    }

    public HomePurseAdvertisementGson getHomePurseAdvertisementGson() {
        return homePurseAdvertisementGson;
    }

    public void setHomePurseAdvertisementGson(HomePurseAdvertisementGson homePurseAdvertisementGson) {
        this.homePurseAdvertisementGson = homePurseAdvertisementGson;
    }

    public TimeGoodsGson.TimeBean getTimeBean() {
        return timeBean;
    }

    public void setTimeBean(TimeGoodsGson.TimeBean timeBean) {
        this.timeBean = timeBean;
    }

    public List<MarQueenGson> getMarQueenGsonList() {
        return marQueenGsonList;
    }

    public void setMarQueenGsonList(List<MarQueenGson> marQueenGsonList) {
        this.marQueenGsonList = marQueenGsonList;
    }

    public HomeDataEntity(List<GoodsGson> timePurseGoodsList,List<HotPurseActivityGson> hotPurseActivityGsons, List<BannerGson> bannerGsons, List<MarQueenGson> marQueenGsonList, HomePurseAdvertisementGson homePurseAdvertisementGson,List<HomeIconGson> homeIconGson) {
        this.timePurseGoodsList = timePurseGoodsList;
        this.homePurseAdvertisementGson = homePurseAdvertisementGson;
        this.hotPurseActivityGsons = hotPurseActivityGsons;
        this.bannerGsons = bannerGsons;
        this.marQueenGsonList = marQueenGsonList;
        this.homeIconGsons=homeIconGson;
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
