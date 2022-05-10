package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cstech.hrvms.Activities.AddUpdateDeleteEmployeeDetailsActivity;
import com.cstech.hrvms.DataModels.FindEmployeeDataModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FindEmployeeAdapter extends BaseAdapter {

    TextView EmpCode,FirstName,LastName;
    ImageView WorkingInfo;
    LayoutInflater inflater;
    Activity activity;
    List<FindEmployeeDataModel> findEmployeeDataModel,filterList;
    LinearLayout EmpCodeLinearLayout,FirstNameLinearLayout,LastNameLinearLayout;

    public FindEmployeeAdapter( Activity activity,List<FindEmployeeDataModel> findEmployeeDataModel) {
        this.findEmployeeDataModel = findEmployeeDataModel;
        this.activity = activity;
        inflater=LayoutInflater.from(activity);
        filterList=new ArrayList<>();
        try {
            filterList.addAll(findEmployeeDataModel);
        }catch (Exception e){

            Utils.showAlertDialog( activity, e.getMessage(),false);
        }
    }

    @Override
    public int getCount() {
        return findEmployeeDataModel.size();
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
        View view =inflater.inflate(R.layout.findemployee_adapter,null);
        EmpCode = view.findViewById(R.id.EmpCode);
        FirstName = view.findViewById(R.id.FirstName);
        LastName = view.findViewById(R.id.LastName);
        WorkingInfo = view.findViewById(R.id.WorkingInfo);
        EmpCodeLinearLayout = view.findViewById(R.id.EmpCodeLinearLayout);
        FirstNameLinearLayout = view.findViewById(R.id.FirstNameLinearLayout);
        LastNameLinearLayout = view.findViewById(R.id.LastNameLinearLayout);

        EmpCode.setText(findEmployeeDataModel.get(position).getEmpCode());
        FirstName.setText(findEmployeeDataModel.get(position).getFirstName());
        LastName.setText(findEmployeeDataModel.get(position).getLastName());


        WorkingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceConnector.writeString(activity, "EmployeeId", findEmployeeDataModel.get(position).getId());

                Intent i = new Intent(activity, AddUpdateDeleteEmployeeDetailsActivity.class);
                i.putExtra("Id",findEmployeeDataModel.get(position).getId());
                i.putExtra("Name",findEmployeeDataModel.get(position).getFirstName() + findEmployeeDataModel.get(position).getLastName());
                activity.startActivity(i);
            }
        });

        if (EmpCode.getText().toString().isEmpty()){
            EmpCodeLinearLayout.setVisibility(View.GONE);
        }
        if (FirstName.getText().toString().isEmpty()){
            FirstNameLinearLayout.setVisibility(View.GONE);
        }
        if (LastName.getText().toString().isEmpty()){
            LastNameLinearLayout.setVisibility(View.GONE);
        }

        if (EmpCode.getText().toString().isEmpty() && FirstName.getText().toString().isEmpty() && LastName.getText().toString().isEmpty()){
            WorkingInfo.setVisibility(View.GONE);
        }

        return view;
    }

    public void filter(String searchedText) {

        if (searchedText.length()!=0){

            searchedText=searchedText.toLowerCase(Locale.getDefault());
            findEmployeeDataModel.clear();

            for (FindEmployeeDataModel events: filterList){
                if (events.getEmpCode() != null && events.getFirstName() != null && events.getLastName() != null){
                    if (events.getEmpCode().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                            events.getFirstName().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                            events.getLastName().toLowerCase(Locale.getDefault()).contains(searchedText)){

                        findEmployeeDataModel.add(events);
                    }
                }else {

                }

                notifyDataSetChanged();
            }
            if (findEmployeeDataModel.size()==0){

                Utils.showAlertDialog(activity, "No data found",false);
            }

        }else {
            findEmployeeDataModel.addAll(filterList);
            notifyDataSetChanged();
        }
    }
    
}
