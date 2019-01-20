package com.example.commonlib.gson;

import java.util.List;

public class OrderDetailGson {

    /**
     * goods : [{"goodsName":"梅湾街秋冬新款百搭修身高领打底衫女长袖针织衫套头毛衣女羊毛衫-白色","goodsCount":1,"goodsId":1,"goodsUserId":1,"goodsPrice":"10.00","goodsPicUrl":"https://img.alicdn.com/imgextra/i4/742634735/TB2u6rgbdXXWeJjSZFvXXa6lpXa_!!742634735.jpg_430x430q90.jpg"}]
     * userAddress : {"id":1,"userId":1,"saveName":"徐易杰","saveTel":"17374131274","saveLocal":"湖南省 株洲市 秀洲区 新城街道","saveAddressDetail":"浙江省嘉兴市嘉兴院梁林校区","isDefault":"0","updateTime":"2019-01-19 13:57:22"}
     * userCoupon : [{"id":1,"couponName":"节假日优惠券","couponTotal":"100","couponReduce":"10","startTime":"2019-01-20 02:19:07","endTime":"2019-01-20 02:19:09"},{"id":2,"couponName":"国庆节优惠券","couponTotal":"150","couponReduce":"20","startTime":"2019-01-20 02:19:12","endTime":"2019-01-20 02:19:15"},{"id":3,"couponName":"会员优惠券","couponTotal":"200","couponReduce":"100","startTime":"2019-01-20 02:19:17","endTime":"2019-01-20 02:19:20"}]
     */

    private UserAddressBean userAddress;
    private List<GoodsBean> goods;
    private List<UserCouponBean> userCoupon;
    private PostFree postFree;

    public PostFree getPostFree() {
        return postFree;
    }

    public void setPostFree(PostFree postFree) {
        this.postFree = postFree;
    }

    private class PostFree {
        private String postName;
        private String postPrice;

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getPostPrice() {
            return postPrice;
        }

        public void setPostPrice(String postPrice) {
            this.postPrice = postPrice;
        }
    }

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<UserCouponBean> getUserCoupon() {
        return userCoupon;
    }

    public void setUserCoupon(List<UserCouponBean> userCoupon) {
        this.userCoupon = userCoupon;
    }

    public static class UserAddressBean {
        /**
         * id : 1
         * userId : 1
         * saveName : 徐易杰
         * saveTel : 17374131274
         * saveLocal : 湖南省 株洲市 秀洲区 新城街道
         * saveAddressDetail : 浙江省嘉兴市嘉兴院梁林校区
         * isDefault : 0
         * updateTime : 2019-01-19 13:57:22
         */

        private int id;
        private int userId;
        private String saveName;
        private String saveTel;
        private String saveLocal;
        private String saveAddressDetail;
        private String isDefault;
        private String updateTime;

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

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        public String getSaveTel() {
            return saveTel;
        }

        public void setSaveTel(String saveTel) {
            this.saveTel = saveTel;
        }

        public String getSaveLocal() {
            return saveLocal;
        }

        public void setSaveLocal(String saveLocal) {
            this.saveLocal = saveLocal;
        }

        public String getSaveAddressDetail() {
            return saveAddressDetail;
        }

        public void setSaveAddressDetail(String saveAddressDetail) {
            this.saveAddressDetail = saveAddressDetail;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class GoodsBean {
        /**
         * goodsName : 梅湾街秋冬新款百搭修身高领打底衫女长袖针织衫套头毛衣女羊毛衫-白色
         * goodsCount : 1
         * goodsId : 1
         * goodsUserId : 1
         * goodsPrice : 10.00
         * goodsPicUrl : https://img.alicdn.com/imgextra/i4/742634735/TB2u6rgbdXXWeJjSZFvXXa6lpXa_!!742634735.jpg_430x430q90.jpg
         */
        private String goodsCommonName;

        public String getGoodsCommonName() {
            return goodsCommonName;
        }

        public void setGoodsCommonName(String goodsCommonName) {
            this.goodsCommonName = goodsCommonName;
        }

        private String goodsName;
        private int goodsCount;
        private int goodsId;
        private int goodsUserId;
        private String goodsPrice;
        private String goodsPicUrl;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsUserId() {
            return goodsUserId;
        }

        public void setGoodsUserId(int goodsUserId) {
            this.goodsUserId = goodsUserId;
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
    }

    public static class UserCouponBean {
        /**
         * id : 1
         * couponName : 节假日优惠券
         * couponTotal : 100
         * couponReduce : 10
         * startTime : 2019-01-20 02:19:07
         * endTime : 2019-01-20 02:19:09
         */

        private int id;
        private String couponName;
        private String couponTotal;
        private String couponReduce;
        private String startTime;
        private String endTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponTotal() {
            return couponTotal;
        }

        public void setCouponTotal(String couponTotal) {
            this.couponTotal = couponTotal;
        }

        public String getCouponReduce() {
            return couponReduce;
        }

        public void setCouponReduce(String couponReduce) {
            this.couponReduce = couponReduce;
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
}
