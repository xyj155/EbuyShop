package com.example.commonlib.gson;

import java.util.List;

public class GoodsCommentGson {

    /**
     * id : 1
     * pId : 7
     * uId : 1
     * comment : 好的东西
     * updateTime : 2019-01-27 17:06:28
     * picture : ["https://gd4.alicdn.com/imgextra/i4/3034056874/O1CN0120eL2226KXJOcyI_!!3034056874.jpg_400x400.jpg_.webp"]
     * goodsName : 游戏本8代i7/16G/1T+256G/1060
     * user : {"username":"17374131273","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1554278126,1318599153&fm=27&gp=0.jpg"}
     */

    private int id;
    private String pId;
    private int uId;
    private String comment;
    private String updateTime;
    private String goodsName;
    private UserBean user;
    private List<String> picture;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public static class UserBean {
        /**
         * username : 17374131273
         * avatar : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1554278126,1318599153&fm=27&gp=0.jpg
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
}
