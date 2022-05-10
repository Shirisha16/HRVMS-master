package com.cstech.hrvms.DataModels;

public class ClientInvoiceDataModel {
    int id;
    String invoiceNo,invoiceDate,invoiceAmout,username,companyName;

    public void setInvoiceAmout(String invoiceAmout) {
        this.invoiceAmout = invoiceAmout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceAmout() {
        return invoiceAmout;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "ClientInvoiceDataModel{" +
                "id=" + id +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", invoiceAmout='" + invoiceAmout + '\'' +
                ", username='" + username + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
