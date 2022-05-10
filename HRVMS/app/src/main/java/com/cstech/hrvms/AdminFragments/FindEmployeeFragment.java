package com.cstech.hrvms.AdminFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Adapters.FindEmployeeAdapter;
import com.cstech.hrvms.DataModels.FindEmployeeDataModel;
import com.cstech.hrvms.Models.FindEmployeeModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FindEmployeeFragment extends Fragment {

    ListView listView;
    RestService restService;
    String Message,ErrorMessage,DidError;
    List<FindEmployeeDataModel> list;
    FindEmployeeAdapter findEmployeeAdapter;
    EditText Searching;


    public FindEmployeeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Find Employee");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.findemployee_fragment,container,false);
        listView = view.findViewById(R.id.ListView);
        Searching = view.findViewById(R.id.Search);

        restService = new RestService();
        list = new ArrayList<>();
        getDetails();

        Searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (findEmployeeAdapter!=null){
                    findEmployeeAdapter.filter(editable.toString());
                }

            }
        });

        return view;
    }

    public void  getDetails(){
        Utils.showProgressDialog(getActivity());
        try {
            restService.getService().FindEmployeeList(new Callback<FindEmployeeModel>() {
                @Override
                public void success(FindEmployeeModel findEmployeeModel, Response response) {

                    Message = findEmployeeModel.getMessage();
                    DidError = findEmployeeModel.getDidError();
                    ErrorMessage = findEmployeeModel.getErrorMessage();

                    list = findEmployeeModel.getModel();
                    if (getActivity() != null) {

                        if (list.size() > 0) {
                            findEmployeeAdapter = new FindEmployeeAdapter(getActivity(),list);
                            listView.setAdapter(findEmployeeAdapter);
                        } else {

                            if (list.size() == 0) {
                                Utils.showAlertDialog(getActivity(), "No Data Available", false);

                            } else {
                                Utils.showAlertDialog(getActivity(), "No Data Available", false);

                            }

                        }
                        Utils.dismissProgressDialog();
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

}
