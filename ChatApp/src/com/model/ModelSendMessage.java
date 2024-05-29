package com.model;

import com.app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class ModelSendMessage {

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ModelFileSender getFile() {
        return file;
    }

    public void setFile(ModelFileSender file) {
        this.file = file;
    }

    private MessageType messageType;
    private int fromUserID;
    private int toUserID;
    private String text;
    private ModelFileSender file;

    public ModelSendMessage() {
    }

    public ModelSendMessage(MessageType messageType, int fromUserID, int toUserID, String text) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            if (messageType == MessageType.FILE || messageType == MessageType.IMAGE) {
                json.put("text", file.getFileExtensions());
            } else {
                json.put("text", text);
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
