package com.cstech.hrvms.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.DataModels.ChangePasswordDataModel;
import com.cstech.hrvms.Models.ChangePasswordModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePasswordFragment extends Fragment {

    TextInputEditText ConfirmPassword,Password,OldPassword;
    Button Confirm;
    RestService restService;
    String Message,ErrorMessage,DidError;

    public ChangePasswordFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Type = PreferenceConnector.readString(getContext(),"Type","");
        if (Type.equals("Employee")){
            ((UserSlideMenu) getActivity())
                    .setActionBarTitle("Change Password");
        }else {
            ((AdminSlideMenu) getActivity())
                    .setActionBarTitle("Change Password");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_change_password,container,false);
        ConfirmPassword = view.findViewById(R.id.ConfirmPassword);
        Password = view.findViewById(R.id.Password);
        OldPassword = view.findViewById(R.id.OldPassword);
        Confirm = view.findViewById(R.id.Confirm);
        restService = new RestService();

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (OldPassword.getText().toString().isEmpty()) {
                    OldPassword.setError("Old Password Can't be Empty!");
                    OldPassword.requestFocus();
                } else {
                    String PasswordValue = PreferenceConnector.readString(getContext(), "Password", "");
                    if (OldPassword.getText().toString().equalsIgnoreCase(PasswordValue)){
                        if (Password.getText().toString().isEmpty()){
                            Utils.showAlertDialog(getContext(), "Enter New Password!", false);
                        }else if (ConfirmPassword.getText().toString().isEmpty()){
                            Utils.showAlertDialog(getContext(), "Enter Confirm Password!", false);
                        }
                        if (OldPassword.getText().toString().equalsIgnoreCase(ConfirmPassword.getText().toString())) {
                            Utils.showAlertDialog(getContext(), "Old Password and New Password Can't be same!", false);
                        }else{
                            if (Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
                                if (Password.getText().toString().isEmpty() || ConfirmPassword.getText().toString().isEmpty()){

                                }else {
                                    UpdatePassword();
                                }
                            }else{
                                if (!ConfirmPassword.getText().toString().isEmpty()){
                                    Utils.showAlertDialog(getContext(), "New Password and Confirm Password doesn't Match!", false);
                                }
                            }
                        }

                    }else if(!OldPassword.getText().toString().equals(PasswordValue)){
                        OldPassword.setError("Enter Correct Old Password");
                        OldPassword.requestFocus();
                        Password.setText("");
                        ConfirmPassword.setText("");
                        Utils.showAlertDialog(getContext(), "Invalid Old Password", false);
                    }
                }
            }
        });

        return view;
    }

    private void UpdatePassword() {
        Utils.showProgressDialog(getContext());
        String UserId = PreferenceConnector.readString(getContext(),"UserId","");
        System.out.println("Error" + UserId + ConfirmPassword.getText().toString());
        ChangePasswordDataModel changePasswordDataModel = new ChangePasswordDataModel();
        changePasswordDataModel.setId(UserId);
        changePasswordDataModel.setLoginPassword(ConfirmPassword.getText().toString());


        restService.getService().UpdatePassword(changePasswordDataModel, new Callback<ChangePasswordModel>() {
            @Override
            public void success(ChangePasswordModel changePasswordModel, Response response) {

                Message = changePasswordModel.getMessage();
                DidError = changePasswordModel.getDidError();
                ErrorMessage = changePasswordModel.getErrorMessage();

                if (!Message.isEmpty()) {
                    PreferenceConnector.writeString(getActivity(), "Password", ConfirmPassword.getText().toString());
                    OldPassword.setText("");
                    Password.setText("");
                    ConfirmPassword.setText("");
                    Utils.showAlertDialog(getContext(), "Password Changed Successfully", false);

                } else {
                    Utils.showAlertDialog(getContext(), ErrorMessage, false);
                }
                Utils.dismissProgressDialog();

            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissProgressDialog();
                Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
