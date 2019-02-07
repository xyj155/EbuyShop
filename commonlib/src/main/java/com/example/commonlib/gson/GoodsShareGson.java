package com.example.commonlib.gson;

import java.util.List;

public class GoodsShareGson {

    /**
     * id : 27
     * pId : 41
     * uId : 1
     * comment : 你是男是女说你呢
     * updateTime : 2019-02-05 22:21:54
     * evaluateRank : 好评
     * user : {"username":"17374131273","avatar":"/StuShop/public/avatar/13527491943.jpg"}
     * goods : {"id":41,"goodsId":38,"goodsName":"16G DDR4 2133Mhz ECC RDIMM","goodsPrice":"1399.00","goodsPicUrl":"https://img.alicdn.com/imgextra/i3/2781172225/TB2GY.afQomBKNjSZFqXXXtqVXa_!!2781172225.jpg_60x60q90.jpg","goodsStock":"100"}
     */
    private List<String> goodsPicture;

    public List<String> getGoodsPicture() {
        return goodsPicture;
    }
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setGoodsPicture(List<String> goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    private int id;
    private String pId;
    private int uId;
    private String comment;
    private String updateTime;
    private String evaluateRank;
    private UserBean user;
    private GoodsBean goods;
private int cid;

    public String getpId() {
        return pId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEvaluateRank() {
        return evaluateRank;
    }

    public void setEvaluateRank(String evaluateRank) {
        this.evaluateRank = evaluateRank;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class UserBean {
        /**
         * username : 17374131273
         * avatar : /StuShop/public/avatar/13527491943.jpg
         */

        private String username;
        private String avatar;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class GoodsBean {
        /**
         * id : 41
         * goodsId : 38
         * goodsName : 16G DDR4 2133Mhz ECC RDIMM
         * goodsPrice : 1399.00
         * goodsPicUrl : https://img.alicdn.com/imgextra/i3/2781172225/TB2GY.afQomBKNjSZFqXXXtqVXa_!!2781172225.jpg_60x60q90.jpg
         * goodsStock : 100
         */

        private int id;
        private int goodsId;
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

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
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
}
