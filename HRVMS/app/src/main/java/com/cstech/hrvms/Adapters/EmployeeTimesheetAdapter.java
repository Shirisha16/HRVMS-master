package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cstech.hrvms.DataModels.ViewTimeSheetDataModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeTimesheetAdapter  extends BaseAdapter {

    LayoutInflater inflater;
    Activity activity;
    List<ViewTimeSheetDataModel> viewTimeSheetData,filterList;
    TextView TimesheetPeriod,Client,WorkLocation,ClientManager,CSTManager,ProjectName,TaskDescription,WorkHours,Date;
    LinearLayout TimesheetPeriodLinearLayout,ClientLinearLayout,WorkLocationLinearLayout,ClientManagerLinearLayout,CSTManagerLinearLayout,
            ProjectNameLinearLayout,TaskDescriptionLinearLayout,WorkHoursLinearLayout,DateLinearLayout;

    public EmployeeTimesheetAdapter(Activity activity, List<ViewTimeSheetDataModel> viewTimeSheetData) {
        this.activity = activity;
        this.viewTimeSheetData = viewTimeSheetData;
        inflater=LayoutInflater.from(activity);
        filterList=new ArrayList<>();
        try {
            filterList.addAll(viewTimeSheetData);
        }catch (Exception e){

            Utils.showAlertDialog( activity, e.getMessage(),false);
        }

    }


    @Override
    public int getCount() {
        return viewTimeSheetData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.employeetimesheet_adapter,null);

        TimesheetPeriod = view.findViewById(R.id.TimesheetPeriod);
        Client = view.findViewById(R.id.Client);
        WorkLocation = view.findViewById(R.id.WorkLocation);
        ClientManager = view.findViewById(R.id.ClientManager);
        CSTManager = view.findViewById(R.id.CSTManager);
        ProjectName = view.findViewById(R.id.ProjectName);
        TaskDescription = view.findViewById(R.id.TaskDescription);
        WorkHours = view.findViewById(R.id.WorkHours);
        Date = view.findViewById(R.id.Date);
        TimesheetPeriodLinearLayout = view.findViewById(R.id.TimesheetPeriodLinearLayout);
        ClientLinearLayout = view.findViewById(R.id.ClientLinearLayout);
        WorkLocationLinearLayout = view.findViewById(R.id.WorkLocationLinearLayout);
        ClientManagerLinearLayout = view.findViewById(R.id.ClientManagerLinearLayout);
        CSTManagerLinearLayout = view.findViewById(R.id.CSTManagerLinearLayout);
        ProjectNameLinearLayout = view.findViewById(R.id.ProjectNameLinearLayout);
        TaskDescriptionLinearLayout = view.findViewById(R.id.TaskDescriptionLinearLayout);
        WorkHoursLinearLayout = view.findViewById(R.id.WorkHoursLinearLayout);
        DateLinearLayout = view.findViewById(R.id.DateLinearLayout);
        String BeforeFromSplit = viewTimeSheetData.get(position).getPeriodFrom();
        String BeforeToSplit = viewTimeSheetData.get(position).getPeriodTo();
        String BeforeDateonSplit = viewTimeSheetData.get(position).getDateOn();
        String[] separated = BeforeFromSplit.split("T");
        String[] separated1 = BeforeToSplit.split("T");
        String[] separated2 = BeforeDateonSplit.split("T");
        String AfterSplitFrom = separated[0].trim();
        String AfterSplitTo = separated1[0].trim();
        String FinalTimesheetPeriod = AfterSplitFrom + " To " + AfterSplitTo;
        TimesheetPeriod.setText(FinalTimesheetPeriod);
        Client.setText(viewTimeSheetData.get(position).getCompanyName());
        WorkLocation.setText(viewTimeSheetData.get(position).getWorkLocation());
        ClientManager.setText(viewTimeSheetData.get(position).getClientManager());
        Date.setText(separated2[0].trim());
        CSTManager.setText(viewTimeSheetData.get(position).getCstManager());
        ProjectName.setText(viewTimeSheetData.get(position).getProjectName());
        TaskDescription.setText(viewTimeSheetData.get(position).getTaskDescription());
        WorkHours.setText(viewTimeSheetData.get(position).getWorkedHours());


        if (WorkLocation.getText().toString().isEmpty()){
            WorkLocationLinearLayout.setVisibility(View.GONE);
        }  if (TimesheetPeriod.getText().toString().isEmpty()){
            TimesheetPeriodLinearLayout.setVisibility(View.GONE);
        }  if (Client.getText().toString().isEmpty()){
            ClientLinearLayout.setVisibility(View.GONE);
        }  if (ClientManager.getText().toString().isEmpty()){
            ClientManagerLinearLayout.setVisibility(View.GONE);
        }  if (CSTManager.getText().toString().isEmpty()){
            CSTManagerLinearLayout.setVisibility(View.GONE);
        }  if (ProjectName.getText().toString().isEmpty()){
            ProjectNameLinearLayout.setVisibility(View.GONE);
        }  if (TaskDescription.getText().toString().isEmpty()){
            TaskDescriptionLinearLayout.setVisibility(View.GONE);
            TaskDescription.setVisibility(View.GONE);
        }if (WorkHours.getText().toString().isEmpty()){
            WorkHoursLinearLayout.setVisibility(View.GONE);
        }if (Date.getText().toString().isEmpty()){
            DateLinearLayout.setVisibility(View.GONE);
        }

        return view;
    }

    private void SendMailMethod() {

    }

    public void filter(String searchedText) {

        if (searchedText.length()!=0){

            searchedText=searchedText.toLowerCase(Locale.getDefault());
            viewTimeSheetData.clear();

            for (ViewTimeSheetDataModel events: filterList){

                if (events.getCompanyName().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                        (events.getWorkLocation().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getClientManager().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getCstManager().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getProjectName().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getPeriodFrom().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getPeriodTo().toLowerCase(Locale.getDefault()).contains(searchedText))||
                        (events.getTaskDescription().toLowerCase(Locale.getDefault()).contains(searchedText))){

                    viewTimeSheetData.add(events);
                }

                notifyDataSetChanged();
            }
            if (viewTimeSheetData.size()==0){

                Utils.showAlertDialog(activity, "No data found",false);
            }

        }else {
            viewTimeSheetData.clear();
            viewTimeSheetData.addAll(filterList);
            notifyDataSetChanged();
        }
    }
}
