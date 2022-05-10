package com.cstech.hrvms.DataModels;

public class AddEmployeeDataModel {

     String ID,employee_Ref,client_Ref,primaryVendor_Ref,secondaryVendor_Ref,thirdVendor_Ref,HourlyRate,startDate,endDate,createdOn,lastChangedOn;

    public String getHourlyRate() {
        return HourlyRate;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setHourlyRate(String hourlyRate) {
        HourlyRate = hourlyRate;
    }

    public String getEmployee_Ref() {
        return employee_Ref;
    }

    public String getClient_Ref() {
        return client_Ref;
    }

    public void setClient_Ref(String client_Ref) {
        this.client_Ref = client_Ref;
    }

    public void setEmployee_Ref(String employee_Ref) {
        this.employee_Ref = employee_Ref;
    }

    public String getPrimaryVendor_Ref() {
        return primaryVendor_Ref;
    }

    public void setPrimaryVendor_Ref(String primaryVendor_Ref) {
        this.primaryVendor_Ref = primaryVendor_Ref;
    }

    public String getSecondaryVendor_Ref() {
        return secondaryVendor_Ref;
    }

    public void setSecondaryVendor_Ref(String secondaryVendor_Ref) {
        this.secondaryVendor_Ref = secondaryVendor_Ref;
    }

    public String getThirdVendor_Ref() {
        return thirdVendor_Ref;
    }

    public void setThirdVendor_Ref(String thirdVendor_Ref) {
        this.thirdVendor_Ref = thirdVendor_Ref;
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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastChangedOn() {
        return lastChangedOn;
    }

    public void setLastChangedOn(String lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    @Override
    public String toString() {
        return "AddEmployeeDataModel{" +
                "ID='" + ID + '\'' +
                ", employee_Ref='" + employee_Ref + '\'' +
                ", client_Ref='" + client_Ref + '\'' +
                ", primaryVendor_Ref='" + primaryVendor_Ref + '\'' +
                ", secondaryVendor_Ref='" + secondaryVendor_Ref + '\'' +
                ", thirdVendor_Ref='" + thirdVendor_Ref + '\'' +
                ", HourlyRate='" + HourlyRate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", lastChangedOn='" + lastChangedOn + '\'' +
                '}';
    }
}
