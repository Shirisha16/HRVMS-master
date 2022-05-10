package com.cstech.hrvms.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.Country;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.HolidaysList;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;
import com.cstech.hrvms.UserFragments.HolidaysFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HolidaysAdapter extends BaseAdapter {

    List<HolidaysList> holidays;
    FragmentActivity activity;
    LayoutInflater inflater;
    String CurrentCountry;
    RestService service;
    Calendar calendar;
    List<Country.CountryList>countryList;
    LoadDetails loadDetails;

    public HolidaysAdapter(FragmentActivity activity, List<HolidaysList> holidays, LoadDetails holidaysFragment) {
        this.activity=activity;
        this.holidays=holidays;
        inflater=LayoutInflater.from(activity);
        countryList=new ArrayList<>();
        this.loadDetails=holidaysFragment;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return holidays.size();
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
    public View getView(final int i, View convertView, ViewGroup parent) {

        String Designation= PreferenceConnector.readString(activity, "Type","");
        View view;
        if (Designation.equalsIgnoreCase("Admin"))
        {
             view=inflater.inflate(R.layout.holidays_items,null);
        }else {
            view=inflater.inflate(R.layout.user_holidays_items,null);
        }


        LinearLayout headerView=(LinearLayout)view.findViewById(R.id.headerView);
        LinearLayout modifyLayout=(LinearLayout)view.findViewById(R.id.modifyLayout);

        TextView Dateofholiday=(TextView)view.findViewById(R.id.date);
        TextView Holiday=(TextView)view.findViewById(R.id.holiday);
        TextView dayofweek=(TextView)view.findViewById(R.id.dayofWeek);
        TextView Country=(TextView)view.findViewById(R.id.Country);
        TextView CountryHView=(TextView)view.findViewById(R.id.CountryHView);
        final TextView editHolidays=(TextView)view.findViewById(R.id.editHolidays);
        TextView deleteHolidays=(TextView)view.findViewById(R.id.DeleteHolidays);

        String holidayDate=holidays.get(i).getDateOfHoliday();
        holidayDate=holidayDate.replace("T00:00:00","");

        if (Designation.equalsIgnoreCase("Admin")){
            modifyLayout.setVisibility(View.VISIBLE);
            Country.setVisibility(View.VISIBLE);
            CountryHView.setVisibility(View.VISIBLE);
        }else {
            modifyLayout.setVisibility(View.GONE);
            Country.setVisibility(View.GONE);
            CountryHView.setVisibility(View.GONE);
        }
        service=new RestService();
        final String finalHolidayDate = holidayDate;
        editHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog editView=new Dialog(activity);
                final AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
                final View editView = inflater.inflate(R.layout.update_holidays, null);
                final TextView date=(TextView)editView.findViewById(R.id.date);
                final EditText holiday=(EditText)editView.findViewById(R.id.holiday);
                final EditText dayofweek=(EditText)editView.findViewById(R.id.dayofWeek);
                final TextView selectCountry=(TextView) editView.findViewById(R.id.selectCountry);
                final TextView Submit=(TextView) editView.findViewById(R.id.Submit);
                final TextView Cancel = (TextView) editView.findViewById(R.id.Cancel);
                dialog.setView(editView);




                final AlertDialog alertDialog=dialog.create();
                alertDialog.show();
                date.setText(finalHolidayDate);
                holiday.setText(holidays.get(i).getHoliday());
                dayofweek.setText(holidays.get(i).getDayOfWeeks());
                selectCountry.setText(holidays.get(i).getCountry());


                selectCountry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        service.getService().getCountries(new Callback<com.cstech.hrvms.Models.Country>() {
                            @Override
                            public void success(Country country, Response response) {

                                countryList=country.getModel();
                                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                                View countryLayout=inflater.inflate(R.layout.country_layout,null);
                                ListView countryListView=(ListView) countryLayout.findViewById(R.id.countryList);
                                builder.setView(countryLayout);
                                CountryAdapter adapter=new CountryAdapter(activity,countryList);
                                countryListView.setAdapter(adapter);
                                final AlertDialog dialog1; dialog1=builder.create();
                                dialog1.show();
                                countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        selectCountry.setText(countryList.get(position).getCountry());
                                        dialog1.dismiss();
                                    }
                                });

                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                    }
                });


                final DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        view.setMaxDate(System.currentTimeMillis());
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        SimpleDateFormat week=new SimpleDateFormat("EEEE",Locale.US);
                        dayofweek.setText(week.format(calendar.getTime()));
                        date.setText(format.format(calendar.getTime()));

                    }
                };
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar=Calendar.getInstance();

                        new DatePickerDialog(activity,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final ProgressDialog progressDialog=new ProgressDialog(activity);
                        progressDialog.setMessage("Loading..");


                        if (date.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(activity,"Date field should not be empty",false);

                        }
                        else if (holiday.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(activity,"Holiday field should not be empty",false);

                        }
                        else if (dayofweek.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(activity,"Day Of Week field should not be empty",false);

                        }
                        else if (selectCountry.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(activity,"Country field should not be empty",false);

                        }
                        else {
                            progressDialog.show();
                            HolidaysList holidaysList=new HolidaysList();
                            holidaysList.setId(holidays.get(i).getId());
                            holidaysList.setDateOfHoliday(date.getText().toString());
                            holidaysList.setHoliday(holiday.getText().toString());
                            holidaysList.setDayOfWeeks(dayofweek.getText().toString());
                            holidaysList.setCountry(selectCountry.getText().toString());

                            service.getService().updateHolidays(holidays.get(i).getId(), holidaysList, new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel result, Response response) {

                                    progressDialog.dismiss();
                                    if (result.getMessage()!=null){

                                        Utils.showAlertDialog(activity, result.getMessage(),false);
                                        loadDetails.onMethodCallback();
                                        notifyDataSetChanged();
                                    }else {
                                        Utils.showAlertDialog(activity, result.getErrorMessage(),false);
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Utils.showAlertDialog(activity, error.getMessage(),false);
                                    progressDialog.dismiss();

                                }
                            });
                            alertDialog.dismiss();
                        }
                    }
                });

            }
        });

        deleteHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        service.getService().deleteHolidays(holidays.get(i).getId(), new Callback<DeleteEmployeeWorkInfoModel>() {
                            @Override
                            public void success(DeleteEmployeeWorkInfoModel delete, Response response) {

                                Utils.showAlertDialog(activity,delete.getMessage(),false);
                                dialog.dismiss();
                                loadDetails.onMethodCallback();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialog.dismiss();
                                Utils.showAlertDialog(activity,error.getMessage(),false);

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        CurrentCountry= PreferenceConnector.readString(activity,"CurrentCountry","");
        if (i==0){
            headerView.setVisibility(View.VISIBLE);
        }else {
            headerView.setVisibility(View.GONE);
        }

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat changeDateFormat=new SimpleDateFormat("MMM-dd-yyyy");

        Date date;
        try {
           date=format.parse(holidayDate);
            holidayDate=changeDateFormat.format(date);
        }catch (Exception e)
        {
            Utils.showAlertDialog(activity,e.getMessage(),false);
        }

        Dateofholiday.setText(holidayDate);
        Holiday.setText(holidays.get(i).getHoliday());
        dayofweek.setText(holidays.get(i).getDayOfWeeks());
        Country.setText(holidays.get(i).getCountry());


        return view;
    }
}
