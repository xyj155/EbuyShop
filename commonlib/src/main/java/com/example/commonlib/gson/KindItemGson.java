package com.example.commonlib.gson;

import java.io.Serializable;

public class KindItemGson implements Serializable {


    @Override
    public String toString() {
        return "KindItemGson{" +
                "id=" + id +
                ", p_id=" + p_id +
                ", kind_name='" + kind_name + '\'' +
                ", kind_url=" + kind_url +
                '}';
    }

    /**
     * id : 1
     * p_id : 0
     * kind_name : 推荐类别
     * kind_url : null
     */

    private int id;
    private int p_id;
    private String kind_name;
    private Object kind_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }

    public Object getKind_url() {
        return kind_url;
    }

    public void setKind_url(Object kind_url) {
        this.kind_url = kind_url;
    }
}
