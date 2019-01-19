package com.example.commonlib.gson;


public class ShopCarGson {

    /**
     * id : 2
     * goodsId : 1
     * goodsName : 梅湾街秋冬新款百搭修身高领打底衫女长袖针织衫套头毛衣女羊毛衫-灰色
     * goodsPrice : 50.00
     * goodsPicUrl : https://img.alicdn.com/imgextra/i4/742634735/TB2z8rkaXXXXXaQXXXXXXXXXXXX_!!742634735.jpg_430x430q90.jpg
     * goodsStock : 40
     * count : 2
     * goodsShopName : FILA斐乐女羽绒马甲2018冬季新品运动休闲轻质保暖连帽运动马夹女
     */

    private int id;
    private int goodsId;
    private String goodsName;
    private String goodsPrice;
    private String goodsPicUrl;
    private String goodsStock;
    private int count;
    private String goodsShopName;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGoodsShopName() {
        return goodsShopName;
    }

    public void setGoodsShopName(String goodsShopName) {
        this.goodsShopName = goodsShopName;
    }
}
