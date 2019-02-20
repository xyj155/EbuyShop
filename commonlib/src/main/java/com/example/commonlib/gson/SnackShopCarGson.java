package com.example.commonlib.gson;

public class SnackShopCarGson {

    /**
     * userId : 596
     * foodName : 阿钦蛋糕盒子（65元装）
     * foodsPrice : 65.00
     * foodsSize : 1盒
     * foodsTaste : 榴莲拔丝盒子蛋糕
     * foodsId : 3
     * tasteId : 17
     * count : 1
     */

    private int userId;
    private String foodName;
    private String foodsPrice;
    private String foodsSize;
    private String foodsTaste;
    private int foodsId;
    private int tasteId;
    private int count;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodsPrice() {
        return foodsPrice;
    }

    public void setFoodsPrice(String foodsPrice) {
        this.foodsPrice = foodsPrice;
    }

    public String getFoodsSize() {
        return foodsSize;
    }

    public void setFoodsSize(String foodsSize) {
        this.foodsSize = foodsSize;
    }

    public String getFoodsTaste() {
        return foodsTaste;
    }

    public void setFoodsTaste(String foodsTaste) {
        this.foodsTaste = foodsTaste;
    }

    public int getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(int foodsId) {
        this.foodsId = foodsId;
    }

    public int getTasteId() {
        return tasteId;
    }

    public void setTasteId(int tasteId) {
        this.tasteId = tasteId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
