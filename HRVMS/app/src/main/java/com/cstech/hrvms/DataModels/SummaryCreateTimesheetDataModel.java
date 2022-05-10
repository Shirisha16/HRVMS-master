package com.cstech.hrvms.DataModels;

public class SummaryCreateTimesheetDataModel {

    String Employee_Ref,id,workLocation,clientManager,cstManager,dateOn,projectName,taskDescription,PeriodFrom,PeriodTo,DatePosted,EntryType,WorkedHours;

    public String getEmployee_Ref() {
        return Employee_Ref;
    }

    public void setEmployee_Ref(String employee_Ref) {
        Employee_Ref = employee_Ref;
    }

    public String getWorkedHours() {
        return WorkedHours;
    }

    public void setWorkedHours(String workedHours) {
        WorkedHours = workedHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDateOn() {
        return dateOn;
    }

    public void setDateOn(String dateOn) {
        this.dateOn = dateOn;
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

    public String getPeriodFrom() {
        return PeriodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        PeriodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return PeriodTo;
    }

    public void setPeriodTo(String periodTo) {
        PeriodTo = periodTo;
    }

    public String getDatePosted() {
        return DatePosted;
    }

    public void setDatePosted(String datePosted) {
        DatePosted = datePosted;
    }

    public String getEntryType() {
        return EntryType;
    }

    public void setEntryType(String entryType) {
        EntryType = entryType;
    }

    @Override
    public String toString() {
        return "SummaryCreateTimesheetDataModel{" +
                "Employee_Ref='" + Employee_Ref + '\'' +
                ", id='" + id + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", clientManager='" + clientManager + '\'' +
                ", cstManager='" + cstManager + '\'' +
                ", dateOn='" + dateOn + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", PeriodFrom='" + PeriodFrom + '\'' +
                ", PeriodTo='" + PeriodTo + '\'' +
                ", DatePosted='" + DatePosted + '\'' +
                ", EntryType='" + EntryType + '\'' +
                ", WorkedHours='" + WorkedHours + '\'' +
                '}';
    }
}
