package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.SummaryCreateTimesheetDataModel;

public class SummaryCreateTimesheetModel {

    String message,didError,errorMessage;
    SummaryCreateTimesheetDataModel model;

    public SummaryCreateTimesheetDataModel getModel() {
        return model;
    }

    public void setModel(SummaryCreateTimesheetDataModel model) {
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

    @Override
    public String toString() {
        return "SummaryCreateTimesheetModel{" +
                "model='" + model + '\'' +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", didError=" + didError +
                '}';
    }
}
