package com.cstech.hrvms.DataModels;

public class ChangePasswordDataModel {
    String id,LoginPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginPassword() {
        return LoginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        LoginPassword = loginPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordDataModel{" +
                "id='" + id + '\'' +
                ", LoginPassword='" + LoginPassword + '\'' +
                '}';
    }
}
