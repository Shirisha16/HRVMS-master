package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.GetClientVendorDataModel;

import java.util.List;

public class GetClientVendorModel {

    String message,errorMessage,didError;

    public List<GetClientVendorDataModel> getModel() {
        return model;
    }

    public void setModel(List<GetClientVendorDataModel> model) {
        this.model = model;
    }

    List<GetClientVendorDataModel> model;

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

    public String getDidError() {
        return didError;
    }

    public void setDidError(String didError) {
        this.didError = didError;
    }

    @Override
    public String toString() {
        return "GetClientVendorModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError=" + didError +
                '}';
    }
}
