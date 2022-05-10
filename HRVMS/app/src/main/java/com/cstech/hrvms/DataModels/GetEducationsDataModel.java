package com.cstech.hrvms.DataModels;

public class GetEducationsDataModel {
    int id;
    String educationTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(String educationTitle) {
        this.educationTitle = educationTitle;
    }

    @Override
    public String toString() {
        return "GetEducationsDataModel{" +
                "id=" + id +
                ", educationTitle='" + educationTitle + '\'' +
                '}';
    }
}
