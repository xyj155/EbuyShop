package com.example.commonlib.gson;

public class HotPurseActivityGson {

    /**
     * id : 1
     * activityTitle : Top热卖
     * activityDesc : 好东西
     * activityPicurl : null
     * activityUrl : null
     */

    private int id;
    private String activityTitle;
    private String activityDesc;
    private String activityPicurl;
    private String activityUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public String getActivityPicurl() {
        return activityPicurl;
    }

    public void setActivityPicurl(String activityPicurl) {
        this.activityPicurl = activityPicurl;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }


}
