package com.cstech.hrvms.Models;

import com.cstech.hrvms.DataModels.SummaryCreateTimesheetDataModel;

import java.util.List;

public class MultipleTimeSheets {

    private  String PeriodFrom,PeriodTo,DatePosted,EntryType;
    private int Employee_Ref;
    private List<SummaryCreateTimesheetDataModel>multipletimesheet;

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

    public int getEmployee_Ref() {
        return Employee_Ref;
    }

    public void setEmployee_Ref(int employee_Ref) {
        Employee_Ref = employee_Ref;
    }

    public List<SummaryCreateTimesheetDataModel> getMultipletimesheet() {
        return multipletimesheet;
    }

    public void setMultipletimesheet(List<SummaryCreateTimesheetDataModel> multipletimesheet) {
        this.multipletimesheet = multipletimesheet;
    }

    @Override
    public String toString() {
        return "MultipleTimeSheets{" +
                "PeriodFrom='" + PeriodFrom + '\'' +
                ", PeriodTo='" + PeriodTo + '\'' +
                ", DatePosted='" + DatePosted + '\'' +
                ", EntryType='" + EntryType + '\'' +
                ", Employee_Ref=" + Employee_Ref +
                ", multipletimesheet=" + multipletimesheet +
                '}';
    }
}
