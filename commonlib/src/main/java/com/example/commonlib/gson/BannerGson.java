package com.example.commonlib.gson;

public class BannerGson {

    /**
     * id : 1
     * img_url : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541219313407&di=63bc512c0a6b5ca99a10fcf3b9d1c195&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F56%2F56%2F46858PICntJ_1024.jpg
     * web_url : http://120.79.5.238/SchoolShop/public/index.php/admin/Index/index
     * address : 嘉兴
     */

    private int id;
    private String img_url;
    private String web_url;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
