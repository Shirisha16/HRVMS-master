package com.cstech.hrvms.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cstech.hrvms.Adapters.SpinnerAdapterForClientList;
import com.cstech.hrvms.DataModels.AddEmployeeDataModel;
import com.cstech.hrvms.DataModels.GetClientVendorDataModel;
import com.cstech.hrvms.Models.AddEmployeeModel;
import com.cstech.hrvms.Models.GetClientVendorModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateEmployeeDetailsActivity extends AppCompatActivity {

    TextView Employee,FromDate,ToDate,Submit,Cancel;
    Spinner ClientSpinner,PrimaryVendorSpinner,SecondaryVendorSpinner,ThirdVendorSpinner;
    EditText DialogHourlyRate;
    Calendar calendar;
    List<GetClientVendorDataModel> getClients,getPrimaryVendors,getSecondaryVendors,getThirdVendors;
    SpinnerAdapterForClientList spinnerAdapterForClientList;
    RestService restService;
    Toolbar toolbar;
    String Message,ErrorMessage,DidError,Variation,SelectedClient,SelectedPrimaryVendor,SelectedSecondaryVendor,SelectedThirdVendor,
            IdValue,StartDateValue,EndDateValue,PrimaryVendorValue,SecondaryVendorValue,ThirdVendorValue,ClientValue,HourlyRateValue,ClientValueId,PrimaryVendorValueId,SecondaryVendorValueId,ThirdVendorValueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee_details);


        toolbar = findViewById(R.id.Toolbar);
        toolbar = findViewById(R.id.Toolbar);
        toolbar.setTitle("Update Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        Intent i = getIntent();
        IdValue = i.getStringExtra("Id");
        StartDateValue = i.getStringExtra("StartDate");
        EndDateValue = i.getStringExtra("EndDate");
        ClientValue =i.getStringExtra("Client");
        ClientValueId =i.getStringExtra("ClientId");
        PrimaryVendorValue =i.getStringExtra("PrimaryVendor");
        PrimaryVendorValueId =i.getStringExtra("PrimaryVendorId");
        SecondaryVendorValue =i.getStringExtra("SecondaryVendor");
        SecondaryVendorValueId =i.getStringExtra("SecondaryVendorId");
        ThirdVendorValue =i.getStringExtra("ThirdVendor");
        ThirdVendorValueId =i.getStringExtra("ThirdVendorId");
        HourlyRateValue =i.getStringExtra("HourlyRate");


        if (StartDateValue!= null){
            StartDateValue = StartDateValue.replace("T00:00:00","");
        }

        if (EndDateValue!= null) {
            EndDateValue = EndDateValue.replace("T00:00:00","");
        }else{
            EndDateValue = "Select To Date";
        }

        restService = new RestService();

        getClients = new ArrayList<>();
        getPrimaryVendors = new ArrayList<>();
        getSecondaryVendors = new ArrayList<>();
        getThirdVendors = new ArrayList<>();

        Clients();
        PrimaryVendors();
        SecondaryVendors();
        ThirdVendors();

        Employee = findViewById(R.id.Employee);
        FromDate = findViewById(R.id.FromDate);
        ToDate = findViewById(R.id.ToDate);
        Submit = findViewById(R.id.Submit);
        Cancel = findViewById(R.id.Cancel);
        ClientSpinner = findViewById(R.id.ClientSpinner);
        PrimaryVendorSpinner = findViewById(R.id.PrimaryVendorSpinner);
        SecondaryVendorSpinner = findViewById(R.id.SecondaryVendorSpinner);
        ThirdVendorSpinner = findViewById(R.id.ThirdVendorSpinner);
        DialogHourlyRate = findViewById(R.id.DialogHourlyRate);
        calendar = Calendar.getInstance();

        FromDate.setText(StartDateValue);
        ToDate.setText(EndDateValue);
        DialogHourlyRate.setText(HourlyRateValue);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateEmployeeDetailsActivity.this,AddUpdateDeleteEmployeeDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    UpdateDetails();
            }
        });

                ClientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        SelectedClient = getClients.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                PrimaryVendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedPrimaryVendor = getPrimaryVendors.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                SecondaryVendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedSecondaryVendor = getSecondaryVendors.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                ThirdVendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedThirdVendor = getThirdVendors.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                final DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        datePicker.setMaxDate(System.currentTimeMillis());
                        datePicker.setMaxDate(System.currentTimeMillis());

                        calendar.set(java.util.Calendar.YEAR,year);
                        calendar.set(java.util.Calendar.MONTH,month);
                        calendar.set(java.util.Calendar.DAY_OF_MONTH,day);

                        String format="yyyy-MM-dd";
                        SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
                        if (Variation.equalsIgnoreCase("FromDate")) {
                            FromDate.setText(sdf.format(calendar.getTime()));
                        } else if (Variation.equalsIgnoreCase("Todate")) {
                            ToDate.setText(sdf.format(calendar.getTime()));
                        }
                    }
                };



                FromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Variation = "FromDate";
                        new DatePickerDialog(UpdateEmployeeDetailsActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                ToDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Variation = "ToDate";
                        new DatePickerDialog(UpdateEmployeeDetailsActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

    }





    private void UpdateDetails() {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        AddEmployeeDataModel addEmployeeDataModel = new AddEmployeeDataModel();
        addEmployeeDataModel.setID(IdValue);
        addEmployeeDataModel.setEmployee_Ref(AddUpdateDeleteEmployeeDetailsActivity.EmployeeId);
        addEmployeeDataModel.setClient_Ref(SelectedClient);
        addEmployeeDataModel.setPrimaryVendor_Ref(SelectedPrimaryVendor);
        addEmployeeDataModel.setSecondaryVendor_Ref(SelectedSecondaryVendor);
        addEmployeeDataModel.setThirdVendor_Ref(SelectedThirdVendor);
        addEmployeeDataModel.setStartDate(FromDate.getText().toString());
        if (ToDate.getText().toString().equalsIgnoreCase("Select To Date")){
            addEmployeeDataModel.setEndDate("");
        }else{
            addEmployeeDataModel.setEndDate(ToDate.getText().toString());
        }
        addEmployeeDataModel.setHourlyRate(DialogHourlyRate.getText().toString());
        addEmployeeDataModel.setCreatedOn("");
        addEmployeeDataModel.setLastChangedOn(date);

        System.out.println("Error" + addEmployeeDataModel);


        try
        {
            restService.getService().UpdateEmployeeWorkInfo(AddUpdateDeleteEmployeeDetailsActivity.EmployeeId, addEmployeeDataModel, new Callback<AddEmployeeModel>() {
                @Override
                public void success(AddEmployeeModel addEmployeeModel, Response response) {

                    Message = addEmployeeModel.getMessage();
                    DidError = addEmployeeModel.getDidError();
                    ErrorMessage = addEmployeeModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        Intent intent=new Intent(UpdateEmployeeDetailsActivity.this,AddUpdateDeleteEmployeeDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, "Updated Successfully", false);

                    } else {
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                    Utils.dismissProgressDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    public void Clients(){
        try {

            restService.getService().GetClients(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetClientVendorDataModel getClientVendorDataModel = new GetClientVendorDataModel();
                        getClientVendorDataModel.setId(ClientValueId);
                        getClientVendorDataModel.setCompanyName(ClientValue);
                        getClients = usersModel.getModel();
                        getClients.add(0,getClientVendorDataModel);
                        if (getClients !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(UpdateEmployeeDetailsActivity.this, R.id.text, getClients);
                            ClientSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    public void PrimaryVendors(){
        try {

            restService.getService().GetVendors(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetClientVendorDataModel getClientVendorDataModel = new GetClientVendorDataModel();
                        getClientVendorDataModel.setId(PrimaryVendorValueId);
                        getClientVendorDataModel.setCompanyName(PrimaryVendorValue);
                        getPrimaryVendors = usersModel.getModel();
                        getPrimaryVendors.add(0,getClientVendorDataModel);
                        if (getPrimaryVendors !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(UpdateEmployeeDetailsActivity.this, R.id.text, getPrimaryVendors);
                            PrimaryVendorSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    public void SecondaryVendors(){
        try {

            restService.getService().GetVendors(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetClientVendorDataModel getClientVendorDataModel = new GetClientVendorDataModel();
                        getClientVendorDataModel.setId(SecondaryVendorValueId);
                        getClientVendorDataModel.setCompanyName(SecondaryVendorValue);
                        getSecondaryVendors = usersModel.getModel();
                        getSecondaryVendors.add(0,getClientVendorDataModel);
                        if (getSecondaryVendors !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(UpdateEmployeeDetailsActivity.this, R.id.text, getSecondaryVendors);
                            SecondaryVendorSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    public void ThirdVendors(){
        try {

            restService.getService().GetVendors(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetClientVendorDataModel getClientVendorDataModel = new GetClientVendorDataModel();
                        getClientVendorDataModel.setId(ThirdVendorValueId);
                        getClientVendorDataModel.setCompanyName(ThirdVendorValue);
                        getThirdVendors = usersModel.getModel();
                        getThirdVendors.add(0,getClientVendorDataModel);
                        if (getThirdVendors !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(UpdateEmployeeDetailsActivity.this, R.id.text, getThirdVendors);
                            ThirdVendorSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(UpdateEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

            Toast.makeText(UpdateEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }
}
