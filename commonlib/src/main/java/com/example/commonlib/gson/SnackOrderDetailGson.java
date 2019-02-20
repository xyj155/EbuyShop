package com.example.commonlib.gson;

import java.util.List;

public class SnackOrderDetailGson {

    /**
     * snackList : [{"foodPicture":"https://syxfoods.oss-cn-beijing.aliyuncs.com/网红辣条8元装.png?x-oss-process=style/press","userId":596,"foodName":"网红辣条（八元装）","foodsPrice":"8.00","foodsSize":"1袋","foodsTaste":"辣皮 香辣味","foodsId":7,"tasteId":22,"count":1},{"foodPicture":"http://syxfoods.oss-cn-beijing.aliyuncs.com/网红辣条.jpg?x-oss-process=style/press","userId":596,"foodName":"网红辣条（九元装）","foodsPrice":"9.00","foodsSize":"1袋","foodsTaste":"麦薄小辣片 变态辣","foodsId":8,"tasteId":32,"count":4},{"foodPicture":"http://syxfoods.oss-cn-beijing.aliyuncs.com/网红辣条.jpg?x-oss-process=style/press","userId":596,"foodName":"网红辣条（九元装）","foodsPrice":"9.00","foodsSize":"1袋","foodsTaste":"口红辣条 超辣味","foodsId":8,"tasteId":37,"count":1},{"foodPicture":"http://syxfoods.oss-cn-beijing.aliyuncs.com/陈氏泡菜 豇豆18元装.png?x-oss-process=style/press","userId":596,"foodName":"陈氏泡菜 豇豆","foodsPrice":"18.00","foodsSize":"500g","foodsTaste":"重辣","foodsId":42,"tasteId":136,"count":1}]
     * userAddress : {"id":20,"userId":596,"saveName":"徐易杰","saveTel":"646464646","saveLocal":"安徽省 安庆市 枞阳县","saveAddressDetail":"你自己找继续继续惊喜惊喜你那","isDefault":"0","updateTime":null}
     */

    private UserAddressBean userAddress;
    private List<SnackListBean> snackList;
    private List<ExpessTrace> express;

    public List<ExpessTrace> getExpress() {
        return express;
    }

    public void setExpress(List<ExpessTrace> express) {
        this.express = express;
    }

    public static class ExpessTrace {

        /**
         * AcceptStation : 上海市【上海奉贤东区一部】，【张国刚/18918353606】已揽收
         * AcceptTime : 2019-02-15 17:26:50
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        @Override
        public String toString() {
            return "ExpessTrace{" +
                    "AcceptStation='" + AcceptStation + '\'' +
                    ", AcceptTime='" + AcceptTime + '\'' +
                    '}';
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }

    public UserAddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddressBean userAddress) {
        this.userAddress = userAddress;
    }

    public List<SnackListBean> getSnackList() {
        return snackList;
    }

    public void setSnackList(List<SnackListBean> snackList) {
        this.snackList = snackList;
    }

    public static class UserAddressBean {
        /**
         * id : 20
         * userId : 596
         * saveName : 徐易杰
         * saveTel : 646464646
         * saveLocal : 安徽省 安庆市 枞阳县
         * saveAddressDetail : 你自己找继续继续惊喜惊喜你那
         * isDefault : 0
         * updateTime : null
         */

        private int id;
        private int userId;
        private String saveName;
        private String saveTel;
        private String saveLocal;
        private String saveAddressDetail;
        private String isDefault;
        private Object updateTime;

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

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class SnackListBean {
        private String message;

        @Override
        public String toString() {
            return "SnackListBean{" +
                    "message='" + message + '\'' +
                    ", postNum='" + postNum + '\'' +
                    ", foodPicture='" + foodPicture + '\'' +
                    ", userId=" + userId +
                    ", foodName='" + foodName + '\'' +
                    ", foodsPrice='" + foodsPrice + '\'' +
                    ", foodsSize='" + foodsSize + '\'' +
                    ", foodsTaste='" + foodsTaste + '\'' +
                    ", foodsId=" + foodsId +
                    ", tasteId=" + tasteId +
                    ", count=" + count +
                    '}';
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        private String postNum;

        public String getPostNum() {
            return postNum;
        }

        public void setPostNum(String postNum) {
            this.postNum = postNum;
        }

        /**
         * foodPicture : https://syxfoods.oss-cn-beijing.aliyuncs.com/网红辣条8元装.png?x-oss-process=style/press
         * userId : 596
         * foodName : 网红辣条（八元装）
         * foodsPrice : 8.00
         * foodsSize : 1袋
         * foodsTaste : 辣皮 香辣味
         * foodsId : 7
         * tasteId : 22
         * count : 1
         */

        private String foodPicture;
        private int userId;
        private String foodName;
        private String foodsPrice;
        private String foodsSize;
        private String foodsTaste;
        private int foodsId;
        private int tasteId;
        private int count;

        public String getFoodPicture() {
            return foodPicture;
        }

        public void setFoodPicture(String foodPicture) {
            this.foodPicture = foodPicture;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodsPrice() {
            return foodsPrice;
        }

        public void setFoodsPrice(String foodsPrice) {
            this.foodsPrice = foodsPrice;
        }

        public String getFoodsSize() {
            return foodsSize;
        }

        public void setFoodsSize(String foodsSize) {
            this.foodsSize = foodsSize;
        }

        public String getFoodsTaste() {
            return foodsTaste;
        }

        public void setFoodsTaste(String foodsTaste) {
            this.foodsTaste = foodsTaste;
        }

        public int getFoodsId() {
            return foodsId;
        }

        public void setFoodsId(int foodsId) {
            this.foodsId = foodsId;
        }

        public int getTasteId() {
            return tasteId;
        }

        public void setTasteId(int tasteId) {
            this.tasteId = tasteId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
