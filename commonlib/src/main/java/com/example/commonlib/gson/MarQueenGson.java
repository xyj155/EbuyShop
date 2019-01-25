package com.example.commonlib.gson;

public class MarQueenGson {

    @Override
    public String toString() {
        return "MarQueenGson{" +
                "id=" + id +
                ", mar_text='" + mar_text + '\'' +
                ", url=" + url +
                ", packName=" + packName +
                ", goodId=" + goodId +
                '}';
    }

    /**
     * id : 2
     * mar_text : 测试2
     * url : null
     * packName : null
     * goodId : null
     */

    private int id;
    private String mar_text;
    private Object url;
    private Object packName;
    private Object goodId;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMar_text() {
        return mar_text;
    }

    public void setMar_text(String mar_text) {
        this.mar_text = mar_text;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getPackName() {
        return packName;
    }

    public void setPackName(Object packName) {
        this.packName = packName;
    }

    public Object getGoodId() {
        return goodId;
    }

    public void setGoodId(Object goodId) {
        this.goodId = goodId;
    }
}
