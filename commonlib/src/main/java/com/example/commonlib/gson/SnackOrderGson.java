package com.example.commonlib.gson;

public class SnackOrderGson {

    /**
     * goodsId : 8
     * cid : 179
     * createTime : 2019-02-19 04:41:52
     * orderNum : 201902190441528854341225
     * status : 1
     * fid : 8
     * foodName : 网红辣条（九元装）
     * foodPicture : http://syxfoods.oss-cn-beijing.aliyuncs.com/网红辣条.jpg?x-oss-process=style/press
     */

    private int goodsId;
    private int cid;
    private String createTime;
    private String orderNum;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String status;
    private int fid;
    private String foodName;
    private String foodPicture;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }
}
