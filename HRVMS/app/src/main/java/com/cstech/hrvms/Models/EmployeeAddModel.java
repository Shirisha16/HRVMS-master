package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.EmployeeAddDataModel;

import java.util.List;

public class EmployeeAddModel {

    String message,didError,errorMessage;
    List<EmployeeAddDataModel> model;

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

    public List<EmployeeAddDataModel> getModel() {
        return model;
    }

    public void setModel(List<EmployeeAddDataModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "EmployeeAddModel{" +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }
}
