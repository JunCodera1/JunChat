package com.model;

import com.app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class ModelReceiveMessage {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ModelReceiveImage getDataImage() {
        return dataImage;
    }

    public void setDataImage(ModelReceiveImage dataImage) {
        this.dataImage = dataImage;
    }
    
    public ModelReceiveFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(ModelReceiveFile dataFile) {
        this.dataFile = dataFile;
    }

    public ModelReceiveMessage(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            if (!obj.isNull("dataImage")) {
                dataImage = new ModelReceiveImage(obj.get("dataImage"));
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    private MessageType messageType;
    private int fromUserID;
    private String text;
    private ModelReceiveImage dataImage;
    private ModelReceiveFile dataFile;

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            if (dataImage != null) {
                json.put("dataImage", dataImage.toJsonObject());
            }
            if (dataFile != null){
                json.put("dataFile", dataFile.toJsonObject());
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
