package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cstech.hrvms.DataModels.SummaryCreateTimesheetDataModel;
import com.cstech.hrvms.R;

import java.util.List;

public class CreateTimeSheetAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<SummaryCreateTimesheetDataModel> summaryCreateTimesheetDataModel;
    TextView Date,WorkLocation,ClientManager,CSTManager,ProjectName,TaskDescription,WorkHours;

    public CreateTimeSheetAdapter(Activity activity, List<SummaryCreateTimesheetDataModel> summaryCreateTimesheetDataModel) {
        this.activity = activity;
        this.summaryCreateTimesheetDataModel = summaryCreateTimesheetDataModel;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return summaryCreateTimesheetDataModel.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.createtimesheet_adapter,null);

        Date = view.findViewById(R.id.Date);
        WorkLocation = view.findViewById(R.id.WorkLocation);
        ClientManager = view.findViewById(R.id.ClientManager);
        CSTManager = view.findViewById(R.id.CSTManager);
        ProjectName = view.findViewById(R.id.ProjectName);
        TaskDescription = view.findViewById(R.id.TaskDescription);
        WorkHours = view.findViewById(R.id.WorkHours);


        Date.setText(summaryCreateTimesheetDataModel.get(position).getDateOn());
        WorkLocation.setText(summaryCreateTimesheetDataModel.get(position).getWorkLocation());
        ClientManager.setText(summaryCreateTimesheetDataModel.get(position).getClientManager());
        CSTManager.setText(summaryCreateTimesheetDataModel.get(position).getCstManager());
        ProjectName.setText(summaryCreateTimesheetDataModel.get(position).getProjectName());
        TaskDescription.setText(summaryCreateTimesheetDataModel.get(position).getTaskDescription());
        WorkHours.setText(summaryCreateTimesheetDataModel.get(position).getWorkedHours());

        return view;
    }
}
