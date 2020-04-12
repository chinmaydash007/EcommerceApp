package com.example.ecommerceapp.Model;

public class Furniture {
    private String name;
    private String price;
    private String description;
    private String type;
    private Boolean available;
    private String imageUrl;
    String ducumentId;

    public String getDucumentId() {
        return ducumentId;
    }

    public void setDucumentId(String ducumentId) {
        this.ducumentId = ducumentId;
    }

    public Furniture() {
    }

    public Furniture(String name, String price, String description, String type, Boolean available, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.available = available;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
