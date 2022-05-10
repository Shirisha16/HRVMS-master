package com.cstech.hrvms.AdminFragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Adapters.SpinnerAdapterForEducations;
import com.cstech.hrvms.Adapters.SpinnerAdapterForJobTitle;
import com.cstech.hrvms.Adapters.SpinnerAdapterForSecurityAccess;
import com.cstech.hrvms.DataModels.EmployeeAddDataModel;
import com.cstech.hrvms.DataModels.GetEducationsDataModel;
import com.cstech.hrvms.DataModels.GetJobTitlesDataModel;
import com.cstech.hrvms.DataModels.GetSecurityProfileDataModel;
import com.cstech.hrvms.Models.EmployeeAddModel;
import com.cstech.hrvms.Models.GetEducationsModel;
import com.cstech.hrvms.Models.GetJobTitlesModel;
import com.cstech.hrvms.Models.GetSecurityProfileModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.app.Activity.RESULT_OK;

public class AddEmployeeFragment extends Fragment {
    EditText EmployeeCode,FirstName,LastName,SSN,DrivingLicenseNumber,TechnicalSkills,Street,City,State,Zip,Country,AddressPhone,
            Fax,PassPortNumber,PassPortExpiry,VisaNumber,VisExpiry,CstechInsuranceText,GreenCardText,Specialization,Salary,BankName,BankAccountNumber,RoutingNumber,
            LoginName,Password,ConfirmPassword;
    Spinner SexSpinner,MaritalStatusSpinner,JobTitle,VisaTypeSpinner,Education,SecurityAccess;
    CheckBox CstechInsuranceCheckbox,GreenCardCheckbox;
    ImageView AddressAdd,AddressSub,PassportAdd,PassportSub,BankDetailsAdd,BankDetailsSub,SecurityAccessAdd,SecurityAccessSub;
    TextView Dob,DateOfJoining,DateOfLeaving;
    Button Submit;
    LinearLayout PassportInfoLinearLayout,AddressLinearLayout,BankInfoLinearLayout,LoginInfoLinearLayout;
    String SexList[],MartialStatusList[],VisaTypeList[],Message,DidError,ErrorMessage,Variation,GreenCardSelection,CstechInsuranceSelection,encodedImage;
    RestService restService;
    List<GetEducationsDataModel> getEducationsModel;
    List<GetJobTitlesDataModel> getJobTitlesDataModel;
    List<GetSecurityProfileDataModel> getSecurityProfileModel;
    SpinnerAdapterForEducations spinnerAdapterForEducations;
    SpinnerAdapterForJobTitle spinnerAdapterForJobTitle;
    SpinnerAdapterForSecurityAccess spinnerAdapterForSecurityAccess;
    Calendar calendar;
    int SecurityAccessId;
    ImageView EmployeeProfilePic;
    Bitmap profilePic;
    private final static int SELECT_PHOTO = 12345;
    public AddEmployeeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addemployee_fragment,container,false);

        EmployeeCode = view.findViewById(R.id.EmployeeCode);
        FirstName = view.findViewById(R.id.FirstName);
        LastName = view.findViewById(R.id.LastName);
        SSN = view.findViewById(R.id.SSN);
        Dob = view.findViewById(R.id.Dob);
        DateOfJoining = view.findViewById(R.id.DateOfJoining);
        DateOfLeaving = view.findViewById(R.id.DateOfLeaving);
        DrivingLicenseNumber = view.findViewById(R.id.DrivingLicenseNumber);
        TechnicalSkills = view.findViewById(R.id.TechnicalSkills);
        Street = view.findViewById(R.id.Street);
        City = view.findViewById(R.id.City);
        State = view.findViewById(R.id.State);
        Zip = view.findViewById(R.id.Zip);
        Country = view.findViewById(R.id.Country);
        AddressPhone = view.findViewById(R.id.AddressPhone);
        Fax = view.findViewById(R.id.Fax);
        PassPortNumber = view.findViewById(R.id.PassPortNumber);
        PassPortExpiry = view.findViewById(R.id.PassPortExpiry);
        VisaNumber = view.findViewById(R.id.VisaNumber);
        VisExpiry = view.findViewById(R.id.VisExpiry);
        CstechInsuranceText = view.findViewById(R.id.CstechInsuranceText);
        GreenCardText = view.findViewById(R.id.GreenCardText);
        Specialization = view.findViewById(R.id.Specialization);
        Salary = view.findViewById(R.id.Salary);
        BankName = view.findViewById(R.id.BankName);
        BankAccountNumber = view.findViewById(R.id.BankAccountNumber);
        RoutingNumber = view.findViewById(R.id.RoutingNumber);
        SecurityAccess = view.findViewById(R.id.SecurityAccess);
        Password = view.findViewById(R.id.Password);
        ConfirmPassword = view.findViewById(R.id.ConfirmPassword);
        SexSpinner = view.findViewById(R.id.SexSpinner);
        MaritalStatusSpinner = view.findViewById(R.id.MaritalStatusSpinner);
        JobTitle = view.findViewById(R.id.JobTitle);
        VisaTypeSpinner = view.findViewById(R.id.VisaTypeSpinner);
        Education = view.findViewById(R.id.Education);
        LoginName = view.findViewById(R.id.LoginName);
        CstechInsuranceCheckbox = view.findViewById(R.id.CstechInsuranceCheckbox);
        GreenCardCheckbox = view.findViewById(R.id.GreenCardCheckbox);
        AddressAdd = view.findViewById(R.id.AddressAdd);
        AddressSub = view.findViewById(R.id.AddressSub);
        PassportAdd = view.findViewById(R.id.PassportAdd);
        PassportSub = view.findViewById(R.id.PassportSub);
        BankDetailsAdd = view.findViewById(R.id.BankDetailsAdd);
        BankDetailsSub = view.findViewById(R.id.BankDetailsSub);
        SecurityAccessAdd = view.findViewById(R.id.SecurityAccessAdd);
        SecurityAccessSub = view.findViewById(R.id.SecurityAccessSub);
        Submit = view.findViewById(R.id.Submit);
        EmployeeProfilePic = view.findViewById(R.id.ProfilePic);
        PassportInfoLinearLayout = view.findViewById(R.id.PassportInfoLinearLayout);
        AddressLinearLayout = view.findViewById(R.id.AddressLinearLayout);
        BankInfoLinearLayout = view.findViewById(R.id.BankInfoLinearLayout);
        LoginInfoLinearLayout = view.findViewById(R.id.LoginInfoLinearLayout);
        SexList = getResources().getStringArray(R.array.Sex);
        MartialStatusList = getResources().getStringArray(R.array.MartialStatus);
        VisaTypeList = getResources().getStringArray(R.array.VisaType);
        calendar =Calendar.getInstance();

        AddressLinearLayout.setVisibility(View.GONE);
        PassportInfoLinearLayout.setVisibility(View.GONE);
        BankInfoLinearLayout.setVisibility(View.GONE);
        LoginInfoLinearLayout.setVisibility(View.GONE);

        CstechInsuranceCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CstechInsuranceCheckbox.isChecked()){
                    CstechInsuranceSelection = "Y";
                    CstechInsuranceText.setVisibility(View.VISIBLE);
                }else if (!CstechInsuranceCheckbox.isChecked()){
                    CstechInsuranceSelection = "N";
                    CstechInsuranceText.setVisibility(View.GONE);
                }
            }
        });

        GreenCardCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GreenCardCheckbox.isChecked()){
                    GreenCardSelection = "Y";
                    GreenCardText.setVisibility(View.VISIBLE);
                }else if (!GreenCardCheckbox.isChecked()){
                    GreenCardSelection = "N";
                    GreenCardText.setVisibility(View.GONE);
                }
            }
        });


        restService = new RestService();

        getEducationsModel = new ArrayList<>();
        getJobTitlesDataModel = new ArrayList<>();
        getSecurityProfileModel = new ArrayList<>();
        getEducations();
        getJobTitle();
        getSecurityDetails();



        AddressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressLinearLayout.setVisibility(View.VISIBLE);
                AddressAdd.setVisibility(View.GONE);
                AddressSub.setVisibility(View.VISIBLE);
            }
        });
        AddressSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressLinearLayout.setVisibility(View.GONE);
                AddressAdd.setVisibility(View.VISIBLE);
                AddressSub.setVisibility(View.GONE);
            }
        });
        PassportAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassportInfoLinearLayout.setVisibility(View.VISIBLE);
                PassportAdd.setVisibility(View.GONE);
                PassportSub.setVisibility(View.VISIBLE);
            }
        });
        PassportSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassportInfoLinearLayout.setVisibility(View.GONE);
                PassportAdd.setVisibility(View.VISIBLE);
                PassportSub.setVisibility(View.GONE);
            }
        });
        BankDetailsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankInfoLinearLayout.setVisibility(View.VISIBLE);
                BankDetailsAdd.setVisibility(View.GONE);
                BankDetailsSub.setVisibility(View.VISIBLE);
            }
        });
        BankDetailsSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankInfoLinearLayout.setVisibility(View.GONE);
                BankDetailsAdd.setVisibility(View.VISIBLE);
                BankDetailsSub.setVisibility(View.GONE);
            }
        });
        SecurityAccessAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInfoLinearLayout.setVisibility(View.VISIBLE);
                SecurityAccessAdd.setVisibility(View.GONE);
                SecurityAccessSub.setVisibility(View.VISIBLE);
            }
        });
        SecurityAccessSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInfoLinearLayout.setVisibility(View.GONE);
                SecurityAccessAdd.setVisibility(View.VISIBLE);
                SecurityAccessSub.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<String> ListAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout,R.id.text,SexList);
        SexSpinner.setAdapter(ListAdapter);

        ArrayAdapter<String> ListAdapter1 = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout,R.id.text,MartialStatusList);
        MaritalStatusSpinner.setAdapter(ListAdapter1);

        ArrayAdapter<String> ListAdapter2 = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout,R.id.text,VisaTypeList);
        VisaTypeSpinner.setAdapter(ListAdapter2);

        SecurityAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               SecurityAccessId = getSecurityProfileModel.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        EmployeeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  HAS_IMAGAE = false;
                Intent i = new
                        Intent(Intent.ACTION_PICK, android.provider.MediaStore
                        .Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, SELECT_PHOTO);
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
                if (Variation.equalsIgnoreCase("Dob")) {
                    Dob.setText(sdf.format(calendar.getTime()));

                } else if (Variation.equalsIgnoreCase("DateOfJoining")) {
                    DateOfJoining.setText(sdf.format(calendar.getTime()));

                }else if (Variation.equalsIgnoreCase("DateOfLeaving")) {
                    DateOfJoining.setText(sdf.format(calendar.getTime()));

                }

            }
        };
        try {
            Dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "Dob";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            DateOfJoining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "DateOfJoining";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });

            DateOfLeaving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "DateOfLeaving";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });
        }catch (Exception e){

        }

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEmployee();
            }
        });




        return view;
    }

    private void AddEmployee() {
        String UserId = PreferenceConnector.readString(getContext(),"UserId","");
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        EmployeeAddDataModel employeeAddDataModel = new EmployeeAddDataModel();
        employeeAddDataModel.setID(Integer.parseInt(UserId));
        employeeAddDataModel.setFirstName(FirstName.getText().toString());
        employeeAddDataModel.setLastName(LastName.getText().toString());
//        employeeAddDataModel.setSSN();
        employeeAddDataModel.setSex(SexSpinner.getSelectedItem().toString());
        employeeAddDataModel.setDOB(Dob.getText().toString());
        employeeAddDataModel.setDOJoining(DateOfJoining.getText().toString());
        employeeAddDataModel.setDOLeaving(DateOfLeaving.getText().toString());
        employeeAddDataModel.setMaritalStatus(MaritalStatusSpinner.getSelectedItem().toString());
        employeeAddDataModel.setStreet(Street.getText().toString());
        employeeAddDataModel.setCity(City.getText().toString());
        employeeAddDataModel.setState(State.getText().toString());
        employeeAddDataModel.setZip(Zip.getText().toString());
        employeeAddDataModel.setCountry(Country.getText().toString());
        employeeAddDataModel.setPhone(AddressPhone.getText().toString());
//        employeeAddDataModel.setEmail();
        employeeAddDataModel.setPassportNumber(PassPortNumber.getText().toString());
        employeeAddDataModel.setPassportExpiry(PassPortExpiry.getText().toString());
        employeeAddDataModel.setVisaNumber(VisaNumber.getText().toString());
        employeeAddDataModel.setVisaExpiry(VisExpiry.getText().toString());
        employeeAddDataModel.setIsGreenCardHolder(GreenCardSelection);
        employeeAddDataModel.setGreenCardNumber(GreenCardText.getText().toString());
        employeeAddDataModel.setIsCSTechInsurance(CstechInsuranceSelection);
        employeeAddDataModel.setHealthInsuranceNumber(CstechInsuranceText.getText().toString());
        employeeAddDataModel.setDrivingLicenseNumber(DrivingLicenseNumber.getText().toString());
        employeeAddDataModel.setBankName(BankName.getText().toString());
        employeeAddDataModel.setEducation(Education.getSelectedItem().toString());
//        employeeAddDataModel.setPhoto(encodedImage);
        employeeAddDataModel.setIsActive("Y");
        employeeAddDataModel.setLoginUser(LoginName.getText().toString());
        employeeAddDataModel.setLoginPassword(ConfirmPassword.getText().toString());
        employeeAddDataModel.setCreatedBy(Integer.parseInt(UserId));
        employeeAddDataModel.setLastChangedBy(Integer.parseInt(UserId));
        employeeAddDataModel.setCreatedOn(date);
        employeeAddDataModel.setLastChangedOn(date);
        employeeAddDataModel.setVisaType(VisaTypeSpinner.getSelectedItem().toString());
        employeeAddDataModel.setVisaType_Other("");
        employeeAddDataModel.setJobTitle(JobTitle.getSelectedItem().toString());
        employeeAddDataModel.setJobTitle_Other("");
        employeeAddDataModel.setEducationType(Education.getSelectedItem().toString());
        employeeAddDataModel.setTechSkill(TechnicalSkills.getText().toString());
        employeeAddDataModel.setSalary(Salary.getText().toString());
        employeeAddDataModel.setEmpCode(EmployeeCode.getText().toString());
        employeeAddDataModel.setSSN2("");
        employeeAddDataModel.setSecurityProfile_Ref(SecurityAccessId);





        restService.getService().PostAddEmployee(employeeAddDataModel, new Callback<EmployeeAddModel>() {
            @Override
            public void success(EmployeeAddModel employeeAddModel, Response response) {

                Message = employeeAddModel.getMessage();
                DidError = employeeAddModel.getDidError();
                ErrorMessage = employeeAddModel.getErrorMessage();


                if (!Message.isEmpty()) {

                    Utils.showAlertDialog(getActivity(), "Employee Added Successfully!", false);

                } else {

                    Utils.showAlertDialog(getActivity(), ErrorMessage, false);
                }
                Utils.dismissProgressDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissProgressDialog();
                Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void getEducations(){
        try {

            restService.getService().GetEducations(new Callback<GetEducationsModel>() {
                @Override
                public void success(GetEducationsModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {

                        getEducationsModel = usersModel.getModel();
                        if (getEducationsModel != null) {
                            spinnerAdapterForEducations = new SpinnerAdapterForEducations(getActivity(), R.id.text, getEducationsModel);
                            Education.setAdapter(spinnerAdapterForEducations);
                        }else{
                            Utils.showAlertDialog(getActivity(), "No Data Found", false);
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

    public void getJobTitle(){
        try {

            restService.getService().GetJobTitles(new Callback<GetJobTitlesModel>() {
                @Override
                public void success(GetJobTitlesModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {

                        getJobTitlesDataModel = usersModel.getModel();
                        if (getJobTitlesDataModel != null) {
                            spinnerAdapterForJobTitle = new SpinnerAdapterForJobTitle(getActivity(), R.id.text, getJobTitlesDataModel);
                            JobTitle.setAdapter(spinnerAdapterForJobTitle);
                        }else{
                            Utils.showAlertDialog(getActivity(), "No Data Found", false);
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

    public void getSecurityDetails(){
        try {

            restService.getService().GetSecurityProfile(new Callback<GetSecurityProfileModel>() {
                @Override
                public void success(GetSecurityProfileModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {

                        getSecurityProfileModel = usersModel.getModel();
                        if (getSecurityProfileModel != null) {
                            spinnerAdapterForSecurityAccess = new SpinnerAdapterForSecurityAccess(getActivity(), R.id.text, getSecurityProfileModel);
                            SecurityAccess.setAdapter(spinnerAdapterForSecurityAccess);
                        }else{
                            Utils.showAlertDialog(getActivity(), "No Data Found", false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        String imagepath = imageUri.getPath().toString();


                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(imageUri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imagepath = cursor.getString(columnIndex);
                        cursor.close();
                        //Toast.makeText(PersonalDetails.this,imagepath, Toast.LENGTH_SHORT).show();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        EmployeeProfilePic.setImageBitmap(selectedImage);

                        encodedImage = BitMapToString(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public void onResume(){
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {

                if (keyEvent.getAction()==KeyEvent.ACTION_DOWN){

                    if (keycode==KeyEvent.KEYCODE_BACK){
                        if (profilePic!=null){
                            EmployeeProfilePic.setImageBitmap(profilePic);
                            encodedImage = BitMapToString(profilePic);
                            PreferenceConnector.writeString(getActivity(),"EmployeeProfilePic", encodedImage);
                        }
                        Utils.navigationFragmentAdd(getActivity(),new AdminHomeScreenFragment(),"Home",null);
                        return true;
                    }
                }
                return false;
            }
        });
    }



}
