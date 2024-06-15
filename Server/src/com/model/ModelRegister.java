package com.model;

public class ModelRegister {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ModelRegister(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    

    public ModelRegister() {
    }

    private String userName;
    private String password;
}
