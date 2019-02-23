package com.example.commonlib.gson;

import java.util.List;

public class OrderDetailGson {

    /**
     * goods : [{"goodsName":"火影忍者 剧场版 宇智波佐助 手办 蓝色","goodsCommonName":"火影忍者 剧场版 宇智波佐助 手办","goodsCount":4,"goodsId":3,"goodsUserId":1,"goodsPrice":"20.00","goodsPicUrl":"https://gd3.alicdn.com/imgextra/i3/126330677/TB2EoREX._C11Bjy1zeXXXtGpXa_!!126330677.jpg"},{"goodsName":"游戏本8代i5/8G/1T+256GB/1060","goodsCommonName":"Xiaomi/小米 游戏本 八代增强版 I7 1060独显15.6吃鸡笔记本电脑","goodsCount":1,"goodsId":8,"goodsUserId":1,"goodsPrice":"6899.00","goodsPicUrl":"https://gd1.alicdn.com/imgextra/i1/3034056874/O1CN0120eL1xiztW8fEEQ_!!3034056874.jpg"}]
     * userAddress : {"id":3,"userId":1,"saveName":"徐易杰","saveTel":"173711312","saveLocal":"湖北省 鄂州市 枞阳县","saveAddressDetail":"嘉兴学院梁林校区","isDefault":"1","updateTime":null}
     * userCoupon : [{"id":2,"couponName":"满减优惠券","couponTotal":"100","couponReduce":"10","startTime":"2019-01-15 00:00:00","endTime":"2019-01-24 00:00:00"},{"id":3,"couponName":"节日优惠券","couponTotal":"200","couponReduce":"10","startTime":"2019-01-01 00:00:00","endTime":"2019-01-23 00:00:00"},{"id":2,"couponName":"满减优惠券","couponTotal":"100","couponReduce":"10","startTime":"2019-01-15 00:00:00","endTime":"2019-01-24 00:00:00"}]
     * postFree : {"postName":"随机","postPrice":10}
     */

    private UserAddressBean userAddress;
    private PostFreeBean postFree;
    private List<GoodsBean> goods;
    private List<UserCouponBean> userCoupon;

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

    public PostFreeBean getPostFree() {
        return postFree;
    }

    public void setPostFree(PostFreeBean postFree) {
        this.postFree = postFree;
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
         * id : 3
         * userId : 1
         * saveName : 徐易杰
         * saveTel : 173711312
         * saveLocal : 湖北省 鄂州市 枞阳县
         * saveAddressDetail : 嘉兴学院梁林校区
         * isDefault : 1
         * updateTime : null
         */

        private int id;
        private int userId;
        private String saveName;
        private String saveTel;
        private String saveLocal;
        private String saveAddressDetail;
        private String isDefault;
        private Object updateTime;

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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class PostFreeBean {
        /**
         * postName : 随机
         * postPrice : 10
         */

        private String postName;
        private int postPrice;

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public int getPostPrice() {
            return postPrice;
        }

        public void setPostPrice(int postPrice) {
            this.postPrice = postPrice;
        }
    }

    public static class GoodsBean {
        /**
         * goodsName : 火影忍者 剧场版 宇智波佐助 手办 蓝色
         * goodsCommonName : 火影忍者 剧场版 宇智波佐助 手办
         * goodsCount : 4
         * goodsId : 3
         * goodsUserId : 1
         * goodsPrice : 20.00
         * goodsPicUrl : https://gd3.alicdn.com/imgextra/i3/126330677/TB2EoREX._C11Bjy1zeXXXtGpXa_!!126330677.jpg
         */
        private String orderNum;
private String isDiscount;

        public String getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(String isDiscount) {
            this.isDiscount = isDiscount;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        private String goodsName;
        private String goodsCommonName;
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

        public String getGoodsCommonName() {
            return goodsCommonName;
        }

        public void setGoodsCommonName(String goodsCommonName) {
            this.goodsCommonName = goodsCommonName;
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
         * id : 2
         * couponName : 满减优惠券
         * couponTotal : 100
         * couponReduce : 10
         * startTime : 2019-01-15 00:00:00
         * endTime : 2019-01-24 00:00:00
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
