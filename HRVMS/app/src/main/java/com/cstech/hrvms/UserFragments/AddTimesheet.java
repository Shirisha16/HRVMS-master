package com.cstech.hrvms.UserFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.Adapters.CreateTimeSheetAdapter;
import com.cstech.hrvms.DataModels.SummaryCreateTimesheetDataModel;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.MultipleTimeSheets;
import com.cstech.hrvms.Models.SummaryCreateTimesheetModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddTimesheet extends Fragment implements LoadDetails {

    List<SummaryCreateTimesheetDataModel> multipleTimeSheet;
    ListView listValues;
    TextView SelectFromDate,SelectToDate,SelectedDate,Cancel,PostDialog;
    RadioButton DetailRadioButton,SummaryRadioButton;
    RadioGroup radioGroup;
    LinearLayout DaywiseDateLayout,TotalInfoLayout;
    EditText WorkLocation,ClientManager,CSTManager,ProjectName,TaskDescription,WorkHours;
    Button Post,Next;
    String WorkLocationText,ClientManagerText,CSTManagerText,ProjectNameText,TaskDescriptionText,WorkHoursText,SelectFromDateText,SelectToDateText,Variation,
            Message,ErrorMessage,DidError,UserId;
    Calendar calendar;
    RestService restService;
    CreateTimeSheetAdapter createTimeSheetAdapter;
    int i,j;
    Dialog dialog2;
    public SummaryCreateTimesheetDataModel model;
    MultipleTimeSheets multipleTimeSheetsList;

    public AddTimesheet() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((UserSlideMenu) getActivity( ))
                .setActionBarTitle("Create Timesheet");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.createtimesheet_fragment, container, false);
        SelectFromDate = view.findViewById(R.id.SelectFromDate);
        SelectToDate = view.findViewById(R.id.SelectToDate);
        SelectedDate = view.findViewById(R.id.SelectedDate);
        DetailRadioButton = view.findViewById(R.id.DetailRadioButton);
        SummaryRadioButton = view.findViewById(R.id.SummaryRadioButton);
        radioGroup = view.findViewById(R.id.radioGroup);
        DaywiseDateLayout = view.findViewById(R.id.DaywiseDateLayout);
        TotalInfoLayout = view.findViewById(R.id.TotalInfoLayout);
        WorkLocation = view.findViewById(R.id.WorkLocation);
        ClientManager = view.findViewById(R.id.ClientManager);
        CSTManager = view.findViewById(R.id.CSTManager);
        ProjectName = view.findViewById(R.id.ProjectName);
        multipleTimeSheet=new ArrayList<>();
        TaskDescription = view.findViewById(R.id.TaskDescription);
        WorkHours = view.findViewById(R.id.WorkHours);
        Post = view.findViewById(R.id.Post);
        Next = view.findViewById(R.id.next);
        calendar = Calendar.getInstance();
        multipleTimeSheetsList=new MultipleTimeSheets();
        restService =new RestService();
         UserId = PreferenceConnector.readString(getActivity(),"UserId","");

        DaywiseDateLayout.setVisibility(View.GONE);
        TotalInfoLayout.setVisibility(View.GONE);

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
                if (Variation.equalsIgnoreCase("SelectFromDate")) {
                    SelectFromDate.setText(sdf.format(calendar.getTime()));

                } else if (Variation.equalsIgnoreCase("SelectToDate")) {
                    SelectToDate.setText(sdf.format(calendar.getTime()));

                }

            }
        };
        try {
            SelectFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "SelectFromDate";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            SelectToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "SelectToDate";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
        }catch (Exception e){

        }


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectFromDate.getText().toString().equalsIgnoreCase("Select From Date")) {
                    Toast.makeText(getContext(), "Please Select From Date First!", Toast.LENGTH_SHORT).show();

                } else if (SelectToDate.getText().toString().equalsIgnoreCase("Select To Date")) {
                    Toast.makeText(getContext(), "Please Select To Date First!", Toast.LENGTH_SHORT).show();
                } else if ( SelectFromDate.getText().toString().compareTo(SelectToDate.getText().toString()) > 0 ) {

                    //  0 comes when two date are same,
                    //  1 comes when date1 is higher then date2
                    // -1 comes when date1 is lower then date2
                    Utils.showAlertDialog(getContext(),"Invalid To Date !",false);
                    SelectToDate.setText("Select To Date");

                }
                else{

                    if (DetailRadioButton.isChecked()) {
                        SelectToDate.setClickable(false);
                        SelectFromDate.setClickable(false);
                        DetailRadioButton.setClickable(false);
                        SummaryRadioButton.setClickable(false);
                        DaywiseDateLayout.setVisibility(View.VISIBLE);
                        TotalInfoLayout.setVisibility(View.VISIBLE);
                        Post.setText("Add");
                        SelectedDate.setText(SelectFromDate.getText().toString());

                    } else if (SummaryRadioButton.isChecked()) {
                        SelectToDate.setClickable(false);
                        SelectFromDate.setClickable(false);
                        DetailRadioButton.setClickable(false);
                        SummaryRadioButton.setClickable(false);
                        TotalInfoLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(getContext(), "Please Check any one field! Either Description or Summary", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

       final Dialog dialog = new Dialog(getContext());
       dialog.setContentView(R.layout.activity_detail_create_timesheet_list);
       listValues = dialog.findViewById(R.id.List);
       PostDialog = dialog.findViewById(R.id.PostDialog);
       Cancel = dialog.findViewById(R.id.Cancel);
        createTimeSheetAdapter = new CreateTimeSheetAdapter(getActivity(), multipleTimeSheet);
        listValues.setAdapter(createTimeSheetAdapter);

       Cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DetailRadioButton.isChecked()) {

                    if (Validations()) {

                        multipleTimeSheetsList.setDatePosted(date);
                        multipleTimeSheetsList.setEmployee_Ref(Integer.parseInt(UserId));
                        multipleTimeSheetsList.setEntryType("D");
                        multipleTimeSheetsList.setPeriodFrom(SelectFromDate.getText().toString().trim());
                        multipleTimeSheetsList.setPeriodTo(SelectToDate.getText().toString().trim());

                        if ((SelectedDate.getText().toString().equals(SelectFromDate.getText().toString())) && (SelectedDate.getText().toString().equals(SelectToDate.getText().toString())) && j==10){
                            dialog2 = dialog;
                            if (dialog2 != null)
                            {
                                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                dialog2.getWindow().setLayout(width, height);
                            }

                            dialog.show();
                        }else if ((SelectedDate.getText().toString().equals(SelectFromDate.getText().toString())) && (SelectedDate.getText().toString().equals(SelectToDate.getText().toString()))){
                            model = new SummaryCreateTimesheetDataModel();
                           /* model.setEntryType("D");
                            model.setEmployee_Ref(UserId);
                            model.setPeriodFrom(SelectedDate.getText().toString().trim());
                            model.setPeriodTo(SelectedDate.getText().toString().trim());*/
                            model.setEmployee_Ref(UserId);
                            model.setDateOn(SelectedDate.getText().toString().trim());
                            model.setWorkLocation(WorkLocation.getText().toString().trim());
                            model.setClientManager(ClientManager.getText().toString().trim());
                            model.setCstManager(CSTManager.getText().toString().trim());
                            model.setProjectName(ProjectName.getText().toString().trim());
                            model.setTaskDescription(TaskDescription.getText().toString());
                            //model.setDatePosted(date);
                            model.setWorkedHours(WorkHours.getText().toString());
                            multipleTimeSheet.add(model);
                            System.out.println("Error Values" + model);
                            dialog2 = dialog;
                            if (dialog2 != null)
                            {
                                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                dialog2.getWindow().setLayout(width, height);
                            }
                            i=10;
                            j=i;
                            dialog.show();
                        }else if (!SelectedDate.getText().toString().equals(SelectToDate.getText().toString())){

                            model = new SummaryCreateTimesheetDataModel();
                           /* model.setEntryType("D");
                            model.setEmployee_Ref(UserId);
                            model.setPeriodFrom(SelectedDate.getText().toString().trim());
                            model.setPeriodTo(SelectedDate.getText().toString().trim());*/
                            model.setEmployee_Ref(UserId);
                            model.setDateOn(SelectedDate.getText().toString());
                            model.setWorkLocation(WorkLocation.getText().toString().trim());
                            model.setClientManager(ClientManager.getText().toString().trim());
                            model.setCstManager(CSTManager.getText().toString().trim());
                            model.setProjectName(ProjectName.getText().toString().trim());
                            model.setTaskDescription(TaskDescription.getText().toString());
                            //model.setDatePosted(date);
                            model.setWorkedHours(WorkHours.getText().toString());
                            multipleTimeSheet.add(model);
                            System.out.println("Error Values" + model);
                            WorkLocation.setText("");
                            ClientManager.setText("");
                            CSTManager.setText("");
                            ProjectName.setText("");
                            TaskDescription.setText("");
                            WorkHours.setText("0");
                            Toast.makeText(getActivity(), "Details Added", Toast.LENGTH_SHORT).show();
                            String dateInString = SelectedDate.getText().toString();  // Start date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(dateInString));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 1);
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date resultdate = new Date(c.getTimeInMillis());
                            dateInString = sdf.format(resultdate);
                            SelectedDate.setText(dateInString);
                        }else if((SelectedDate.getText().toString().equals(SelectToDate.getText().toString())) && j==10){
                           dialog2 = dialog;
                            if (dialog2 != null)
                            {
                                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                dialog2.getWindow().setLayout(width, height);
                            }
                            dialog.show();
                        }
                        else if(SelectedDate.getText().toString().equals(SelectToDate.getText().toString())){

                            model = new SummaryCreateTimesheetDataModel();
                           /* model.setEntryType("D");

                            model.setPeriodFrom(SelectedDate.getText().toString().trim());
                            model.setPeriodTo(SelectedDate.getText().toString().trim());*/
                            model.setEmployee_Ref(UserId);
                            model.setDateOn(SelectedDate.getText().toString());
                            model.setWorkLocation(WorkLocation.getText().toString().trim());
                            model.setClientManager(ClientManager.getText().toString().trim());
                            model.setCstManager(CSTManager.getText().toString().trim());
                            model.setProjectName(ProjectName.getText().toString().trim());
                            model.setTaskDescription(TaskDescription.getText().toString());
                            //model.setDatePosted(date);
                            model.setWorkedHours(WorkHours.getText().toString());
                            multipleTimeSheet.add(model);
                            System.out.println("Error Values" + model);
                            dialog2 = dialog;
                            if (dialog2 != null)
                            {
                                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                dialog2.getWindow().setLayout(width, height);
                            }
                            dialog.show();
                            i=10;
                            j=i;
                        }

                    }
                }else {
                    if (Validations())
                        SummaryPostMethod();
                }
            }
        });

        PostDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showProgressDialog(getContext());

                multipleTimeSheetsList.setMultipletimesheet(multipleTimeSheet);

                restService.getService().AddNewMultipleTimesheets(UserId, multipleTimeSheetsList, new Callback<SummaryCreateTimesheetModel>() {
                    @Override
                    public void success(SummaryCreateTimesheetModel summaryCreateTimesheetModel, Response response) {

                        if (summaryCreateTimesheetModel.getMessage()!=null){
                            WorkLocation.setText("");
                            ClientManager.setText("");
                            CSTManager.setText("");
                            ProjectName.setText("");
                            TaskDescription.setText("");
                            WorkHours.setText("0");
                            SelectFromDate.setText("Select From Date");
                            SelectToDate.setText("Select To Date");
                            TotalInfoLayout.setVisibility(View.GONE);
                            DaywiseDateLayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            SelectFromDate.setClickable(true);
                            SelectToDate.setClickable(true);
                            DetailRadioButton.setClickable(true);
                            SummaryRadioButton.setClickable(true);
                            multipleTimeSheet.clear();
                            i=1;
                            j=1;
                            if (!summaryCreateTimesheetModel.getMessage().isEmpty()){
                                Utils.showAlertDialog(getActivity(), summaryCreateTimesheetModel.getMessage(), false);
                            }

                        }else {
                            Toast.makeText(getActivity(),summaryCreateTimesheetModel.getErrorMessage(),Toast.LENGTH_LONG).show();
                        }
                        Utils.dismissProgressDialog();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Utils.dismissProgressDialog();
                        Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();
                    }
                });

