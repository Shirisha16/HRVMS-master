package com.cstech.hrvms.DataModels;

public class ViewTimeSheetDataModel {

    String id;
    String workLocation;
    String clientManager;
    String cstManager;
    String projectName;
    String taskDescription;
    String dateOn;
    String periodFrom;
    String periodTo;
    String workedHours;
    String companyName;

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getClientManager() {
        return clientManager;
    }

    public void setClientManager(String clientManager) {
        this.clientManager = clientManager;
    }

    public String getCstManager() {
        return cstManager;
    }

    public void setCstManager(String cstManager) {
        this.cstManager = cstManager;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours = workedHours;
    }



    public String getDateOn() {
        return dateOn;
    }

    public void setDateOn(String dateOn) {
        this.dateOn = dateOn;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ViewTimeSheetDataModel{" +
                "id='" + id + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", clientManager='" + clientManager + '\'' +
                ", cstManager='" + cstManager + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", dateOn='" + dateOn + '\'' +
                ", periodFrom='" + periodFrom + '\'' +
                ", periodTo='" + periodTo + '\'' +
                ", workedHours='" + workedHours + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

}
