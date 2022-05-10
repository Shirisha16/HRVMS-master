package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.GetJobTitlesDataModel;

import java.util.List;

public class GetJobTitlesModel {
    String message,errorMessage,didError;
    List<GetJobTitlesDataModel> model;

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

    public List<GetJobTitlesDataModel> getModel() {
        return model;
    }

    public void setModel(List<GetJobTitlesDataModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "GetJobTitlesModel{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError='" + didError + '\'' +
                ", model=" + model +
                '}';
    }
}