/*
               restService.getService().AddMultipleTimesheets(UserId, multipleTimeSheet, new Callback<SummaryCreateTimesheetModel>() {
                   @Override
                   public void success(SummaryCreateTimesheetModel summaryCreateTimesheetModel, Response response) {

                       if (summaryCreateTimesheetModel.getMessage()!=null){
                           WorkLocation.setText("");
                           ClientManager.setText("");
                           CSTManager.setText("");
                           ProjectName.setText("");
                           TaskDescription.setText("");
                           WorkHours.setText("0");
                           SelectFromDate.setText("Select From Date");
                           SelectToDate.setText("Select To Date");
                           TotalInfoLayout.setVisibility(View.GONE);
                           DaywiseDateLayout.setVisibility(View.GONE);
                           dialog.dismiss();
                           SelectFromDate.setClickable(true);
                           SelectToDate.setClickable(true);
                           DetailRadioButton.setClickable(true);
                           SummaryRadioButton.setClickable(true);
                           multipleTimeSheet.clear();
                           i=1;
                           j=1;
                           if (!summaryCreateTimesheetModel.getMessage().isEmpty()){
                               Utils.showAlertDialog(getActivity(), summaryCreateTimesheetModel.getMessage(), false);
                           }

                       }else {
                           Toast.makeText(getActivity(),summaryCreateTimesheetModel.getErrorMessage(),Toast.LENGTH_LONG).show();
                       }
                       Utils.dismissProgressDialog();
                   }

                   @Override
                   public void failure(RetrofitError error) {
                       Utils.dismissProgressDialog();
                       Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();

                   }
               });
*/
            }
        });

        return  view;
    }

    private boolean Validations() {
        if (!ValidateWorkLocation()){
            return false;
        }else if (!ValidateClientManager()){
            return false;
        }else if (!ValidateCSTManager()){
            return false;
        }else if (!ValidateProjectName()){
            return false;
        }else if (!ValidateTaskDescription()){
            return false;
        }else return ValidateWorkHours();

    }

    private boolean ValidateWorkLocation() {

        if (WorkLocation.getText().toString().trim().isEmpty()){
            WorkLocation.setError("Work Location should not be empty");
            WorkLocation.requestFocus();
            return false;

        }else {
            return true;
        }

    }

    private boolean ValidateCSTManager() {

        if (CSTManager.getText().toString().trim().isEmpty()){
            CSTManager.setError("CST Manager should not be empty");
            CSTManager.requestFocus();
            return false;

        }else return true;

    }

    private boolean ValidateClientManager() {
        if (ClientManager.getText().toString().trim().isEmpty()){

            ClientManager.setError("Client Manager should not be empty");
            ClientManager.requestFocus();
            return false;
        }else return true;

    }
    private boolean ValidateProjectName() {

        if (ProjectName.getText().toString().trim().isEmpty()){
            ProjectName.setError("Project Name should not be empty");
            ProjectName.requestFocus();
            return false;

        }else return true;
    }

    private boolean ValidateTaskDescription() {
        if (TaskDescription.getText().toString().trim().isEmpty()){

            TaskDescription.setError("Task Description should not be empty");
            TaskDescription.requestFocus();
            return false;
        }else return true;

    }
    private boolean ValidateWorkHours() {
        if (WorkHours.getText().toString().trim().isEmpty()){
            WorkHours.setError("Work Hours should not be empty");
            WorkHours.requestFocus();
            return false;
        }else if (WorkHours.getText().toString().equalsIgnoreCase("0")){
            WorkHours.setError("Work Hours Can't be Zero");
            WorkHours.requestFocus();
            return false;
        }else return true;
    }

    private void SummaryPostMethod() {
        Utils.showProgressDialog(getContext());
        SelectFromDateText = SelectFromDate.getText().toString().trim();
        SelectToDateText = SelectToDate.getText().toString().trim();
        WorkLocationText =WorkLocation.getText().toString().trim();
        ClientManagerText = ClientManager.getText().toString().trim();
        CSTManagerText =CSTManager.getText().toString().trim();
        ProjectNameText = ProjectName.getText().toString().trim();
        TaskDescriptionText = TaskDescription.getText().toString().trim();
        WorkHoursText =WorkHours.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String UserId = PreferenceConnector.readString(getActivity(),"UserId","");

        SummaryCreateTimesheetDataModel summaryCreateTimesheetDataModel = new SummaryCreateTimesheetDataModel();
        summaryCreateTimesheetDataModel.setId(UserId);
        summaryCreateTimesheetDataModel.setEmployee_Ref(UserId);
        summaryCreateTimesheetDataModel.setWorkLocation(WorkLocationText);
        summaryCreateTimesheetDataModel.setClientManager(ClientManagerText);
        summaryCreateTimesheetDataModel.setCstManager(CSTManagerText);
        summaryCreateTimesheetDataModel.setDateOn(SelectToDateText);
        summaryCreateTimesheetDataModel.setProjectName(ProjectNameText);
        summaryCreateTimesheetDataModel.setTaskDescription(TaskDescriptionText);
        summaryCreateTimesheetDataModel.setPeriodFrom(SelectFromDateText);
        summaryCreateTimesheetDataModel.setPeriodTo(SelectToDateText);
        summaryCreateTimesheetDataModel.setDatePosted(date);
        summaryCreateTimesheetDataModel.setEntryType("S");
        summaryCreateTimesheetDataModel.setWorkedHours(WorkHoursText);

        System.out.println("Error Id" + UserId  +WorkLocationText + ClientManagerText + CSTManagerText + ProjectNameText + TaskDescriptionText + WorkHoursText + SelectFromDateText +SelectToDateText + date );
        try {

            restService.getService().postSummaryCreateTimeSheet(UserId,summaryCreateTimesheetDataModel, new Callback<SummaryCreateTimesheetModel>() {
                @Override
                public void success(SummaryCreateTimesheetModel summaryCreateTimesheetModel, Response response) {
                    Message = summaryCreateTimesheetModel.getMessage();
                    DidError = summaryCreateTimesheetModel.getDidError();
                    ErrorMessage = summaryCreateTimesheetModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        Utils.dismissProgressDialog();
                        WorkLocation.setText("");
                        ClientManager.setText("");
                        CSTManager.setText("");
                        ProjectName.setText("");
                        TaskDescription.setText("");
                        WorkHours.setText("0");
                        SelectFromDate.setText("Select From Date");
                        SelectToDate.setText("Select To Date");
                        TotalInfoLayout.setVisibility(View.GONE);
                        SelectFromDate.setClickable(true);
                        SelectToDate.setClickable(true);
                        DetailRadioButton.setClickable(true);
                        SummaryRadioButton.setClickable(true);
                        if (Message.equalsIgnoreCase("Dates are already added")){
                            Utils.showAlertDialog(getActivity(), "Selected Dates are Already Added!", false);
                        }else{
                            Utils.showAlertDialog(getActivity(), "Timesheet Added Succesfully!", false);
                        }


                    } else {
                        Utils.dismissProgressDialog();
                        Utils.showAlertDialog(getActivity(), ErrorMessage, false);
                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime2.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMethodCallback() {

    }
}
