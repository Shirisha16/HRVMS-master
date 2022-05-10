package com.cstech.hrvms.DataModels;

public class GetJobTitlesDataModel {
    int id;
    String jobTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "GetJobTitlesDataModel{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
