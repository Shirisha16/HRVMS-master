package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.UserLoginDataModel;

import java.util.List;


public class UserLoginModel {

    String message;
    String didError;

    public void setModel(List<UserLoginDataModel> model) {
        this.model = model;
    }

    public List<UserLoginDataModel> getModel() {
        return model;
    }

    List<UserLoginDataModel> model;

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

    String errorMessage;

    @Override
    public String toString() {
        return "UserLoginModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError=" + didError +
                '}';
    }

}
