package com.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelReceiveFile {

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ModelReceiveFile(int fileID, String file, int width, int height) {
        this.fileID = fileID;
        this.file = file;
        this.width = width;
        this.height = height;
    }

    public ModelReceiveFile(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fileID = obj.getInt("fileID");
            file = obj.getString("file");
            width = obj.getInt("width");
            height = obj.getInt("height");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    private int fileID;
    private String file;
    private int width;
    private int height;

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("file", file);
            json.put("width", width);
            json.put("height", height);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
