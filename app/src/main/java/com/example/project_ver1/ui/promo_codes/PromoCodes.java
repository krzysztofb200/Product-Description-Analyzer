package com.example.project_ver1.ui.promo_codes;

public class PromoCodes {
    private String id;
    private String brand;
    private String code;
    private String expires;
    private String image;
    private String desc;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public PromoCodes() {}

    // Getter and setter method
    public String getID() {return id;}
    public void setID(String id) {this.id = id;}
    public String getBrand()
    {
        return brand;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getExpires()
    {
        return expires;
    }
    public void setExpires(String expires)
    {
        this.expires = expires;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}
}
