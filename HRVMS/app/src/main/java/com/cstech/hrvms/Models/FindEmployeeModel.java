package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.FindEmployeeDataModel;

import java.util.List;

public class FindEmployeeModel {

    String message,errorMessage,didError;

    public List<FindEmployeeDataModel> getModel() {
        return model;
    }

    public void setModel(List<FindEmployeeDataModel> model) {
        this.model = model;
    }

    List<FindEmployeeDataModel> model;

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
        return "FindEmployeeModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError=" + didError +
                '}';
    }
    
}
