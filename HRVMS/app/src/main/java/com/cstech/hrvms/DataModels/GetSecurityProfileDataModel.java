package com.cstech.hrvms.DataModels;

public class GetSecurityProfileDataModel {
    int id;
    String securityProfile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecurityProfile() {
        return securityProfile;
    }

    public void setSecurityProfile(String securityProfile) {
        this.securityProfile = securityProfile;
    }

    @Override
    public String toString() {
        return "GetSecurityProfileDataModel{" +
                "id=" + id +
                ", securityProfile='" + securityProfile + '\'' +
                '}';
    }
}
