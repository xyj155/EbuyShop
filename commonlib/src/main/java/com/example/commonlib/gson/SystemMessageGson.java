package com.example.commonlib.gson;

public class SystemMessageGson {

    /**
     * id : 2
     * message : 测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送测试推送
     * updateTime : 2019-02-07 13:42:41
     * pictureUrl : https://img.zcool.cn/community/0196cd5541d6960000011541b99c93.jpg@2o.jpg
     * title : 测试推送
     */

    private int id;
    private String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    private String message;
    private String updateTime;
    private String pictureUrl;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
