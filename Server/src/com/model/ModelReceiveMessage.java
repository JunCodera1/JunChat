package com.model;

public class ModelReceiveMessage {

    int fromUserID;
    String text;

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }

    public ModelReceiveMessage(Object json) {
       
    }

    public ModelReceiveMessage(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }
}
