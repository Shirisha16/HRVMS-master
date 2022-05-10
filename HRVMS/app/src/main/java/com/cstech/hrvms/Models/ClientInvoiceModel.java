package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.ClientInvoiceDataModel;

import java.util.List;

public class ClientInvoiceModel {

    String message,didError,errorMessage;
    List<ClientInvoiceDataModel> model;

    public List<ClientInvoiceDataModel> getModel() {
        return model;
    }

    public void setModel(List<ClientInvoiceDataModel> model) {
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
        return "ClientInvoiceModel{" +
                "message='" + message + '\'' +
                ", didError='" + didError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }
}
