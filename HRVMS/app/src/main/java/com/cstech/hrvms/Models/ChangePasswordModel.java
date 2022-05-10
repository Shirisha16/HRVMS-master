package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.ChangePasswordDataModel;

import java.util.List;

public class ChangePasswordModel {

    String message,didError,errorMessage;
    List<ChangePasswordDataModel> model;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDidError() {
        return didError;
    }

    public void setDidError(String didError) {
        this.didError = didError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ChangePasswordDataModel> getModel() {
        return model;
    }

    public void setModel(List<ChangePasswordDataModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ChangePasswordModel{" +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }
}
