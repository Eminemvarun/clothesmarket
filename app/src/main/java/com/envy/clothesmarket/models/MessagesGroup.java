package com.envy.clothesmarket.models;


public class MessagesGroup {

    String userOneID;
    String userTwoId;
    String title;

    public MessagesGroup() {
    }

    public MessagesGroup(String userOneID, String userTwoId, String title) {
        this.userOneID = userOneID;
        this.userTwoId = userTwoId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserOneID() {
        return userOneID;
    }

    public void setUserOneID(String userOneID) {
        this.userOneID = userOneID;
    }

    public String getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(String userTwoId) {
        this.userTwoId = userTwoId;
    }
}
