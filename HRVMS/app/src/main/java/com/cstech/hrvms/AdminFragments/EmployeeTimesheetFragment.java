package com.cstech.hrvms.AdminFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Adapters.SpinnerAdapterForEmployee;
import com.cstech.hrvms.Adapters.EmployeeTimesheetAdapter;
import com.cstech.hrvms.DataModels.GetUsersDataModel;
import com.cstech.hrvms.DataModels.ViewTimeSheetDataModel;
import com.cstech.hrvms.Models.GetUsersModel;
import com.cstech.hrvms.Models.ViewTimeSheetModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EmployeeTimesheetFragment extends Fragment {
    ListView listView;
    Spinner EmployeeSpinner,TimePeriodSpinner;
    ArrayAdapter<GetUsersDataModel>  listArrayAdapter;
    String EmployeeId,Message,ErrorMessage,DidError,Variation = "",StartDateText,EndDateText,FinalStartDate,FinalEndDate;
    RestService restService;
    List<GetUsersDataModel> getUsersModel;
    SpinnerAdapterForEmployee spinnerAdapterForEmployee;
    List<ViewTimeSheetDataModel> list;
    public EmployeeTimesheetAdapter adapter;
    EditText Searching;
    TextView SelectCustomFromDate,SelectCustomToDate;
    ImageButton SearchButton;
    String[] TimePeriodList;
    LinearLayout CustomLinearLayout,SelectPeriodLinearLayout;
    Calendar calendar,mycal;

    public EmployeeTimesheetFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Employee Timesheet");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employeetimesheet_fragment,container,false);
        listView = view.findViewById(R.id.ListView);
        Searching = view.findViewById(R.id.Search);
        EmployeeSpinner = view.findViewById(R.id.EmployeeSpinner);
        TimePeriodSpinner = view.findViewById(R.id.TimePeriodSpinner);
        SelectCustomFromDate = view.findViewById(R.id.SelectCustomFromDate);
        SelectCustomToDate = view.findViewById(R.id.SelectCustomToDate);
        SearchButton = view.findViewById(R.id.SearchButton);
        CustomLinearLayout = view.findViewById(R.id.CustomLinearLayout);
        SelectPeriodLinearLayout = view.findViewById(R.id.SelectPeriodLinearLayout);
        TimePeriodList = getResources().getStringArray(R.array.timeperiod);
        restService = new RestService();
        getUsersModel = new ArrayList<>();
        list = new ArrayList<>();


        SearchButton.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        Searching.setVisibility(View.GONE);
        SelectPeriodLinearLayout.setVisibility(View.GONE);
        CustomLinearLayout.setVisibility(View.GONE);


        mycal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                datePicker.setMaxDate(System.currentTimeMillis());
                datePicker.setMaxDate(System.currentTimeMillis());

                mycal.set(java.util.Calendar.YEAR,year);
                mycal.set(java.util.Calendar.MONTH,month);
                mycal.set(java.util.Calendar.DAY_OF_MONTH,day);

                String format="yyyy-MM-dd";
                SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
                if (Variation.equalsIgnoreCase("SelectFromDate")) {
                    SelectCustomFromDate.setText(sdf.format(mycal.getTime()));

                } else if (Variation.equalsIgnoreCase("SelectToDate")) {
                    SelectCustomToDate.setText(sdf.format(mycal.getTime()));

                }

            }
        };
        try {
            SelectCustomFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "SelectFromDate";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, mycal.get(Calendar.YEAR), mycal.get(Calendar.MONTH), mycal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            SelectCustomToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "SelectToDate";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, mycal.get(Calendar.YEAR), mycal.get(Calendar.MONTH), mycal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
        }catch (Exception e){

        }

        Searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (adapter!=null){
                    adapter.filter(editable.toString());
                }

            }
        });

        try{
            getUsers();
            listArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_layout,R.id.text,getUsersModel);
            EmployeeSpinner.setAdapter(listArrayAdapter);
        }catch (Exception e){

        }

        EmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmployeeId = getUsersModel.get(position).getId();
                if (EmployeeId.equalsIgnoreCase("0")){
                    SelectPeriodLinearLayout.setVisibility(View.GONE);
                }else {
                    SelectPeriodLinearLayout.setVisibility(View.VISIBLE);
                }

                TimePeriodSpinner.setSelection(0);
                listView.setVisibility(View.GONE);
                CustomLinearLayout.setVisibility(View.GONE);
                SearchButton.setVisibility(View.GONE);
                Searching.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showProgressDialog(getContext());
                list.clear();
                FinalStartDate = SelectCustomFromDate.getText().toString();
                FinalEndDate = SelectCustomToDate.getText().toString();
                System.out.println("Error V" + FinalStartDate + FinalEndDate);
                getTimesheetDetails();
                listView.setVisibility(View.VISIBLE);
                Searching.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> ListAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout,R.id.text,TimePeriodList);
        TimePeriodSpinner.setAdapter(ListAdapter);

        TimePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String Selected = TimePeriodSpinner.getSelectedItem().toString();
                if (Selected.equalsIgnoreCase("3 Months")){
                    list.clear();
                    CustomLinearLayout.setVisibility(View.GONE);
                    SearchButton.setVisibility(View.GONE);
                    MainLogicForThreeMonths();
                    System.out.println("Error For " + StartDateText + EndDateText);
                    String[] separated = StartDateText.split("/");
                    FinalStartDate = separated[2].trim()  + "-"+ separated[0].trim() + "-"+separated[1].trim();
                    String[] separated1 = EndDateText.split("/");
                    FinalEndDate = separated1[2].trim() + "-" +separated1[0].trim()+"-"+separated1[1].trim();
                    Utils.showProgressDialog(getContext());
                    getTimesheetDetails();
                    listView.setVisibility(View.VISIBLE);
                    Searching.setVisibility(View.VISIBLE);

                }
                if (Selected.equalsIgnoreCase("6 Months")){
                    list.clear();
                    CustomLinearLayout.setVisibility(View.GONE);
                    SearchButton.setVisibility(View.GONE);
                    mainLogicForSixMonths();
                    System.out.println("Error For 6" + StartDateText + EndDateText);
                    String[] separated = StartDateText.split("/");
                    FinalStartDate = separated[2].trim()  + "-"+ separated[0].trim() + "-"+separated[1].trim();
                    String[] separated1 = EndDateText.split("/");
                    FinalEndDate = separated1[2].trim() + "-" +separated1[0].trim()+"-"+separated1[1].trim();
                    System.out.println("Error For 6 V" + StartDateText + EndDateText);
                    Utils.showProgressDialog(getContext());
                    getTimesheetDetails();
                    listView.setVisibility(View.VISIBLE);
                    Searching.setVisibility(View.VISIBLE);

                }
                if (Selected.equalsIgnoreCase("9 Months")){
                    list.clear();
                    CustomLinearLayout.setVisibility(View.GONE);
                    SearchButton.setVisibility(View.GONE);
                    mainLogicForNineMonths();
                    System.out.println("Error For 9" + StartDateText + EndDateText);
                    String[] separated = StartDateText.split("/");
                    FinalStartDate = separated[2].trim()  + "-"+ separated[0].trim() + "-"+separated[1].trim();
                    String[] separated1 = EndDateText.split("/");
                    FinalEndDate = separated1[2].trim() + "-" +separated1[0].trim()+"-"+separated1[1].trim();
                    System.out.println("Error For 9 V" + FinalStartDate + FinalEndDate);
                    Utils.showProgressDialog(getContext());
                    getTimesheetDetails();
                    listView.setVisibility(View.VISIBLE);
                    Searching.setVisibility(View.VISIBLE);

                }
                if (Selected.equalsIgnoreCase("Custom Date")){
                    CustomLinearLayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    SearchButton.setVisibility(View.VISIBLE);
                    Searching.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void getTimesheetDetails() {
        try {

            restService.getService().ViewTimeSheet(EmployeeId,FinalStartDate,FinalEndDate, new Callback<ViewTimeSheetModel>() {
                @Override
                public void success(ViewTimeSheetModel aa, Response response) {
                    try {


                        list = aa.getModel();
                        if (getActivity() != null) {

                            if (list.size() > 0) {
                                adapter = new EmployeeTimesheetAdapter(getActivity(), list);
                                listView.setAdapter(adapter);
                            } else {

                                if (list.size() == 0) {
                                    Utils.showAlertDialog(getActivity(), "No Data Available", false);
                                } else {
                                    Utils.showAlertDialog(getActivity(), "No Data Available", false);
                                }
                                Searching.setVisibility(View.GONE);
                            }
                            Utils.dismissProgressDialog();
                        }
                    }catch (Exception e){
                        Utils.showAlertDialog(getActivity(), "No Data Available", false);
                        Utils.dismissProgressDialog();
                        Searching.setVisibility(View.GONE);
                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_LONG).show();

                }
            });
        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_LONG).show();
        }
    }

    public void getUsers(){
        try {

            restService.getService().GetUsers(new Callback<GetUsersModel>() {
                @Override
                public void success(GetUsersModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {
                        GetUsersDataModel getUsersDataModel = new GetUsersDataModel();
                        getUsersDataModel.setId("0");
                        getUsersDataModel.setUserName("Select Employee");
                        getUsersModel = usersModel.getModel();
                        getUsersModel.add(0,getUsersDataModel);
                        if (getUsersModel !=null) {
                            spinnerAdapterForEmployee = new SpinnerAdapterForEmployee(getActivity(), R.id.text, getUsersModel);
                            EmployeeSpinner.setAdapter(spinnerAdapterForEmployee);
                        }
                    } else {
                        Utils.showAlertDialog(getActivity(), ErrorMessage, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }


    public void MainLogicForThreeMonths(){
        int InternalMonthStartDate,InternalMonthEndDate;
        calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH) + 1 ;
        int Year = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, -1);
        int PreviousYear = calendar.get(Calendar.YEAR);
        System.out.println("Error Data Values" + Month + Year + PreviousYear);

        switch (Month){
            case 1 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 10 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = 12;
                EndDateText =  InternalMonthEndDate + "/31/" + PreviousYear;
                break;
            case 2 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 11 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH) ;
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 3 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 12 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH) ;
                if (checkLeapYear()){
                    EndDateText =  InternalMonthEndDate + "/29/" + Year;
                }else{
                    EndDateText =  InternalMonthEndDate + "/28/" + Year;
                }
                break;
            case 4 :
            case 11 :
            case 9 :
            case 8 :
            case 6 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 2 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 5 :
            case 12 :
            case 10 :
            case 7 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 2 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            default:
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

        }
    }


    public void mainLogicForSixMonths()
    {
        int InternalMonthStartDate,InternalMonthEndDate;
        calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH) + 1 ;
        int Year = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, -1);
        int PreviousYear = calendar.get(Calendar.YEAR);
        System.out.println("Error Data Values" + Month + Year + PreviousYear);

        switch (Month){
            case 1 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 7 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = 12;
                EndDateText =  InternalMonthEndDate + "/31/" + PreviousYear;
                break;
            case 2 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 8 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 3 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 9 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                if (checkLeapYear()){
                    EndDateText =  InternalMonthEndDate + "/29/" + Year;
                }else{
                    EndDateText =  InternalMonthEndDate + "/28/" + Year;
                }
                break;
            case 4 :
                InternalMonthStartDate = 10 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 5 :
                InternalMonthStartDate = 11 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            case 6 :
                InternalMonthStartDate = 12 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 7 :
            case 10 :
            case 12 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 5 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            case 8 :
            case 11 :
            case 9 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 5 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            default:
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

        }
    }



    public void mainLogicForNineMonths()
    {
        int InternalMonthStartDate,InternalMonthEndDate;
        calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH) + 1 ;
        int Year = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, -1);
        int PreviousYear = calendar.get(Calendar.YEAR);

        switch (Month){
            case 1 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 4 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = 12;
                EndDateText =  InternalMonthEndDate + "/31/" + PreviousYear;
                break;
            case 2 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 5 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 3 :
                calendar = Calendar.getInstance();
                InternalMonthStartDate = 6 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH) - 1;
                if (checkLeapYear()){
                    EndDateText =  InternalMonthEndDate + "/29/" + Year;
                }else{
                    EndDateText =  InternalMonthEndDate + "/28/" + Year;
                }
                break;
            case 4 :
                InternalMonthStartDate = 7 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 5 :
                InternalMonthStartDate = 8 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            case 6 :
                InternalMonthStartDate = 9 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 7 :
                InternalMonthStartDate = 10 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            case 8 :
                InternalMonthStartDate = 11 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 9 :
                InternalMonthStartDate = 12 ;
                StartDateText = InternalMonthStartDate + "/1/" + PreviousYear;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            case 10 :
            case 12 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 8 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/30/" + Year;
                break;
            case 11 :
                InternalMonthStartDate = calendar.get(Calendar.MONTH) - 8 ;
                StartDateText = InternalMonthStartDate + "/1/" + Year;
                InternalMonthEndDate = calendar.get(Calendar.MONTH);
                EndDateText =  InternalMonthEndDate + "/31/" + Year;
                break;
            default:
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

        }
    }

    private  boolean checkLeapYear() {
        int year = Calendar.getInstance().get(calendar.YEAR);

        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                // year is divisible by 400, hence the year is a leap year
                if ( year % 400 == 0)
                    return true;
                else
                    return false;
            }
            else
                return true;
        }
        else
            return false;
    }
}
