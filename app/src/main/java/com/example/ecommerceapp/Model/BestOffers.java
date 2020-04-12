package com.example.ecommerceapp.Model;

public class BestOffers {
    private String name;
    private String imageUrl;
    private String rating;
    private String before_price;
    private String after_price;

    public BestOffers(String name, String imageUrl, String rating, String before_price, String after_price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.before_price = before_price;
        this.after_price = after_price;
    }

    public BestOffers() {
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getBefore_price() {
        return before_price;
    }

    public String getAfter_price() {
        return after_price;
    }
}
