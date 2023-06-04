package com.example.project_ver1.ui.all_products;

public class AllProducts {

    public AllProducts(){}

    public AllProducts(String name, String bar_code, String desc, String id, String image) {
        this.name = name;
        this.bar_code = bar_code;
        this.desc = desc;
        this.id = id;
        this.image = image;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
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

    private String id;
    // Variable to store data corresponding
    // to firstname keyword in database
    private String name;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String bar_code;

    private String image;

    private String desc;
}
