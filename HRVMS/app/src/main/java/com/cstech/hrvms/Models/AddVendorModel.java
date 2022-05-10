package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.AddVendorDataModel;

import java.util.List;

public class AddVendorModel {

    String didError,message,errorMessage;


    public List<AddVendorDataModel> getModel() {
        return model;
    }

    public void setModel(List<AddVendorDataModel> model) {
        this.model = model;
    }

    List<AddVendorDataModel> model;

    public String getDidError() {
        return didError;
    }

    public void setDidError(String didError) {
        this.didError = didError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AddVendorModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
