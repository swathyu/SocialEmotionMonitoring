package com.example.futuro.socialemotionmining;

/**
 * Created by futuro on 07-02-2018.
 */

public class Appmodel {
    String appname,desc,cat;
    Byte[]img;
    public String getAppname() {
        return appname;
    }
    public void setAppname(String appname) {
        this.appname = appname;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCat() {
        return cat;
    }
    public void setCat(String cat) {
        this.cat = cat;
    }
    public Byte[] getImg() {
        return img;
    }
    public void setImg(Byte[] img) {
        this.img = img;
    }
}
