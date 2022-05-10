package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cstech.hrvms.DataModels.ClientInvoiceDataModel;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientInvoiceAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    TextView Invoice,InvoiceDate,Amount,Client,Employee;
    ImageView ViewInfo;
    LinearLayout InvoiceLinearLayout,InvoiceDateLinearLayout,AmountLinearLayout,ClientLinearLayout,EmployeeLinearLayout;
    List<ClientInvoiceDataModel> clientInvoiceModel,filterList;


    public ClientInvoiceAdapter(Activity activity, List<ClientInvoiceDataModel> clientInvoiceModel) {
        this.activity = activity;
        this.clientInvoiceModel = clientInvoiceModel;
        inflater=LayoutInflater.from(activity);
        filterList=new ArrayList<>();
        try {
            filterList.addAll(clientInvoiceModel);
        }catch (Exception e){

            Utils.showAlertDialog( activity, e.getMessage(),false);
        }
    }

    @Override
    public int getCount() {
        return clientInvoiceModel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =inflater.inflate(R.layout.clientinvoice_adapter,null);

        Invoice = view.findViewById(R.id.Invoice);
        InvoiceDate = view.findViewById(R.id.InvoiceDate);
        Amount = view.findViewById(R.id.AmountValue);
        Client = view.findViewById(R.id.Client);
        Employee = view.findViewById(R.id.Employee);
        InvoiceLinearLayout = view.findViewById(R.id.InvoiceLinearLayout);
        InvoiceDateLinearLayout = view.findViewById(R.id.InvoiceDateLinearLayout);
        AmountLinearLayout = view.findViewById(R.id.AmountLinearLayout);
        ClientLinearLayout = view.findViewById(R.id.ClientLinearLayout);
        EmployeeLinearLayout = view.findViewById(R.id.EmployeeLinearLayout);
        ViewInfo = view.findViewById(R.id.ViewInfo);

        Invoice.setText(clientInvoiceModel.get(position).getInvoiceNo());
        InvoiceDate.setText(clientInvoiceModel.get(position).getInvoiceDate());
        Amount.setText(clientInvoiceModel.get(position).getInvoiceAmout());
        Client.setText(clientInvoiceModel.get(position).getCompanyName());
        Employee.setText(clientInvoiceModel.get(position).getUsername());


       if (Invoice.getText().toString().isEmpty() || Invoice.getText().toString().startsWith(" ") ){
           InvoiceLinearLayout.setVisibility(View.GONE);
       } if (InvoiceDate.getText().toString().isEmpty() || InvoiceDate.getText().toString().startsWith(" ")){
            InvoiceDateLinearLayout.setVisibility(View.GONE);
       } if (Amount.getText().toString().isEmpty() || Amount.getText().toString().startsWith(" ")){
            AmountLinearLayout.setVisibility(View.GONE);
       } if (Client.getText().toString().isEmpty() || Client.getText().toString().startsWith(" ")){
            ClientLinearLayout.setVisibility(View.GONE);
       } if (Employee.getText().toString().isEmpty() || Employee.getText().toString().startsWith(" ")){
            EmployeeLinearLayout.setVisibility(View.GONE);
       }



        return view;
    }

    public void filter(String searchedText) {

        if (searchedText.length()!=0){

            searchedText=searchedText.toLowerCase(Locale.getDefault());
            clientInvoiceModel.clear();

            for (ClientInvoiceDataModel events: filterList) {


                if (events.getCompanyName().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                        events.getUsername().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                        events.getInvoiceNo().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                        events.getInvoiceDate().toLowerCase(Locale.getDefault()).contains(searchedText) ||
                        events.getInvoiceAmout().toLowerCase(Locale.getDefault()).contains(searchedText)) {

                    clientInvoiceModel.add(events);
                }

                notifyDataSetChanged();

            }
            if (clientInvoiceModel.size()==0){

                Utils.showAlertDialog(activity, "No data found",false);
            }

        }else {
            clientInvoiceModel.addAll(filterList);
            notifyDataSetChanged();
        }
    }
}
