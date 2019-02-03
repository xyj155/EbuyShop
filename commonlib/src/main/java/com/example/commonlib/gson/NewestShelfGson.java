package com.example.commonlib.gson;

import java.util.List;

public class NewestShelfGson {

    private List<TimeBean> time;
    private List<GoodsBean> goods;

    public List<TimeBean> getTime() {
        return time;
    }

    public void setTime(List<TimeBean> time) {
        this.time = time;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class TimeBean {
        /**
         * date : 2019-02-02
         * week : 星期六
         */

        private String date;
        private String week;

        @Override
        public String toString() {
            return "TimeBean{" +
                    "date='" + date + '\'' +
                    ", week='" + week + '\'' +
                    '}';
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }

    public static class GoodsBean {
        /**
         * id : 33
         * goodsName : Xiaomi/小米 小米笔记本 Pro(i5 8250/8GB)GTX版i7手提游戏本电脑
         * goodsService : 2,3,4
         * goodsLocation : 上海
         * goodsPrice : 3599.00
         * goodsPic : https://gd2.alicdn.com/imgextra/i3/3034056874/O1CN01GbLHQ320eL2yCylxx_!!3034056874.jpg
         * goodsDescribe : Xiaomi/小米 小米笔记本 Pro(i5 8250/8GB)GTX版i7手提游戏本电脑
         * goodsOwner : 16
         * updateTime : 2019-01-24 16:37:31
         * goodsKind : 108
         * isPurse : 1
         * originalPrice : 3899.00
         * postFree : 0
         * isTimePurse : 0
         * goodsDetailWeb : null
         * goodsParameter : 0
         * isShow : 1
         * orderCount : null
         * tradePrice : null
         */

        private int id;

        @Override
        public String toString() {
            return "GoodsBean{" +
                    "id=" + id +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsService='" + goodsService + '\'' +
                    ", goodsLocation='" + goodsLocation + '\'' +
                    ", goodsPrice='" + goodsPrice + '\'' +
                    ", goodsPic='" + goodsPic + '\'' +
                    ", goodsDescribe='" + goodsDescribe + '\'' +
                    ", goodsOwner='" + goodsOwner + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", goodsKind='" + goodsKind + '\'' +
                    ", isPurse=" + isPurse +
                    ", originalPrice='" + originalPrice + '\'' +
                    ", postFree='" + postFree + '\'' +
                    ", isTimePurse='" + isTimePurse + '\'' +
                    ", goodsDetailWeb=" + goodsDetailWeb +
                    ", goodsParameter='" + goodsParameter + '\'' +
                    ", isShow='" + isShow + '\'' +
                    ", orderCount=" + orderCount +
                    ", tradePrice=" + tradePrice +
                    '}';
        }

        private String goodsName;
        private String goodsService;
        private String goodsLocation;
        private String goodsPrice;
        private String goodsPic;
        private String goodsDescribe;
        private String goodsOwner;
        private String updateTime;
        private String goodsKind;
        private int isPurse;
        private String originalPrice;
        private String postFree;
        private String isTimePurse;
        private Object goodsDetailWeb;
        private String goodsParameter;
        private String isShow;
        private Object orderCount;
        private Object tradePrice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsService() {
            return goodsService;
        }

        public void setGoodsService(String goodsService) {
            this.goodsService = goodsService;
        }

        public String getGoodsLocation() {
            return goodsLocation;
        }

        public void setGoodsLocation(String goodsLocation) {
            this.goodsLocation = goodsLocation;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsPic() {
            return goodsPic;
        }

        public void setGoodsPic(String goodsPic) {
            this.goodsPic = goodsPic;
        }

        public String getGoodsDescribe() {
            return goodsDescribe;
        }

        public void setGoodsDescribe(String goodsDescribe) {
            this.goodsDescribe = goodsDescribe;
        }

        public String getGoodsOwner() {
            return goodsOwner;
        }

        public void setGoodsOwner(String goodsOwner) {
            this.goodsOwner = goodsOwner;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getGoodsKind() {
            return goodsKind;
        }

        public void setGoodsKind(String goodsKind) {
            this.goodsKind = goodsKind;
        }

        public int getIsPurse() {
            return isPurse;
        }

        public void setIsPurse(int isPurse) {
            this.isPurse = isPurse;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getPostFree() {
            return postFree;
        }

        public void setPostFree(String postFree) {
            this.postFree = postFree;
        }

        public String getIsTimePurse() {
            return isTimePurse;
        }

        public void setIsTimePurse(String isTimePurse) {
            this.isTimePurse = isTimePurse;
        }

        public Object getGoodsDetailWeb() {
            return goodsDetailWeb;
        }

        public void setGoodsDetailWeb(Object goodsDetailWeb) {
            this.goodsDetailWeb = goodsDetailWeb;
        }

        public String getGoodsParameter() {
            return goodsParameter;
        }

        public void setGoodsParameter(String goodsParameter) {
            this.goodsParameter = goodsParameter;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public Object getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(Object orderCount) {
            this.orderCount = orderCount;
        }

        public Object getTradePrice() {
            return tradePrice;
        }

        public void setTradePrice(Object tradePrice) {
            this.tradePrice = tradePrice;
        }
    }
}
