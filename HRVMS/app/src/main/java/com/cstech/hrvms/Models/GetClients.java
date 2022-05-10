package com.cstech.hrvms.Models;

public class GetClients {

    private String id,startDate,endDate,client,primaryVendor,secondVendors,thirdVendors,hourlyRate,clientId,primaryVendorId,secondaryVendorId,thirdVendorId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPrimaryVendorId() {
        return primaryVendorId;
    }

    public void setPrimaryVendorId(String primaryVendorId) {
        this.primaryVendorId = primaryVendorId;
    }

    public String getSecondaryVendorId() {
        return secondaryVendorId;
    }

    public void setSecondaryVendorId(String secondaryVendorId) {
        this.secondaryVendorId = secondaryVendorId;
    }

    public String getThirdVendorId() {
        return thirdVendorId;
    }

    public void setThirdVendorId(String thirdVendorId) {
        this.thirdVendorId = thirdVendorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPrimaryVendor() {
        return primaryVendor;
    }

    public void setPrimaryVendor(String primaryVendor) {
        this.primaryVendor = primaryVendor;
    }

    public String getSecondVendors() {
        return secondVendors;
    }

    public void setSecondVendors(String secondVendors) {
        this.secondVendors = secondVendors;
    }

    public String getThirdVendors() {
        return thirdVendors;
    }

    public void setThirdVendors(String thirdVendors) {
        this.thirdVendors = thirdVendors;
    }

    @Override
    public String toString() {
        return "GetClients{" +
                "id='" + id + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", client='" + client + '\'' +
                ", primaryVendor='" + primaryVendor + '\'' +
                ", secondVendors='" + secondVendors + '\'' +
                ", thirdVendors='" + thirdVendors + '\'' +
                ", hourlyRate='" + hourlyRate + '\'' +
                ", clientId='" + clientId + '\'' +
                ", primaryVendorId='" + primaryVendorId + '\'' +
                ", secondaryVendorId='" + secondaryVendorId + '\'' +
                ", thirdVendorId='" + thirdVendorId + '\'' +
                '}';
    }
}
