package com.cstech.hrvms.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.cstech.hrvms.Models.Country;
import com.cstech.hrvms.R;

import java.util.List;

public class CountryAdapter extends BaseAdapter {

    FragmentActivity activity;
    List<Country.CountryList> countryList;
    LayoutInflater inflater;
    public CountryAdapter(FragmentActivity activity, List<Country.CountryList> countryList) {

        this.activity=activity;
        this.countryList=countryList;
        inflater=LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return countryList.size();
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

        View view=inflater.inflate(R.layout.country_items,null);
        TextView countryName=(TextView)view.findViewById(R.id.countryName);
        countryName.setText(countryList.get(position).getCountry());
        return view;
    }
}
