package com.example.ecommerceapp.Model;

public class OnBoardScreenComponents {
    String title;
    String description;
    int image;

    public OnBoardScreenComponents(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
