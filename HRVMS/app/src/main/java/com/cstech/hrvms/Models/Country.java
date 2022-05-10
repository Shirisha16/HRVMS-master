package com.cstech.hrvms.Models;

import java.util.List;

public class Country {

    private String message,errorMessage;
    private List<CountryList> model;

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

    public List<CountryList> getModel() {
        return model;
    }

    public void setModel(List<CountryList> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Country{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }

    public class CountryList {

        private int id;
        private String country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "CountryList{" +
                    "id=" + id +
                    ", country='" + country + '\'' +
                    '}';
        }
    }
}
