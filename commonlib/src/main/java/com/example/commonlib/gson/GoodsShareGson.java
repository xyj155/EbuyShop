package com.example.commonlib.gson;

public class GoodsShareGson {

    /**
     * id : 1
     * uId : 1
     * goodsName : 我的电脑
     * userComment : 好东西呀
     * updateTime : null
     * shareVideo : 1515
     * user : {"username":"17374131273","avatar":null}
     * count : 1
     */

    private int id;
    private int uId;
    private String goodsName;
    private String userComment;
    private Object updateTime;
    private String shareVideo;
    private UserGson user;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getShareVideo() {
        return shareVideo;
    }

    public void setShareVideo(String shareVideo) {
        this.shareVideo = shareVideo;
    }

    public UserGson getUser() {
        return user;
    }

    public void setUser(UserGson user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
