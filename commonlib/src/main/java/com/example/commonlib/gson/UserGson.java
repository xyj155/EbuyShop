package com.example.commonlib.gson;

public class UserGson {


    /**
     * id : 545
     * username : 7897897
     * password : 312
     * avatar :
     * qqtoken :
     * wbtoken :
     * telphone : 3213
     * location :
     * lastLoginTIme :
     * trueName :
     * createTime :
     * status :
     * userLevel :
     * userToken : 9bd5ee6fe55aaeb673025dbcb8f939c1
     * school : 3132
     * age : 31231
     * sex : 312
     */

    private int id;
    private String username;
    private String vipRank;
    private String imToken;

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getVipRank() {
        return vipRank;
    }

    public void setVipRank(String vipRank) {
        this.vipRank = vipRank;
    }

    @Override
    public String toString() {
        return "UserGson{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", qqtoken='" + qqtoken + '\'' +
                ", wbtoken='" + wbtoken + '\'' +
                ", telphone='" + telphone + '\'' +
                ", location='" + location + '\'' +
                ", lastLoginTIme='" + lastLoginTIme + '\'' +
                ", trueName='" + trueName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status='" + status + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", userToken='" + userToken + '\'' +
                ", school='" + school + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    private String password;
    private String avatar;
    private String qqtoken;
    private String wbtoken;
    private String telphone;
    private String location;
    private String lastLoginTIme;
    private String trueName;
    private String createTime;
    private String status;
    private String userLevel;
    private String userToken;
    private String school;
    private int age;
    private String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getQqtoken() {
        return qqtoken;
    }

    public void setQqtoken(String qqtoken) {
        this.qqtoken = qqtoken;
    }

    public String getWbtoken() {
        return wbtoken;
    }

    public void setWbtoken(String wbtoken) {
        this.wbtoken = wbtoken;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLastLoginTIme() {
        return lastLoginTIme;
    }

    public void setLastLoginTIme(String lastLoginTIme) {
        this.lastLoginTIme = lastLoginTIme;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
