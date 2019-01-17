package com.example.commonlib.gson;

public class GoodsStyleGson {

    /**
     * id : 1
     * goodsId : 1
     * goodsName : 梅湾街秋冬新款百搭修身高领打底衫女长袖针织衫套头毛衣女羊毛衫-白色
     * goodsPrice : 10.00
     * goodsPicUrl : https://img.alicdn.com/imgextra/i4/742634735/TB2u6rgbdXXWeJjSZFvXXa6lpXa_!!742634735.jpg_430x430q90.jpg
     * goodsStock : 30
     * goodsStyle : null
     */

    private int id;
    private int goodsId;
    private String goodsName;
    private String goodsPrice;
    private String goodsPicUrl;
    private String goodsStock;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private Object goodsStyle;

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

    public Object getGoodsStyle() {
        return goodsStyle;
    }

    public void setGoodsStyle(Object goodsStyle) {
        this.goodsStyle = goodsStyle;
    }
}
