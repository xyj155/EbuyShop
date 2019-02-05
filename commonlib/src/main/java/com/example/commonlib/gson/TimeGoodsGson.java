package com.example.commonlib.gson;

import java.util.List;

public class TimeGoodsGson {


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
            @Override
            public String toString() {
                return "TimeBean{" +
                        "id=" + id +
                        ", startTime='" + startTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", currentTime='" + currentTime + '\'' +
                        '}';
            }

            /**
             * id : 1
             * startTime : 2019-02-05 13:22:40
             * endTime : 2019-02-14 13:22:46
             */

            private int id;
            private String startTime;
            private String endTime;
private String currentTime;

            public String getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(String currentTime) {
                this.currentTime = currentTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }

        public static class GoodsBean {
            /**
             * id : 20
             * goodsName : Xiaomi/小米 游戏本 八代增强版 I7 1060独显15.6吃鸡笔记本电脑
             * goodsLocation : 上海
             * goodsPic : https://gd1.alicdn.com/imgextra/i2/3034056874/O1CN01JaqEAY20eL2zSnOyI_!!3034056874.jpg_400x400.jpg_.webp
             * goodsPrice : 5899.00
             * nowPrice : 5899.00
             * previousPrice : 5600.00
             */

            private int id;
            private String goodsName;
            private String goodsLocation;
            private String goodsPic;
            private String goodsPrice;
            private String nowPrice;
            private String time;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            private String previousPrice;

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

            public String getGoodsLocation() {
                return goodsLocation;
            }

            public void setGoodsLocation(String goodsLocation) {
                this.goodsLocation = goodsLocation;
            }

            public String getGoodsPic() {
                return goodsPic;
            }

            public void setGoodsPic(String goodsPic) {
                this.goodsPic = goodsPic;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getNowPrice() {
                return nowPrice;
            }

            public void setNowPrice(String nowPrice) {
                this.nowPrice = nowPrice;
            }

            public String getPreviousPrice() {
                return previousPrice;
            }

            public void setPreviousPrice(String previousPrice) {
                this.previousPrice = previousPrice;
            }
        }

}
