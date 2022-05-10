package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.AddEmployeeDataModel;

import java.util.List;

public class AddEmployeeModel {
    String didError,message,errorMessage;

    public List<AddEmployeeDataModel> getModel() {
        return model;
    }

    public void setModel(List<AddEmployeeDataModel> model) {
        this.model = model;
    }

    List<AddEmployeeDataModel> model;

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
        return "AddEmployeeModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
