package com.cstech.hrvms.DataModels;

public class GetUsersDataModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String userName;

    @Override
    public String toString() {
        return "GetUsersDataModel{" +
                "id='" + id + '\'' +
                ", workLocation='" + userName + '\'' +
                '}';
    }
}
