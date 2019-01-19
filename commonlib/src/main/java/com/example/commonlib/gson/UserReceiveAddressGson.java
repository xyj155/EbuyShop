package com.example.commonlib.gson;


public class UserReceiveAddressGson {

    /**
     * id : 1
     * userId : 1
     * saveName : 徐易杰
     * saveTel : 17374131274
     * saveLocal : 湖南省 株洲市 秀洲区 新城街道
     * saveAddressDetail : 浙江省嘉兴市嘉兴学院梁林校区
     * tag : 学校
     * isDefault : 1
     */

    private int id;
    private int userId;
    private String saveName;
    private String saveTel;
    private String saveLocal;
    private String saveAddressDetail;
    private String tag;
    private String isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveTel() {
        return saveTel;
    }

    public void setSaveTel(String saveTel) {
        this.saveTel = saveTel;
    }

    public String getSaveLocal() {
        return saveLocal;
    }

    public void setSaveLocal(String saveLocal) {
        this.saveLocal = saveLocal;
    }

    public String getSaveAddressDetail() {
        return saveAddressDetail;
    }

    public void setSaveAddressDetail(String saveAddressDetail) {
        this.saveAddressDetail = saveAddressDetail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
