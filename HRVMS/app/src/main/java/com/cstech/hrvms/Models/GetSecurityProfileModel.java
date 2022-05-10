package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.GetSecurityProfileDataModel;

import java.util.List;

public class GetSecurityProfileModel {
    String message,errorMessage,didError;
    List<GetSecurityProfileDataModel> model;

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

    public List<GetSecurityProfileDataModel> getModel() {
        return model;
    }

    public void setModel(List<GetSecurityProfileDataModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "GetSecurityProfileModel{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError='" + didError + '\'' +
                ", model=" + model +
                '}';
    }
}
