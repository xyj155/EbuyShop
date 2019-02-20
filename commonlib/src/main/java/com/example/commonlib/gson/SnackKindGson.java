package com.example.commonlib.gson;

public class SnackKindGson {

    @Override
    public String toString() {
        return "SnackKindGson{" +
                "id=" + id +
                ", kindName='" + kindName + '\'' +
                '}';
    }

    /**
     * id : 1
     * kindName : 辣条
     */


    private int id;
    private String kindName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
}
