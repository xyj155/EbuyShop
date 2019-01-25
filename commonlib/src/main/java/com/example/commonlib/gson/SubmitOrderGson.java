package com.example.commonlib.gson;

public class SubmitOrderGson {

    /**
     * id : 2
     * userId : 1
     * goodsId : 1
     * status : 1
     * addTime : 2019-01-24 16:29:59
     * orderNum : 2019012467092
     * isInshopCar : 1
     * goodsName : 1
     * goodsPrice : 1.00
     * goodsPicUrl : 1
     * goodsStock : 1
     */

    private int id;
    private int userId;
    private int goodsId;
    private String status;
    private String addTime;
    private String orderNum;
    private String isInshopCar;
    private String goodsName;
    private String goodsPrice;
    private String goodsPicUrl;
    private String goodsStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getIsInshopCar() {
        return isInshopCar;
    }

    public void setIsInshopCar(String isInshopCar) {
        this.isInshopCar = isInshopCar;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPicUrl() {
        return goodsPicUrl;
    }

    public void setGoodsPicUrl(String goodsPicUrl) {
        this.goodsPicUrl = goodsPicUrl;
    }

    public String getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(String goodsStock) {
        this.goodsStock = goodsStock;
    }
}
