package com.example.commonlib.gson;

public class PopAdvertisementGson {

    /**
     * id : 1
     * imgUrl : https://img.zcool.cn/community/0102b25bed0854a80121ab5d0af271.png@2o.png
     * webUrl : https://img.zcool.cn/community/0102b25bed0854a80121ab5d0af271.png@2o.png
     */

    private int id;
    private String imgUrl;
    private String webUrl;

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
}
