package com.cstech.hrvms.Models;

import java.util.Arrays;
import java.util.List;

public class EventsImages {

    private String message,errorMessage;
    private List<EventsImagesList>model;

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

    public List<EventsImagesList> getModel() {
        return model;
    }

    public void setModel(List<EventsImagesList> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "EventsImages{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }

    public class EventsImagesList {

       private int id;
       private String  photo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


    }
}
