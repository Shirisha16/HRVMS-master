package com.cstech.hrvms.AdminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.DataModels.AddVendorDataModel;
import com.cstech.hrvms.Models.AddVendorModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddVendorFragment extends Fragment {

    LinearLayout AddressLinearLayout,SalesLinearLayout,AccountsLinearLayout;
    EditText CompanyName,EmailAddress,Street,City,State,Zip,Country,AddressPhone,Fax,SalesContactName,
            SalesPhone,SalesEmail,AccountsContactName,AccountsPhone,AccountsEmail;
    ImageView AddressAdd,AddressSub,SalesAdd,SalesSub,AccountsAdd,AccountsSub;
    Button Submit;
    String CompanyNameText,EmailAddressText,StreetText,CityText,StateText,ZipText,CountryText,AddressPhoneText,FaxText,SalesContactNameText,
            SalesPhoneText,SalesEmailText,AccountsContactNameText,AccountsPhoneText,AccountsEmailText,Message,ErrorMessage,DidError;
    RestService restService;



    public AddVendorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Add Vendor");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.addvendor_fragment,container,false);
        AddressLinearLayout = view.findViewById(R.id.AddressLinearLayout);
        SalesLinearLayout = view.findViewById(R.id.SalesLinearLayout);
        AccountsLinearLayout = view.findViewById(R.id.AccountsLinearLayout);
        CompanyName = view.findViewById(R.id.CompanyName);
        EmailAddress = view.findViewById(R.id.EmailAddress);
        Street = view.findViewById(R.id.Street);
        City = view.findViewById(R.id.City);
        State = view.findViewById(R.id.State);
        Zip = view.findViewById(R.id.Zip);
        Country = view.findViewById(R.id.Country);
        AddressPhone = view.findViewById(R.id.AddressPhone);
        Fax = view.findViewById(R.id.Fax);
        SalesContactName = view.findViewById(R.id.SalesContactName);
        SalesPhone = view.findViewById(R.id.SalesPhone);
        SalesEmail = view.findViewById(R.id.SalesEmail);
        AccountsContactName = view.findViewById(R.id.AccountsContactName);
        AccountsPhone = view.findViewById(R.id.AccountsPhone);
        AccountsEmail = view.findViewById(R.id.AccountsEmail);
        AddressAdd = view.findViewById(R.id.AddressAdd);
        AddressSub = view.findViewById(R.id.AddressSub);
        SalesAdd = view.findViewById(R.id.SalesAdd);
        SalesSub = view.findViewById(R.id.SalesSub);
        AccountsAdd = view.findViewById(R.id.AccountsAdd);
        AccountsSub = view.findViewById(R.id.AccountsSub);
        Submit = view.findViewById(R.id.Submit);

        restService = new RestService();

        AddressLinearLayout.setVisibility(View.GONE);
        SalesLinearLayout.setVisibility(View.GONE);
        AccountsLinearLayout.setVisibility(View.GONE);

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

        SalesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalesLinearLayout.setVisibility(View.VISIBLE);
                SalesAdd.setVisibility(View.GONE);
                SalesSub.setVisibility(View.VISIBLE);
            }
        });
        SalesSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalesLinearLayout.setVisibility(View.GONE);
                SalesAdd.setVisibility(View.VISIBLE);
                SalesSub.setVisibility(View.GONE);
            }
        });

        AccountsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountsLinearLayout.setVisibility(View.VISIBLE);
                AccountsAdd.setVisibility(View.GONE);
                AccountsSub.setVisibility(View.VISIBLE);
            }
        });
        AccountsSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountsLinearLayout.setVisibility(View.GONE);
                AccountsAdd.setVisibility(View.VISIBLE);
                AccountsSub.setVisibility(View.GONE);
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validations()){
                    if (!SalesEmail.getText().toString().isEmpty()){
                        if (ValidateEmailAddress()){
                            if (!AccountsEmail.getText().toString().isEmpty()){
                                if (ValidateEmailAddress()){
                                    Utils.showProgressDialog(getContext());
                                    AddVendor();
                                }
                            }
                        }
                    }
                }
            }
        });
        return view;
    }

    private void AddVendor() {

        CompanyNameText = CompanyName.getText().toString().trim();
        EmailAddressText = EmailAddress.getText().toString().trim();
        StreetText = Street.getText().toString().trim();
        CityText = City.getText().toString().trim();
        StateText =State.getText().toString().trim();
        ZipText =Zip.getText().toString().trim();
        CountryText =Country.getText().toString().trim();
        AddressPhoneText = AddressPhone.getText().toString().trim();
        FaxText = Fax.getText().toString().trim();
        SalesContactNameText = SalesContactName.getText().toString().trim();
        SalesPhoneText = SalesPhone.getText().toString().trim();
        SalesEmailText = SalesEmail.getText().toString().trim();
        AccountsContactNameText = AccountsContactName.getText().toString().trim();
        AccountsPhoneText = AccountsPhone.getText().toString().trim();
        AccountsEmailText = AccountsEmail.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        AddVendorDataModel addVendorDataModel = new AddVendorDataModel();
        addVendorDataModel.setRecordType("V");
        addVendorDataModel.setCompanyName(CompanyNameText);
        addVendorDataModel.setEmail(EmailAddressText);
        addVendorDataModel.setStreet(StreetText);
        addVendorDataModel.setCity(CityText);
        addVendorDataModel.setState(StateText);
        addVendorDataModel.setZip(ZipText);
        addVendorDataModel.setCountry(CountryText);
        addVendorDataModel.setPhone(AddressPhoneText);
        addVendorDataModel.setFax(FaxText);
        addVendorDataModel.setContactName(SalesContactNameText);
        addVendorDataModel.setContactPhone(SalesPhoneText);
        addVendorDataModel.setContactEmail(SalesEmailText);
        addVendorDataModel.setAccountsContactName(AccountsContactNameText);
        addVendorDataModel.setAccountsContactPhone(AccountsPhoneText);
        addVendorDataModel.setAccountsContactEmail(AccountsEmailText);
        addVendorDataModel.setCreatedOn(date);
        addVendorDataModel.setLastChangedOn(date);

        String UserId =PreferenceConnector.readString(getContext(),"UserId","");


        try{
            restService.getService().PostVendorDetails(UserId,addVendorDataModel,new Callback<AddVendorModel>(){

                @Override
                public void success(AddVendorModel addVendorModel, Response response) {

                    Message = addVendorModel.getMessage();
                    DidError = addVendorModel.getDidError();
                    ErrorMessage = addVendorModel.getErrorMessage();

                    if (!Message.isEmpty()){
                        CompanyName.setText("");
                        EmailAddress.setText("");
                        Street.setText("");
                        City.setText("");
                        State.setText("");
                        Zip.setText("");
                        Country.setText("");
                        AddressPhone.setText("");
                        Fax.setText("");
                        SalesContactName.setText("");
                        SalesPhone.setText("");
                        SalesEmail.setText("");
                        AccountsContactName.setText("");
                        AccountsPhone.setText("");
                        AccountsEmail.setText("");
                        AddressLinearLayout.setVisibility(View.GONE);
                        SalesLinearLayout.setVisibility(View.GONE);
                        AccountsLinearLayout.setVisibility(View.GONE);
                        AddressAdd.setVisibility(View.VISIBLE);
                        AddressSub.setVisibility(View.GONE);
                        SalesAdd.setVisibility(View.VISIBLE);
                        SalesSub.setVisibility(View.GONE);
                        AccountsAdd.setVisibility(View.VISIBLE);
                        AccountsSub.setVisibility(View.GONE);
                        Utils.dismissProgressDialog();
                        Utils.showAlertDialog(getContext(),"Vendor Added Succesfully!",false);
                    }else {
                        Utils.dismissProgressDialog();
                        Utils.showAlertDialog(getContext(),ErrorMessage,false);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean ValidateEmailAddress(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(EmailAddress.getText().toString().isEmpty()) {
            EmailAddress.setError("Email Address Can't be empty");
            EmailAddress.requestFocus();
            return false;
        }
        else {
            if (EmailAddress.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                EmailAddress.setError("Enter Valid Email Address");
                EmailAddress.requestFocus();
                return false;
            }
        }
    }

    public boolean ValidateSalesEmailAddress(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(SalesEmail.getText().toString().isEmpty()) {
            SalesEmail.setError("Email Address Can't be empty");
            SalesEmail.requestFocus();
            return false;
        }
        else {
            if (SalesEmail.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                SalesEmail.setError("Enter Valid Email Address");
                SalesEmail.requestFocus();
                return false;
            }
        }
    }

    public boolean ValidateAccountsEmailAddress(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(AccountsEmail.getText().toString().isEmpty()) {
            AccountsEmail.setError("Email Address Can't be empty");
            AccountsEmail.requestFocus();
            return false;
        }
        else {
            if (AccountsEmail.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                AccountsEmail.setError("Enter Valid Email Address");
                AccountsEmail.requestFocus();
                return false;
            }
        }
    }

    public boolean ValidateCompanyName(){

        if(CompanyName.getText().toString().isEmpty()) {
            CompanyName.setError("Company Address Can't be Empty");
            CompanyName.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateStreet(){

        if(Street.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            Street.setError("Street Can't be Empty");
            Street.requestFocus();
            return false;
        }else return true;

    }
    public boolean ValidateCity(){

        if(City.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            City.setError("City Can't be Empty");
            City.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateState(){

        if(State.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            State.setError("State Can't be Empty");
            State.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateZip(){

        if(Zip.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            Zip.setError("Zip Can't be Empty");
            Zip.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateCountry(){

        if(Country.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            Country.setError("Country Can't be Empty");
            Country.requestFocus();
            return false;
        }else return true;

    }


    public boolean ValidatePhone(){

        if(AddressPhone.getText().toString().isEmpty()) {
            AddressLinearLayout.setVisibility(View.VISIBLE);
            AddressAdd.setVisibility(View.VISIBLE);
            AddressSub.setVisibility(View.GONE);
            AddressPhone.setError("Phone Can't be Empty");
            AddressPhone.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateSalesName(){

        if(SalesContactName.getText().toString().isEmpty()) {
            SalesLinearLayout.setVisibility(View.VISIBLE);
            SalesAdd.setVisibility(View.VISIBLE);
            SalesSub.setVisibility(View.GONE);
            SalesContactName.setError("Name Can't be Empty");
            SalesContactName.requestFocus();
            return false;
        }else return true;

    }


    public boolean ValidateSalesPhone(){

        if(SalesPhone.getText().toString().isEmpty()) {
            SalesLinearLayout.setVisibility(View.VISIBLE);
            SalesAdd.setVisibility(View.VISIBLE);
            SalesSub.setVisibility(View.GONE);
            SalesPhone.setError("Phone Can't be Empty");
            SalesPhone.requestFocus();
            return false;
        }else return true;

    }

    public boolean ValidateAccountsContactName(){

        if(AccountsContactName.getText().toString().isEmpty()) {
            AccountsLinearLayout.setVisibility(View.VISIBLE);
            AccountsAdd.setVisibility(View.VISIBLE);
            AccountsSub.setVisibility(View.GONE);
            AccountsContactName.setError("Name Can't be Empty");
            AccountsContactName.requestFocus();
            return false;
        }else return true;

    }


    public boolean ValidateAccountsPhone(){

        if(AccountsPhone.getText().toString().isEmpty()) {
            AccountsLinearLayout.setVisibility(View.VISIBLE);
            AccountsAdd.setVisibility(View.VISIBLE);
            AccountsSub.setVisibility(View.GONE);
            AccountsPhone.setError("Phone Can't be Empty");
            AccountsPhone.requestFocus();
            return false;
        }else return true;

    }

        public boolean Validations(){
            if (!ValidateCompanyName()){
                return false;
            } if (!ValidateEmailAddress()){
                return false;
            } if (!ValidateStreet()){
                return false;
            } if (!ValidateCity()){
                return false;
            } if (!ValidateState()){
                return false;
            } if (!ValidateZip()){
                return false;
            }if (!ValidateCountry()){
                return false;
            }if (!ValidatePhone()){
                return false;
            }if (!ValidateSalesName()){
                return false;
            }if (!ValidateSalesPhone()){
                return false;
            }if (!ValidateSalesEmailAddress()){
                return false;
            }if (!ValidateAccountsContactName()){
                return false;
            }if (!ValidateAccountsPhone()){
                return false;
            }
            return ValidateAccountsEmailAddress();
        }
}

