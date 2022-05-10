package com.cstech.hrvms.AdminFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Adapters.ClientInvoiceAdapter;
import com.cstech.hrvms.DataModels.ClientInvoiceDataModel;
import com.cstech.hrvms.Models.ClientInvoiceModel;
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

public class ClientInvoiceFragment extends Fragment {
    TextView SelectFromDate,SelectToDate;
    ImageButton Search;
    Calendar mycal;
    String Variation;
    ListView listView;
    RestService restService;
    List<ClientInvoiceDataModel> list;
    ClientInvoiceAdapter adapter;
    EditText Searching;

    public ClientInvoiceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Client Invoices");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clientinvoice_fragment, container,false);
        SelectFromDate = view.findViewById(R.id.SelectFromDate);
        SelectToDate = view.findViewById(R.id.SelectToDate);
        Search = view.findViewById(R.id.SearchButton);
        Searching = view.findViewById(R.id.Search);
        listView = view.findViewById(R.id.ListView);

        restService = new RestService();
        list =  new ArrayList<>();



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

        mycal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                datePicker.setMaxDate(System.currentTimeMillis());
                datePicker.setMaxDate(System.currentTimeMillis());

                mycal.set(java.util.Calendar.YEAR,year);
                mycal.set(java.util.Calendar.MONTH,month);
                mycal.set(java.util.Calendar.DAY_OF_MONTH,day);

                String format="MM/dd/yyyy";
                SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
                if (Variation.equalsIgnoreCase("SelectFromDate")) {
                    SelectFromDate.setText(sdf.format(mycal.getTime()));

                } else if (Variation.equalsIgnoreCase("SelectToDate")) {
                    SelectToDate.setText(sdf.format(mycal.getTime()));

                }

            }
        };
        try {
            SelectFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variation = "SelectFromDate";
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(getContext(), listener, mycal.get(Calendar.YEAR), mycal.get(Calendar.MONTH), mycal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            SelectToDate.setOnClickListener(new View.OnClickListener() {
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

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getClientInvoiceDetails();


        return view;
    }

    private void getClientInvoiceDetails() {
        Utils.showProgressDialog(getContext());
        try {

            restService.getService().GetInvoiceDetails(new Callback<ClientInvoiceModel>() {
                @Override
                public void success(ClientInvoiceModel aa, Response response) {

                    list = aa.getModel();
                    if (getActivity() != null) {

                        if (list.size() > 0) {

                            adapter = new ClientInvoiceAdapter(getActivity(), list);
                            listView.setAdapter(adapter);
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
                    Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_LONG).show();

                }
            });
        }catch (Exception e){
            Utils.dismissProgressDialog();
            Toast.makeText(getActivity(), "Server Failure! Please Try After Sometime.", Toast.LENGTH_LONG).show();
        }


    }
}
