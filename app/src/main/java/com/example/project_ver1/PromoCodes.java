package com.example.project_ver1;

public class PromoCodes {
    // Variable to store data corresponding
    // to firstname keyword in database
    private String brand;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String code;

    // Variable to store data corresponding
    // to age keyword in database
    private String expires;

    private String image;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public PromoCodes() {}

    // Getter and setter method
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
}
