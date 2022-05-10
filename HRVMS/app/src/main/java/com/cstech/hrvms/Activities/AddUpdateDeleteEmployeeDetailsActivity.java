package com.cstech.hrvms.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cstech.hrvms.Adapters.AddUpdateDeleteEmployeeDetailsAdapter;
import com.cstech.hrvms.Adapters.SpinnerAdapterForClientList;
import com.cstech.hrvms.DataModels.AddEmployeeDataModel;
import com.cstech.hrvms.DataModels.EmployeeClientDetailsDataModel;
import com.cstech.hrvms.DataModels.GetClientVendorDataModel;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.AddEmployeeModel;
import com.cstech.hrvms.Models.EmployeeClientDetailsModel;
import com.cstech.hrvms.Models.GetClientVendorModel;
import com.cstech.hrvms.Models.GetClients;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddUpdateDeleteEmployeeDetailsActivity extends AppCompatActivity implements LoadDetails {
    
    ListView listView;
    RestService restService;
    String Message,DidError,ErrorMessage,EmployeeName,Variation,SelectedClient,SelectedPrimaryVendor,SelectedSecondaryVendor,SelectedThirdVendor;
    public static String EmployeeId;
    AddUpdateDeleteEmployeeDetailsAdapter addUpdateDeleteEmployeeDetailsAdapter;
    Toolbar toolbar;
    FloatingActionButton AddButton;
    TextView Employee,FromDate,ToDate,Submit,Cancel;
    Spinner ClientSpinner,PrimaryVendorSpinner,SecondaryVendorSpinner,ThirdVendorSpinner;
    EditText HourlyRate;
    Dialog dialog,dialog2;
    public static List<GetClientVendorDataModel> getClients,getVendors;
    SpinnerAdapterForClientList spinnerAdapterForClientList;
    Calendar calendar;
    List<EmployeeClientDetailsDataModel> list;
    List<GetClients> getEmpWorkInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_delete_employee_details);

        toolbar = findViewById(R.id.Toolbar);
        toolbar.setTitle("Working Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        
        listView = findViewById(R.id.ListView);
        AddButton = (FloatingActionButton)findViewById(R.id.AddButton);
        restService = new RestService();
        list = new ArrayList<>();
        getClients = new ArrayList<>();
        getVendors = new ArrayList<>();
        getEmpWorkInfo = new ArrayList<>();


        Intent i = getIntent();
         EmployeeId =PreferenceConnector.readString(getApplicationContext(), "EmployeeId","");
         EmployeeName =i.getStringExtra("Name");

        Clients();
        Vendors();
        getDetails();


        dialog = new Dialog(AddUpdateDeleteEmployeeDetailsActivity.this);
        dialog.setContentView(R.layout.add_workinginfo_dialog_activity);
        Employee = dialog.findViewById(R.id.Employee);
        FromDate = dialog.findViewById(R.id.FromDate);
        ToDate = dialog.findViewById(R.id.ToDate);
        Submit = dialog.findViewById(R.id.Submit);
        Cancel = dialog.findViewById(R.id.Cancel);
        ClientSpinner = dialog.findViewById(R.id.ClientSpinner);
        PrimaryVendorSpinner = dialog.findViewById(R.id.PrimaryVendorSpinner);
        SecondaryVendorSpinner = dialog.findViewById(R.id.SecondaryVendorSpinner);
        ThirdVendorSpinner = dialog.findViewById(R.id.ThirdVendorSpinner);
        HourlyRate = dialog.findViewById(R.id.DialogHourlyRate);
        calendar = Calendar.getInstance();


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

                SelectedPrimaryVendor = getVendors.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SecondaryVendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectedSecondaryVendor = getVendors.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ThirdVendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectedThirdVendor = getVendors.get(position).getId();
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
                new DatePickerDialog(AddUpdateDeleteEmployeeDetailsActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variation = "ToDate";
                new DatePickerDialog(AddUpdateDeleteEmployeeDetailsActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = dialog;
                if (dialog2 != null)
                {
                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    dialog2.getWindow().setLayout(width, height);
                }
                AddUpdateDeleteEmployeeDetailsActivity.this.dialog.show();
                Employee.setText(EmployeeName);

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HourlyRate.getText().toString().isEmpty()){
                    HourlyRate.setError("Hourly rate Can't be Empty");
                }else if (FromDate.getText().toString().equalsIgnoreCase("Select From Date")){
                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Please Select From Date First!", Toast.LENGTH_SHORT).show();
                }else if (SelectedClient == null){
                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Please Select To Client First!", Toast.LENGTH_SHORT).show();
                }else if (SelectedPrimaryVendor == null){
                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Please Select To Primary Vendor First!", Toast.LENGTH_SHORT).show();
                }else {
                    AddEmployeeWorkInfo();
                }
            }
        });
    }

    public void  getDetails(){
        Utils.showProgressDialog(AddUpdateDeleteEmployeeDetailsActivity.this);
        try {
            restService.getService().GetClientVendorDetails(EmployeeId,new Callback<EmployeeClientDetailsModel>() {
                @Override
                public void success(EmployeeClientDetailsModel employeeClientDetailsModel, Response response) {

                    Message = employeeClientDetailsModel.getMessage();
                    DidError = employeeClientDetailsModel.getDidError();
                    ErrorMessage = employeeClientDetailsModel.getErrorMessage();
                    getEmpWorkInfo=new ArrayList<>();
                    list = employeeClientDetailsModel.getModel();
                    if (AddUpdateDeleteEmployeeDetailsActivity.this != null) {


                        if (getClients!=null){

                            for (int i=0; i<list.size(); i++){
                                GetClients clients=new GetClients();
                                clients.setStartDate(list.get(i).getStartDate());
                                clients.setEndDate(list.get(i).getEndDate());
                                clients.setId(list.get(i).getId());
                                clients.setHourlyRate(list.get(i).getHourlyRate());
                                for (int j=0; j<getClients.size(); j++){
                                    if (!getClients.get(j).getCompanyName().isEmpty()) {
                                        if (getClients.get(j).getId().equalsIgnoreCase(list.get(i).getClient_Ref())) {
                                            clients.setClient(getClients.get(j).getCompanyName());
                                            clients.setClientId(getClients.get(j).getId());
                                        }
                                    }

                                }
                                for (int k=0; k<getVendors.size(); k++){
                                    if (!getVendors.get(k).getCompanyName().isEmpty()) {
                                        if (list.get(i).getPrimaryVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {
                                            clients.setPrimaryVendor(getVendors.get(k).getCompanyName());
                                            clients.setPrimaryVendorId(getVendors.get(k).getId());
                                        }
                                        if (list.get(i).getSecondaryVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {
                                            clients.setSecondVendors(getVendors.get(k).getCompanyName());
                                            clients.setSecondaryVendorId(getVendors.get(k).getId());
                                        }
                                        if (list.get(i).getThirdVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {
                                            clients.setThirdVendors(getVendors.get(k).getCompanyName());
                                            clients.setThirdVendorId(getVendors.get(k).getId());
                                        }

                                    }
                                }
                            getEmpWorkInfo.add(clients);

                            }

                            if (list.size() > 0) {

                                addUpdateDeleteEmployeeDetailsAdapter = new AddUpdateDeleteEmployeeDetailsAdapter(AddUpdateDeleteEmployeeDetailsActivity.this,getEmpWorkInfo,AddUpdateDeleteEmployeeDetailsActivity.this);
                                listView.setAdapter(addUpdateDeleteEmployeeDetailsAdapter);
                                System.out.println("Error" + getEmpWorkInfo);
                            } else {

                                if (list.size() == 0) {
                                    addUpdateDeleteEmployeeDetailsAdapter = new AddUpdateDeleteEmployeeDetailsAdapter(AddUpdateDeleteEmployeeDetailsActivity.this,getEmpWorkInfo,AddUpdateDeleteEmployeeDetailsActivity.this);
                                    listView.setAdapter(addUpdateDeleteEmployeeDetailsAdapter);
                                    System.out.println("Error" + getEmpWorkInfo);
                                    Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, "No Data Available", false);

                                } else {
                                    Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, "No Data Available", false);

                                }
                            }
                            Utils.dismissProgressDialog();
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    private void AddEmployeeWorkInfo() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        AddEmployeeDataModel addEmployeeDataModel = new AddEmployeeDataModel();
        addEmployeeDataModel.setEmployee_Ref(EmployeeId);
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
        addEmployeeDataModel.setHourlyRate(HourlyRate.getText().toString());
        addEmployeeDataModel.setCreatedOn(date);
        addEmployeeDataModel.setLastChangedOn(date);


        try
        {

            restService.getService().AddEmployeeWorkInfo(EmployeeId, addEmployeeDataModel, new Callback<AddEmployeeModel>() {
                @Override
                public void success(AddEmployeeModel addEmployeeModel, Response response) {

                    Message = addEmployeeModel.getMessage();
                    DidError = addEmployeeModel.getDidError();
                    ErrorMessage = addEmployeeModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        dialog.dismiss();
                        getDetails();
                        FromDate.setText("Select From Date");
                        ToDate.setText("Select To Date");
                        HourlyRate.setText("");
                        Clients();
                        Vendors();
                        Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, "Work Info Added ", false);


                    } else {
                        Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                    Utils.dismissProgressDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
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
                        getClientVendorDataModel.setCompanyName("");
                        getClients = usersModel.getModel();
                        getClients.add(0,getClientVendorDataModel);
                        if (getClients !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(AddUpdateDeleteEmployeeDetailsActivity.this, R.id.text, getClients);
                            ClientSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    public void Vendors(){
        try {

            restService.getService().GetVendors(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetClientVendorDataModel getClientVendorDataModel = new GetClientVendorDataModel();
                        getClientVendorDataModel.setCompanyName("");
                        getVendors = usersModel.getModel();
                        getVendors.add(0,getClientVendorDataModel);
                        if (getVendors !=null) {
                            spinnerAdapterForClientList = new SpinnerAdapterForClientList(AddUpdateDeleteEmployeeDetailsActivity.this, R.id.text, getVendors);
                            PrimaryVendorSpinner.setAdapter(spinnerAdapterForClientList);
                            SecondaryVendorSpinner.setAdapter(spinnerAdapterForClientList);
                            ThirdVendorSpinner.setAdapter(spinnerAdapterForClientList);
                        }
                    } else {
                        Utils.showAlertDialog(AddUpdateDeleteEmployeeDetailsActivity.this, ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(AddUpdateDeleteEmployeeDetailsActivity.this, "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMethodCallback() {
        getDetails();
    }
}
