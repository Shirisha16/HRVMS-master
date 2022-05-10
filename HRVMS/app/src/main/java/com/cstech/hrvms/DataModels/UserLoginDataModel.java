package com.cstech.hrvms.DataModels;

public class UserLoginDataModel {
    String designation;
    int id;
    String isActive;
    String country;

    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "UserLoginDataModel{" +
                "id='" + id + '\'' +
                ", designation='" + designation + '\'' +
                ", isActive='" + isActive + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
