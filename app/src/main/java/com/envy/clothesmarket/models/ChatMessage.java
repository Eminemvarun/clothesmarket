package com.envy.clothesmarket.models;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessage {
    String senderId;
    String messageText;
    long time;
    boolean isMine;

    public ChatMessage(String senderId, String messageText, long time) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.time = time;
    }

    public ChatMessage() {
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMine() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().equals(senderId) ;
    }

    public void setMine(boolean mine) {
        this.isMine = mine;
    }

    public String getTimeString(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(this.getTime());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }
}
