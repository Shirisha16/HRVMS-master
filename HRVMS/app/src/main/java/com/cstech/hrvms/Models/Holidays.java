package com.cstech.hrvms.Models;

import java.util.List;

public class Holidays {

    private String message,errorMessage;
    List<HolidaysList>model;

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

    public List<HolidaysList> getModel() {
        return model;
    }

    public void setModel(List<HolidaysList> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Holidays{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }


}
