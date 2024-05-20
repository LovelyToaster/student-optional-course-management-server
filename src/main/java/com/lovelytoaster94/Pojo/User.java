package com.lovelytoaster94.Pojo;

public class User {
    private String userName;
    private String userPassword;
    private int permissions;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPermission() {
        return permissions;
    }

    public void setPermission(int permission) {
        this.permissions = permission;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
