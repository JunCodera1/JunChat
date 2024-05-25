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

    public ModelRegister() {
    }

    public ModelRegister(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }    
    
    String password; 
    String userName;
}
