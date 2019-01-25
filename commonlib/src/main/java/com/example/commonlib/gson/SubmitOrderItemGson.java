package com.example.commonlib.gson;

public class SubmitOrderItemGson {

    /**
     * id : 211
     * userId : 1
     * goodsId : 5
     * status : 1
     * addTime : 2019-01-24 06:46:12
     * orderNum : 2019012407918
     * isInshopCar : 1
     */

    private int id;
    private int userId;
    private int goodsId;
    private String status;
    private String addTime;
    private String orderNum;
    private String isInshopCar;

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
}
