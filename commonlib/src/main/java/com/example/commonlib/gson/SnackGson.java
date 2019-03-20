package com.example.commonlib.gson;

import java.util.List;

public class SnackGson {

    /**
     * id : 5
     * foodName : 果蔬小饼干
     * foodsPrice : 23.00
     * foodsSize : 1罐
     * foodPicture : http://syxfoods.oss-cn-beijing.aliyuncs.com/果蔬小饼干.jpg?x-oss-process=style/press
     * activityId : 2
     * kindId : 12
     * tasty : [{"foodsTaste":"默认","foodsId":5,"styleId":55}]
     */

    private int id;
    private int status;
    private String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String foodName;
    private double foodsPrice;
    private String foodsSize;
    private int goodsCount;
private int needCount;

    public int getNeedCount() {
        return needCount;
    }

    public void setNeedCount(int needCount) {
        this.needCount = needCount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    private String foodPicture;
    private int activityId;
    private int kindId;
    private List<TastyBean> tasty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodsPrice() {
        return foodsPrice;
    }

    public void setFoodsPrice(double foodsPrice) {
        this.foodsPrice = foodsPrice;
    }

    public String getFoodsSize() {
        return foodsSize;
    }

    public void setFoodsSize(String foodsSize) {
        this.foodsSize = foodsSize;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public List<TastyBean> getTasty() {
        return tasty;
    }

    public void setTasty(List<TastyBean> tasty) {
        this.tasty = tasty;
    }

    public static class TastyBean {
        /**
         * foodsTaste : 默认
         * foodsId : 5
         * styleId : 55
         */

        private String foodsTaste;
        private int foodsId;
        private int styleId;

        public String getFoodsTaste() {
            return foodsTaste;
        }

        public void setFoodsTaste(String foodsTaste) {
            this.foodsTaste = foodsTaste;
        }

        public int getFoodsId() {
            return foodsId;
        }
private int goodsCount;

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public void setFoodsId(int foodsId) {
            this.foodsId = foodsId;
        }

        public int getStyleId() {
            return styleId;
        }

        public void setStyleId(int styleId) {
            this.styleId = styleId;
        }
    }
}
