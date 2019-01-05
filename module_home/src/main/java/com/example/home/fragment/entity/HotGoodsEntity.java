package com.example.home.fragment.entity;

public class HotGoodsEntity {
    private String url;
    private String name;
    private String decribe;

    public HotGoodsEntity(String url, String name, String decribe) {
        this.url = url;
        this.name = name;
        this.decribe = decribe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecribe() {
        return decribe;
    }

    public void setDecribe(String decribe) {
        this.decribe = decribe;
    }
}
