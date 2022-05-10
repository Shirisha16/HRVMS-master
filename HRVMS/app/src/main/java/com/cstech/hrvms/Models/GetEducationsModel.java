package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.GetEducationsDataModel;

import java.util.List;

public class GetEducationsModel {
    String message,errorMessage,didError;
    List<GetEducationsDataModel> model;

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

    public List<GetEducationsDataModel> getModel() {
        return model;
    }

    public void setModel(List<GetEducationsDataModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "GetEducationsModel{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError='" + didError + '\'' +
                ", model=" + model +
                '}';
    }
}
