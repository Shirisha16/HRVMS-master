package com.cstech.hrvms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cstech.hrvms.DataModels.GetSecurityProfileDataModel;
import com.cstech.hrvms.R;

import java.util.List;

public class SpinnerAdapterForSecurityAccess extends ArrayAdapter<GetSecurityProfileDataModel> {

    private Context context;
    List<GetSecurityProfileDataModel> myObjs;
    LayoutInflater inflater;

    public SpinnerAdapterForSecurityAccess(Context context, int textViewResourceId, List<GetSecurityProfileDataModel> myObjs) {
        super(context, textViewResourceId,myObjs);
        this.context = context;
        this.myObjs = myObjs;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return myObjs.size();
    }

    public GetSecurityProfileDataModel getItem(int position){
        return myObjs.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
        //TextView label = new TextView(context);
        //label.setText(myObjs.get(position).getClassName());
        //return label;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(R.layout.spinner_layout, parent, false);
        TextView main_text = view.findViewById(R.id.text);
        main_text.setText(myObjs.get(position).getSecurityProfile());

        return view;
    }



}
