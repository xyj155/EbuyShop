package com.example.commonlib.gson;

import java.util.List;

public class SecondHandsGoodsGson {

    /**
     * id : 6
     * userId : 1
     * goodsName : 徐易杰
     * goodsSchool : 学校：阿坝师范高等专科学校
     * goodsReason : 男滴NND南安
     * goodsPrice : 11.70
     * goodsPostMoney :
     * goodsMethod : 包邮
     * goodsKind : 手机
     * createTime : 2019-02-05 09:15:15
     * isShow : 1
     * goodsPic : ["/StuShop/public/secondHands553905181.jpeg"]
     */

    private int id;
    private int userId;
    private String goodsName;
    private String goodsSchool;
    private String goodsReason;
    private String goodsPrice;
    private String goodsPostMoney;
    private String goodsMethod;
    private String goodsKind;
    private String createTime;
    private String isShow;
    private List<String> goodsPic;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSchool() {
        return goodsSchool;
    }

    public void setGoodsSchool(String goodsSchool) {
        this.goodsSchool = goodsSchool;
    }

    public String getGoodsReason() {
        return goodsReason;
    }

    public void setGoodsReason(String goodsReason) {
        this.goodsReason = goodsReason;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPostMoney() {
        return goodsPostMoney;
    }

    public void setGoodsPostMoney(String goodsPostMoney) {
        this.goodsPostMoney = goodsPostMoney;
    }

    public String getGoodsMethod() {
        return goodsMethod;
    }

    public void setGoodsMethod(String goodsMethod) {
        this.goodsMethod = goodsMethod;
    }

    public String getGoodsKind() {
        return goodsKind;
    }

    public void setGoodsKind(String goodsKind) {
        this.goodsKind = goodsKind;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public List<String> getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(List<String> goodsPic) {
        this.goodsPic = goodsPic;
    }
}
