package com.example.commonlib.gson;

public class MemberGson {

    /**
     * id : 1
     * vipName : 会员等级1
     * vipPrice : 10.00
     */

    private int id;
    private String vipName;
    private String vipPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }
}
