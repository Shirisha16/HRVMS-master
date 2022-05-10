package com.cstech.hrvms.DataModels;

public class GetClientVendorDataModel {

    String companyName,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "GetClientVendorDataModel{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
