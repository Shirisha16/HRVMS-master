package com.cstech.hrvms.AdminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Adapters.EmployeeClientDetailsAdapter;
import com.cstech.hrvms.Adapters.SpinnerAdapterForEmployee;
import com.cstech.hrvms.DataModels.EmployeeClientDetailsDataModel;
import com.cstech.hrvms.DataModels.GetClientVendorDataModel;
import com.cstech.hrvms.DataModels.GetUsersDataModel;
import com.cstech.hrvms.Models.EmployeeClientDetailsModel;
import com.cstech.hrvms.Models.GetClientVendorModel;
import com.cstech.hrvms.Models.GetClients;
import com.cstech.hrvms.Models.GetUsersModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EmployeeClientDetailsFragment extends Fragment {

    Spinner EmployeeSpinner;
    RestService restService;
    ArrayAdapter<GetUsersDataModel>  listArrayAdapter;
    List<GetUsersDataModel> getUsersModel;
    String Message,ErrorMessage,DidError,EmployeeId;
    SpinnerAdapterForEmployee spinnerAdapterForEmployee;
    ListView listView;
    EmployeeClientDetailsAdapter employeeClientDetailsAdapter;
    List<EmployeeClientDetailsDataModel> list;
    List<GetClientVendorDataModel> getClients;
    List<GetClientVendorDataModel> getVendors;
    List<GetClients> getEmpWorkInfo;


    public EmployeeClientDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Employee Client Details");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.employeeclientdetails_fragment,container,false);
        EmployeeSpinner =  view.findViewById(R.id.EmployeeSpinner);
        listView =  view.findViewById(R.id.ListView);
        restService = new RestService();
        getUsersModel=new ArrayList<>();
        getClients=new ArrayList<>();
        getVendors=new ArrayList<>();

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
                 System.out.println("Error" + EmployeeId);
                 if (EmployeeId.equalsIgnoreCase("0")){
                     listView.setVisibility(View.GONE);
                 }else{
                     getDetails();
                 }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Clients();
        Vendors();
        return view;
    }

    private void Vendors() {
        try {

            restService.getService().GetVendors(new Callback<GetClientVendorModel>() {
                @Override
                public void success(GetClientVendorModel usersModel, Response response) {

                    Message = usersModel.getMessage();
                    DidError = usersModel.getDidError();
                    ErrorMessage = usersModel.getErrorMessage();

                    if (!Message.isEmpty()) {

                        getVendors = usersModel.getModel();

                    } else {

                        Utils.showAlertDialog(getContext(), ErrorMessage, false);
                    }


                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
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
                        if (getUsersModel != null) {
                            spinnerAdapterForEmployee = new SpinnerAdapterForEmployee(getActivity(), R.id.text, getUsersModel);
                            EmployeeSpinner.setAdapter(spinnerAdapterForEmployee);
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

    public void  getDetails(){
        Utils.showProgressDialog(getActivity());
        try {
            restService.getService().GetClientVendorDetails(EmployeeId,new Callback<EmployeeClientDetailsModel>() {
                @Override
                public void success(EmployeeClientDetailsModel employeeClientDetailsModel, Response response) {

                    Message = employeeClientDetailsModel.getMessage();
                    DidError = employeeClientDetailsModel.getDidError();
                    ErrorMessage = employeeClientDetailsModel.getErrorMessage();
                    getEmpWorkInfo=new ArrayList<>();
                    list = employeeClientDetailsModel.getModel();
                    if (getActivity() != null) {

                        listView.setVisibility(View.VISIBLE);
                        if (getClients!=null){

                            for (int i=0; i<list.size(); i++){
                                GetClients clients=new GetClients();
                                clients.setStartDate(list.get(i).getStartDate());
                                clients.setEndDate(list.get(i).getEndDate());
                                for (int j=0; j<getClients.size(); j++){

                                    if (list.get(i).getClient_Ref().equalsIgnoreCase(getClients.get(j).getId())){
                                        clients.setClient(getClients.get(j).getCompanyName());
                                    }

                                }
                                for (int k=0; k<getVendors.size(); k++) {
                                    if (list.get(i).getPrimaryVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {

                                        clients.setPrimaryVendor(getVendors.get(k).getCompanyName());
                                    }
                                    if (list.get(i).getSecondaryVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {

                                        clients.setSecondVendors(getVendors.get(k).getCompanyName());
                                    }
                                    if (list.get(i).getThirdVendor_Ref().equalsIgnoreCase(getVendors.get(k).getId())) {

                                        clients.setThirdVendors(getVendors.get(k).getCompanyName());
                                    }
                                }
                                getEmpWorkInfo.add(clients);
                            }

                            if (list.size() > 0) {

                                employeeClientDetailsAdapter = new EmployeeClientDetailsAdapter(getActivity(),getEmpWorkInfo);
                                listView.setAdapter(employeeClientDetailsAdapter);
                                listView.setVisibility(View.VISIBLE);
                            } else {

                                if (list.size() == 0) {
                                    listView.setVisibility(View.GONE);
                                    Utils.showAlertDialog(getActivity(), "No Data Available", false);

                                } else {
                                    listView.setVisibility(View.GONE);
                                    Utils.showAlertDialog(getActivity(), "No Data Available", false);
                                }


                            }
                            Utils.dismissProgressDialog();
                        }



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
            Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
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

                        getClients = usersModel.getModel();
                        System.out.println("Error" + getClients);

                    } else {

                        Utils.showAlertDialog(getContext(), ErrorMessage, false);
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){

            Toast.makeText(getContext(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_SHORT).show();
        }
    }
}