package com.envy.clothesmarket.models;

import com.google.firebase.Timestamp;

//Entity Class Clothes
public class Clothes {
    private String title;
    private String description;
    private String imageUrl;
    private String price;
    private String userId;
    private Timestamp timeAdded;
    private String userName;

    public Clothes(String title, String description, String imageUrl, String price, String userId, Timestamp timeAdded, String userName) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.userId = userId;
        this.timeAdded = timeAdded;
        this.userName = userName;
    }

    public Clothes(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
