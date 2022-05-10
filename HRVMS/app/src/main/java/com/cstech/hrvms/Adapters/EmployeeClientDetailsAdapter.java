package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cstech.hrvms.Models.GetClients;
import com.cstech.hrvms.R;

import java.util.List;

public class EmployeeClientDetailsAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Activity activity;
    List<GetClients> employeeClientDetailsDataModel;
    TextView TimePeriod,Client,PrimaryVendor,SecondaryVendor,ThirdVendor;
    LinearLayout TimePeriodLinearLayout,ClientLinearLayout,PrimaryVendorLinearLayout,SecondaryVendorLinearLayout,ThirdVendorLinearLayout;
    String StartDateText,EndDateText;

    public EmployeeClientDetailsAdapter(Activity activity, List<GetClients> employeeClientDetailsDataModel) {
        this.activity = activity;
        inflater=LayoutInflater.from(activity);
        this.employeeClientDetailsDataModel = employeeClientDetailsDataModel;
    }

    @Override
    public int getCount() {
        return employeeClientDetailsDataModel.size();
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
        View view = inflater.inflate(R.layout.employeeclientdetails_adapter,null);

        TimePeriod = view.findViewById(R.id.TimePeriod);
        Client = view.findViewById(R.id.Client);
        PrimaryVendor = view.findViewById(R.id.PrimaryVendor);
        SecondaryVendor = view.findViewById(R.id.SecondaryVendor);
        ThirdVendor = view.findViewById(R.id.ThirdVendor);
        TimePeriodLinearLayout = view.findViewById(R.id.TimePeriodLinearLayout);
        ClientLinearLayout = view.findViewById(R.id.ClientLinearLayout);
        PrimaryVendorLinearLayout = view.findViewById(R.id.PrimaryVendorLinearLayout);
        SecondaryVendorLinearLayout = view.findViewById(R.id.SecondaryVendorLinearLayout);
        ThirdVendorLinearLayout = view.findViewById(R.id.ThirdVendorLinearLayout);

        StartDateText = employeeClientDetailsDataModel.get(position).getStartDate();

        if (StartDateText!= null){
            StartDateText = StartDateText.replace("T00:00:00","");
        }

        EndDateText = employeeClientDetailsDataModel.get(position).getEndDate();
        if (EndDateText!= null) {
            if (EndDateText.startsWith("0001-01-01T00:00:00")) {
                EndDateText = EndDateText.replace("0001-01-01T00:00:00", "");
            } else {
                EndDateText = employeeClientDetailsDataModel.get(position).getEndDate();
            }
            if (EndDateText.equals("")) {
                TimePeriod.setText(StartDateText + " To " + "-");
            } else{
                EndDateText = EndDateText.replace("T00:00:00", "");
                TimePeriod.setText(StartDateText + " To " + EndDateText);
            }

        }else{
            TimePeriod.setText(StartDateText + " To " + "-");
        }

        if (employeeClientDetailsDataModel.get(position).getClient()!=null){
            Client.setText(employeeClientDetailsDataModel.get(position).getClient());
        }
        if (employeeClientDetailsDataModel.get(position).getPrimaryVendor()!=null){
            PrimaryVendor.setText(employeeClientDetailsDataModel.get(position).getPrimaryVendor());
        }
        if (employeeClientDetailsDataModel.get(position).getSecondVendors()!=null){
            SecondaryVendor.setText(employeeClientDetailsDataModel.get(position).getSecondVendors());
        }
        if (employeeClientDetailsDataModel.get(position).getThirdVendors()!=null){
            ThirdVendor.setText(employeeClientDetailsDataModel.get(position).getThirdVendors());
        }


        if (Client.getText().toString().isEmpty()){
            ClientLinearLayout.setVisibility(View.GONE);
        }
        if (PrimaryVendor.getText().toString().isEmpty()){
            PrimaryVendorLinearLayout.setVisibility(View.GONE);
        }
        if (SecondaryVendor.getText().toString().isEmpty()){
            SecondaryVendorLinearLayout.setVisibility(View.GONE);
        }
        if (ThirdVendor.getText().toString().isEmpty()){
            ThirdVendorLinearLayout.setVisibility(View.GONE);
        }
        if (TimePeriod.getText().toString().isEmpty()){
            TimePeriodLinearLayout.setVisibility(View.GONE);
        }

        return view;
    }
}
