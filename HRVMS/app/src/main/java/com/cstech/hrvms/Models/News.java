package com.cstech.hrvms.Models;

import java.util.List;

public class News {

    private String message,errorMessage;
    private List<NewsList> model;

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

    public List<NewsList> getModel() {
        return model;
    }

    public void setModel(List<NewsList> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "News{" +
                "message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", model=" + model +
                '}';
    }

    public static class NewsList {

        private int id;
        private String title,description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "NewsList{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }


    }
}
