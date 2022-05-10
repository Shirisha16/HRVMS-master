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

public class AddClientFragment extends Fragment{

    LinearLayout AddressLinearLayout,ContactInfoLinearLayout;
    EditText CompanyName,EmailAddress,Street,City,State,Zip,Country,AddressPhone,Fax,ContactInfoContactName,ContactInfoPhone,ContactInfoEmail;
    ImageView AddressAdd,AddressSub,ContactInfoAdd,ContactInfoSub;
    Button Submit;
    String CompanyNameText,EmailAddressText,StreetText,CityText,StateText,ZipText,CountryText,AddressPhoneText,FaxText,ContactInfoContactNameText,
            ContactInfoPhoneText,ContactInfoEmailText,Message,ErrorMessage,DidError;
    RestService restService;

    public AddClientFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Add Client");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.addclient_fragment,container,false);
        AddressLinearLayout = view.findViewById(R.id.AddressLinearLayout);
        ContactInfoLinearLayout = view.findViewById(R.id.ContactInfoLinearLayout);
        CompanyName = view.findViewById(R.id.CompanyName);
        EmailAddress = view.findViewById(R.id.EmailAddress);
        Street = view.findViewById(R.id.Street);
        City = view.findViewById(R.id.City);
        State = view.findViewById(R.id.State);
        Zip = view.findViewById(R.id.Zip);
        Country = view.findViewById(R.id.Country);
        AddressPhone = view.findViewById(R.id.AddressPhone);
        Fax = view.findViewById(R.id.Fax);
        ContactInfoContactName = view.findViewById(R.id.ContactInfoContactName);
        ContactInfoPhone = view.findViewById(R.id.ContactInfoPhone);
        ContactInfoEmail = view.findViewById(R.id.ContactInfoEmail);
        AddressAdd = view.findViewById(R.id.AddressAdd);
        AddressSub = view.findViewById(R.id.AddressSub);
        ContactInfoAdd = view.findViewById(R.id.ContactInfoAdd);
        ContactInfoSub = view.findViewById(R.id.ContactInfoSub);
        Submit = view.findViewById(R.id.Submit);

        restService = new RestService();

        AddressLinearLayout.setVisibility(View.GONE);
        ContactInfoLinearLayout.setVisibility(View.GONE);

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

        ContactInfoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfoLinearLayout.setVisibility(View.VISIBLE);
                ContactInfoAdd.setVisibility(View.GONE);
                ContactInfoSub.setVisibility(View.VISIBLE);
            }
        });
        ContactInfoSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfoLinearLayout.setVisibility(View.GONE);
                ContactInfoAdd.setVisibility(View.VISIBLE);
                ContactInfoSub.setVisibility(View.GONE);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validations()){
                    if (!ContactInfoEmail.getText().toString().isEmpty()){
                        if (ValidateEmailAddress()){
                                if(Validations()) {
                                    Utils.showProgressDialog(getContext());
                                    AddVendor();
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
        ContactInfoContactNameText = ContactInfoContactName.getText().toString().trim();
        ContactInfoPhoneText = ContactInfoPhone.getText().toString().trim();
        ContactInfoEmailText = ContactInfoEmail.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        AddVendorDataModel addVendorDataModel = new AddVendorDataModel();
        addVendorDataModel.setRecordType("C");
        addVendorDataModel.setCompanyName(CompanyNameText);
        addVendorDataModel.setEmail(EmailAddressText);
        addVendorDataModel.setStreet(StreetText);
        addVendorDataModel.setCity(CityText);
        addVendorDataModel.setState(StateText);
        addVendorDataModel.setZip(ZipText);
        addVendorDataModel.setCountry(CountryText);
        addVendorDataModel.setPhone(AddressPhoneText);
        addVendorDataModel.setFax(FaxText);
        addVendorDataModel.setContactName(ContactInfoContactNameText);
        addVendorDataModel.setContactPhone(ContactInfoPhoneText);
        addVendorDataModel.setContactEmail(ContactInfoEmailText);
        addVendorDataModel.setAccountsContactName("");
        addVendorDataModel.setAccountsContactPhone("");
        addVendorDataModel.setAccountsContactEmail("");
        addVendorDataModel.setCreatedOn(date);
        addVendorDataModel.setLastChangedOn(date);

        String UserId = PreferenceConnector.readString(getContext(),"UserId","");


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
                        ContactInfoContactName.setText("");
                        ContactInfoPhone.setText("");
                        ContactInfoEmail.setText("");
                        AddressLinearLayout.setVisibility(View.GONE);
                        ContactInfoLinearLayout.setVisibility(View.GONE);
                        AddressAdd.setVisibility(View.VISIBLE);
                        AddressSub.setVisibility(View.GONE);
                        ContactInfoAdd.setVisibility(View.VISIBLE);
                        ContactInfoSub.setVisibility(View.GONE);
                        Utils.dismissProgressDialog();
                        Utils.showAlertDialog(getContext(),"Client Added Succesfully!",false);
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

    public boolean ValidateContactEmailAddress(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(ContactInfoEmail.getText().toString().isEmpty()) {
            ContactInfoEmail.setError("Email Address Can't be empty");
            ContactInfoEmail.requestFocus();
            return false;
        }
        else {
            if (ContactInfoEmail.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                ContactInfoEmail.setError("Enter Valid Email Address");
                ContactInfoEmail.requestFocus();
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

    public boolean ValidateContactName(){

        if(ContactInfoContactName.getText().toString().isEmpty()) {
            ContactInfoLinearLayout.setVisibility(View.VISIBLE);
            ContactInfoAdd.setVisibility(View.VISIBLE);
            ContactInfoSub.setVisibility(View.GONE);
            ContactInfoContactName.setError("Name Can't be Empty");
            ContactInfoContactName.requestFocus();
            return false;
        }else return true;

    }


    public boolean ValidateContactPhone(){

        if(ContactInfoPhone.getText().toString().isEmpty()) {
            ContactInfoLinearLayout.setVisibility(View.VISIBLE);
            ContactInfoAdd.setVisibility(View.VISIBLE);
            ContactInfoSub.setVisibility(View.GONE);
            ContactInfoPhone.setError("Phone Can't be Empty");
            ContactInfoPhone.requestFocus();
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
        }if (!ValidateContactName()){
            return false;
        }if (!ValidateContactPhone()){
            return false;
        }
        return ValidateContactEmailAddress();
    }
}
