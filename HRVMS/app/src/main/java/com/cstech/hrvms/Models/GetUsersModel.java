package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.GetUsersDataModel;

import java.util.List;

public class GetUsersModel {
    String message,errorMessage,didError;

    public List<GetUsersDataModel> getModel() {
        return model;
    }

    public void setModel(List<GetUsersDataModel> model) {
        this.model = model;
    }

    List<GetUsersDataModel> model;

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
        return "GetUsersModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError=" + didError +
                '}';
    }
}
