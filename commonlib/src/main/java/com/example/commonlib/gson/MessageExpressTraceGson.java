package com.example.commonlib.gson;

public class MessageExpressTraceGson {

    /**
     * orderNum : 2019022016286
     * addTime : 2019-02-20 06:27:30
     * goodsId : 41
     * expressNum : null
     * goodsName : 16G DDR4 2133Mhz ECC RDIMM
     * goodsPicUrl : https://img.alicdn.com/imgextra/i3/2781172225/TB2GY.afQomBKNjSZFqXXXtqVXa_!!2781172225.jpg_60x60q90.jpg
     */

    private String orderNum;
    private int count;
    private String expressCod;

    public String getExpressCod() {
        return expressCod;
    }

    public void setExpressCod(String expressCod) {
        this.expressCod = expressCod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String addTime;
    private int goodsId;
    private Object expressNum;
    private String goodsName;
    private String goodsPicUrl;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Object getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(Object expressNum) {
        this.expressNum = expressNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPicUrl() {
        return goodsPicUrl;
    }

    public void setGoodsPicUrl(String goodsPicUrl) {
        this.goodsPicUrl = goodsPicUrl;
    }
}
