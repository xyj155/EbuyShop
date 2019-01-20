package com.example.commonlib.gson;

public class CouponGson {

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
