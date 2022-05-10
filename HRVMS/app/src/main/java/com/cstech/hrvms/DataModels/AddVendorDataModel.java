package com.cstech.hrvms.DataModels;

public class AddVendorDataModel {

    String RecordType,CompanyName,Street,City,State,Zip,Country,Phone,Fax,Email,ContactName,ContactPhone,ContactEmail,AccountsContactName,AccountsContactPhone,AccountsContactEmail,CreatedOn,LastChangedOn;


    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRecordType() {
        return RecordType;
    }

    public void setRecordType(String recordType) {
        RecordType = recordType;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getAccountsContactName() {
        return AccountsContactName;
    }

    public void setAccountsContactName(String accountsContactName) {
        AccountsContactName = accountsContactName;
    }

    public String getAccountsContactPhone() {
        return AccountsContactPhone;
    }

    public void setAccountsContactPhone(String accountsContactPhone) {
        AccountsContactPhone = accountsContactPhone;
    }

    public String getAccountsContactEmail() {
        return AccountsContactEmail;
    }

    public void setAccountsContactEmail(String accountsContactEmail) {
        AccountsContactEmail = accountsContactEmail;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getLastChangedOn() {
        return LastChangedOn;
    }

    public void setLastChangedOn(String lastChangedOn) {
        LastChangedOn = lastChangedOn;
    }


    @Override
    public String toString() {
        return "AddVendorDataModel{" +
                "RecordType='" + RecordType + '\'' +
                "CompanyName='" + CompanyName + '\'' +
                "Street='" + Street + '\'' +
                "City='" + City + '\'' +
                "State='" + State + '\'' +
                "Zip='" + Zip + '\'' +
                "Country='" + Country + '\'' +
                "Phone='" + Phone + '\'' +
                "Fax='" + Fax + '\'' +
                "Email='" + Email + '\'' +
                "ContactName='" + ContactName + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "ContactEmail='" + ContactEmail + '\'' +
                "AccountsContactName='" + AccountsContactName + '\'' +
                "AccountsContactPhone='" + AccountsContactPhone + '\'' +
                "AccountsContactEmail='" + AccountsContactEmail + '\'' +
                "CreatedOn='" + CreatedOn + '\'' +
                ", LastChangedOn='" + LastChangedOn + '\'' +
                '}';
    }
}
