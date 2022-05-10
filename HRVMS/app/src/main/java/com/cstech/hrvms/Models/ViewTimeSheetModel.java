package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.ViewTimeSheetDataModel;

import java.util.List;

public class ViewTimeSheetModel {
    String message;
    List<ViewTimeSheetDataModel> model;

    public List<ViewTimeSheetDataModel> getModel() {
        return model;
    }

    public void setModel(List<ViewTimeSheetDataModel> model) {
        this.model = model;
    }



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

    String didError;
    String errorMessage;

    @Override
    public String toString() {
        return "ViewTimeSheetModel{" +
                ", model=" + model +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
