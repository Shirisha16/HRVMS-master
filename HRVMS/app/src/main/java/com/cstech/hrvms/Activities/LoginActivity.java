package com.cstech.hrvms.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstech.hrvms.Models.ForgetPasswordModel;
import com.cstech.hrvms.Models.GetCountry;
import com.cstech.hrvms.Models.UserLoginModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.CountryService;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Session;
import com.cstech.hrvms.Supporters.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    Button LogIn;
    CheckBox RememberMe;
    TextInputEditText Password;
    TextInputEditText UserId;
    String UserIdText,PasswordText,IsRememberValue,UserIdResponse,errorMessage,DidError;
    Session session;
    RestService service;
    CountryService Cservice;
    TextView ForgetPassword;
    String currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogIn = (Button)findViewById(R.id.LogIn);
        RememberMe = (CheckBox) findViewById(R.id.rememberme);
        UserId = (TextInputEditText) findViewById(R.id.userId);
        Password = (TextInputEditText) findViewById(R.id.password);
        ForgetPassword =findViewById(R.id.ForgetPassword);

        session=new Session(getApplicationContext());
        service=new RestService();
        Cservice=new CountryService();
       // currentCountry = LoginActivity.this.getResources().getConfiguration().locale.getDisplayCountry();

//http://ip-api.com/json

        Cservice.getService().getCountry(new Callback<GetCountry>() {
            @Override
            public void success(GetCountry getCountry, Response response) {

                currentCountry=getCountry.getCountry();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        IsRememberValue = PreferenceConnector.readString(getApplicationContext(),"Remember me","");

        if (IsRememberValue.isEmpty()){
                //do Nothing
        }else {
            UserId.setText(IsRememberValue);
            RememberMe.setChecked(true);
        }

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validations()){
                    IsRemember();
                    LoginMethod();

                }

            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserId.getText().toString().isEmpty()){
                    UserId.setError("Please Enter your User name or Email Id");
                    UserId.requestFocus();
                }
                else{
                ForgetPasswordDetails();
                }
            }
        });

    }

    private void ForgetPasswordDetails() {
        Utils.showProgressDialog(LoginActivity.this);
        UserIdText = UserId.getText().toString().trim();
        service.getService().PostForgetPassword(UserIdText, new Callback<ForgetPasswordModel>() {
            @Override
            public void success(ForgetPasswordModel forgetPasswordModel, Response response) {
                DidError = forgetPasswordModel.getDidError();
                UserIdResponse = forgetPasswordModel.getMessage();
                errorMessage = forgetPasswordModel.getErrorMessage();


                if (UserIdResponse != null){
                    Utils.showAlertDialog(LoginActivity.this,"Please Check your Email Id, We have Sent your Password!",false);
                }else {
                    Utils.showAlertDialog(LoginActivity.this,errorMessage,false);
                }
                Utils.dismissProgressDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissProgressDialog();
                Toast.makeText(LoginActivity.this, "Server Failure! Please Try After Sometime", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void LoginMethod() {
        Utils.showProgressDialog(LoginActivity.this);
        UserIdText = UserId.getText().toString().trim();
        PasswordText = Password.getText().toString();
        try{

        service.getService().getLogin(UserIdText, PasswordText, new Callback<UserLoginModel>() {
            @Override
            public void success(UserLoginModel loginData, Response response) {
                UserIdResponse=loginData.getMessage();
                if (UserIdResponse != null) {

                    DidError=loginData.getDidError();
                    if (!UserIdResponse.isEmpty()) {

                        int UserId = loginData.getModel().get(0).getId();
                        String Designation = loginData.getModel().get(0).getDesignation();
                        String isActive = loginData.getModel().get(0).getIsActive();
                        String country = loginData.getModel().get(0).getCountry();
                        System.out.println("Error" + UserId);
                        String User = Integer.toString(UserId);
                        PreferenceConnector.writeString(getApplicationContext(), "UserId", User);
                        PreferenceConnector.writeString(getApplicationContext(), "Type", Designation);
                        PreferenceConnector.writeString(getApplicationContext(), "Password", PasswordText);
                        PreferenceConnector.writeString(getApplicationContext(), "UserName", UserIdText);
                        PreferenceConnector.writeString(getApplicationContext(), "CurrentCountry", country);


                        if (country.equalsIgnoreCase(currentCountry)){

                            if (Designation.equalsIgnoreCase("Employee") && isActive.equalsIgnoreCase("Y")) {
                                session.setLoging(true);
                                Intent intent = new Intent(getApplicationContext(), UserSlideMenu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else if (Designation.equalsIgnoreCase("Admin") && isActive.equalsIgnoreCase("Y")) {
                                session.setLoging(true);
                                Intent intent = new Intent(getApplicationContext(), AdminSlideMenu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }else {

                            Utils.showAlertDialog(LoginActivity.this, "You can't login using "+country+" credentials "+"In "+currentCountry,false);
                        }
                        Utils.dismissProgressDialog();


                    }
                }else {
                    errorMessage=loginData.getErrorMessage();
                    Utils.showAlertDialog(LoginActivity.this, errorMessage,false);
                    Utils.dismissProgressDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginActivity.this, "Server Failure! Please Try After Sometime." + error, Toast.LENGTH_SHORT).show();
                Utils.dismissProgressDialog();
            }
        });
        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(LoginActivity.this, "Server Failure! Please Try After Sometime2.", Toast.LENGTH_SHORT).show();
        }
    }


    public void IsRemember(){
        if (RememberMe.isChecked()){
            PreferenceConnector.writeString(getApplicationContext(),"Remember me",UserId.getText().toString());

        }else{
            PreferenceConnector.writeString(getApplicationContext(),"Remember me","");
        }
    }

    private boolean Validations() {
        try{
    if (!ValidateUserName()){
        System.out.println("Error values3");
            return false;
        }else if (!ValidatePassword()){
            return false;
        }
        }catch (Exception e){

        }
            return true;
    }

    private boolean ValidatePassword() {

        if (Password.getText().toString().trim().isEmpty()){
            Password.setError("Password should not be empty");
            Password.requestFocus();
            return false;

        }else {
            return true;
        }

    }

    private boolean ValidateUserName() {
        if (UserId.getText().toString().trim().isEmpty()){
            UserId.setError("Username should not be empty");
            UserId.requestFocus();
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
