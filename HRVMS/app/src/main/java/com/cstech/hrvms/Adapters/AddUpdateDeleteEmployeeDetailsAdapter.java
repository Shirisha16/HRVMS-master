package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cstech.hrvms.Activities.LoginActivity;
import com.cstech.hrvms.Activities.UpdateEmployeeDetailsActivity;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.GetClients;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.Utils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddUpdateDeleteEmployeeDetailsAdapter extends BaseAdapter {

    TextView TimePeriod,Client,PrimaryVendor,SecondaryVendor,ThirdVendor,HourlyRate,Edit,Delete;
    LinearLayout TimePeriodLinearLayout,ClientLinearLayout,PrimaryVendorLinearLayout,SecondaryVendorLinearLayout,ThirdVendorLinearLayout,HourlyRateLinearLayout;
    LayoutInflater inflater;
    Activity activity;
    List<GetClients> employeeClientDetailsDataModel;
    RestService restService;
    String Message,ErrorMessage,DidError,StartDateText,EndDateText;
    LoadDetails loadDetails;



    public AddUpdateDeleteEmployeeDetailsAdapter(Activity activity, List<GetClients> employeeClientDetailsDataModel,LoadDetails loadDetails) {
        this.activity = activity;
        inflater=LayoutInflater.from(activity);
        this.employeeClientDetailsDataModel = employeeClientDetailsDataModel;
        this.loadDetails = loadDetails;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view =inflater.inflate(R.layout.add_update_delete_employee_details_adapter,null);
        TimePeriod = view.findViewById(R.id.TimePeriod);
        Client = view.findViewById(R.id.Client);
        PrimaryVendor = view.findViewById(R.id.PrimaryVendor);
        SecondaryVendor = view.findViewById(R.id.SecondaryVendor);
        ThirdVendor = view.findViewById(R.id.ThirdVendor);
        HourlyRate = view.findViewById(R.id.HourlyRate);
        Edit = view.findViewById(R.id.Edit);
        Delete = view.findViewById(R.id.Delete);
        TimePeriodLinearLayout = view.findViewById(R.id.TimePeriodLinearLayout);
        ClientLinearLayout = view.findViewById(R.id.ClientLinearLayout);
        PrimaryVendorLinearLayout = view.findViewById(R.id.PrimaryVendorLinearLayout);
        SecondaryVendorLinearLayout = view.findViewById(R.id.SecondaryVendorLinearLayout);
        ThirdVendorLinearLayout = view.findViewById(R.id.ThirdVendorLinearLayout);
        HourlyRateLinearLayout = view.findViewById(R.id.HourlyRateLinearLayout);

        restService = new RestService();

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

        Client.setText(employeeClientDetailsDataModel.get(position).getClient());
        PrimaryVendor.setText(employeeClientDetailsDataModel.get(position).getPrimaryVendor());
        SecondaryVendor.setText(employeeClientDetailsDataModel.get(position).getSecondVendors());
        ThirdVendor.setText(employeeClientDetailsDataModel.get(position).getThirdVendors());
        HourlyRate.setText(employeeClientDetailsDataModel.get(position).getHourlyRate());

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,UpdateEmployeeDetailsActivity.class);
                i.putExtra("Id",employeeClientDetailsDataModel.get(position).getId());
                i.putExtra("StartDate",employeeClientDetailsDataModel.get(position).getStartDate());
                i.putExtra("EndDate",employeeClientDetailsDataModel.get(position).getEndDate());
                i.putExtra("Client",employeeClientDetailsDataModel.get(position).getClient());
                i.putExtra("ClientId",employeeClientDetailsDataModel.get(position).getClientId());
                i.putExtra("PrimaryVendor",employeeClientDetailsDataModel.get(position).getPrimaryVendor());
                i.putExtra("PrimaryVendorId",employeeClientDetailsDataModel.get(position).getPrimaryVendorId());
                i.putExtra("SecondaryVendor",employeeClientDetailsDataModel.get(position).getSecondVendors());
                i.putExtra("SecondaryVendorId",employeeClientDetailsDataModel.get(position).getSecondaryVendorId());
                i.putExtra("ThirdVendor",employeeClientDetailsDataModel.get(position).getThirdVendors());
                i.putExtra("ThirdVendorId",employeeClientDetailsDataModel.get(position).getThirdVendorId());
                i.putExtra("HourlyRate",employeeClientDetailsDataModel.get(position).getHourlyRate());

                activity.startActivity(i);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setMessage("Do You Want To Delete?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        try {
                            restService.getService().DeleteEmployeeWorkInfo(employeeClientDetailsDataModel.get(position).getId(), new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {
                                    Message = deleteEmployeeWorkInfoModel.getMessage();
                                    DidError = deleteEmployeeWorkInfoModel.getDidError();
                                    ErrorMessage = deleteEmployeeWorkInfoModel.getErrorMessage();

                                    if (Message!= null){
                                        androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(activity);
                                        builder.setMessage("Deleted Successfully!");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                loadDetails.onMethodCallback();
                                            }
                                        });
                                        androidx.appcompat.app.AlertDialog dialog=builder.create();
                                        dialog.show();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Utils.showAlertDialog(activity,"These Record Can't be Deleted",false);
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(activity, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });

        return view;
    }
}