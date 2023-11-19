package com.example.project_ver1;

public class AllCodes {

    public AllCodes(){}

    public AllCodes(String brand, String code, String desc, String id, String image, String link, String expires) {
        this.brand = brand;
        this.code = code;
        this.desc = desc;
        this.id = id;
        this.image = image;
        this.link = link;
        this.expires = expires;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand =brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {return link;}

    public void setLink(String link) {this.link = link;}

    public String getExpires() {return expires;}

    public void setExpires(String expires) {this.expires = expires;}

    private String id;
    // Variable to store data corresponding
    // to firstname keyword in database
    private String brand;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String code;

    private String image;

    private String desc;

    private String link;

    private String expires;
}
