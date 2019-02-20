package com.example.commonlib.gson;

public class PopAdvertisementGson {


    /**
     * id : 1
     * imgUrl : https://img.zcool.cn/community/0102b25bed0854a80121ab5d0af271.png@2o.png
     * webUrl : https://img.zcool.cn/community/0102b25bed0854a80121ab5d0af271.png@2o.png
     * isShow : null
     * packageUrl : null
     * goodsId : null
     */

    private int id;
    private String imgUrl;
    private String webUrl;
    private Object isShow;
    private Object packageUrl;
    private Object goodsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Object getIsShow() {
        return isShow;
    }

    public void setIsShow(Object isShow) {
        this.isShow = isShow;
    }

    public Object getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(Object packageUrl) {
        this.packageUrl = packageUrl;
    }

    public Object getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Object goodsId) {
        this.goodsId = goodsId;
    }
}
