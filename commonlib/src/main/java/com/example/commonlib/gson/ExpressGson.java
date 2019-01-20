package com.example.commonlib.gson;

public class ExpressGson {

    /**
     * id : 1
     * expressName : 随机
     * expressPrice : 10.00
     */

    private int id;
    private String expressName;
    private String expressPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }
}
