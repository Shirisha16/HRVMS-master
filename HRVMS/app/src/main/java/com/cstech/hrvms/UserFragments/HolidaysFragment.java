package com.cstech.hrvms.UserFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.Adapters.CountryAdapter;
import com.cstech.hrvms.Adapters.HolidaysAdapter;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.Country;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.GetCountry;
import com.cstech.hrvms.Models.Holidays;
import com.cstech.hrvms.Models.HolidaysList;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidaysFragment extends Fragment implements LoadDetails {


    ListView holidaysList;
    RestService service;
    List<HolidaysList> holidays;
    TextView selectCountry;
    CardView selectCountryView;

    List<HolidaysList>cuntrywiseHolodays;
    TextView addHolidays;

    ProgressDialog pdialog;
    List<Country.CountryList>countryList;

    public HolidaysFragment() {
        // Required empty public constructor
    }
    HolidaysAdapter adapter;
    String CurrentCountry;
    String Designation;
    Calendar calendar;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_holidays, container, false);
        holidaysList=(ListView)view.findViewById(R.id.holidaysList);
        addHolidays=(TextView)view.findViewById(R.id.addHolidays);
        selectCountry=(TextView)view.findViewById(R.id.selectCountry);
        selectCountryView=(CardView)view.findViewById(R.id.selectCountryView);
        holidays=new ArrayList<>();
        cuntrywiseHolodays=new ArrayList<>();
        pdialog=new ProgressDialog(getContext());
        pdialog.setMessage("Loading..");
        pdialog.setCancelable(false);
        pdialog.show();
        countryList=new ArrayList<>();
        service=new RestService();
        CurrentCountry= PreferenceConnector.readString(getContext(), "CurrentCountry","");
        Designation= PreferenceConnector.readString(getContext(), "Type","");

        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.getService().getCountries(new Callback<Country>() {
                    @Override
                    public void success(Country country, Response response) {

                        countryList=country.getModel();
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        View countryLayout=inflater.inflate(R.layout.country_layout,null);
                        ListView countryListView=(ListView) countryLayout.findViewById(R.id.countryList);
                        builder.setView(countryLayout);
                        CountryAdapter adapter=new CountryAdapter(getActivity(),countryList);
                        countryListView.setAdapter(adapter);
                        final AlertDialog dialog1=builder.create();
                        dialog1.show();
                        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                selectCountry.setText(countryList.get(position).getCountry());
                                LoadHolidays(selectCountry.getText().toString().trim());
                                cuntrywiseHolodays.clear();
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

        if (Designation.equalsIgnoreCase("Admin")){
            addHolidays.setVisibility(View.VISIBLE);
            selectCountryView.setVisibility(View.VISIBLE);
        }else {
            addHolidays.setVisibility(View.GONE);

            selectCountryView.setVisibility(View.GONE);
        }
        addHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Dialog editView=new Dialog(activity);
                final AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                final View editView = inflater.inflate(R.layout.update_holidays, null);
                final TextView date=(TextView)editView.findViewById(R.id.date);
                final EditText holiday=(EditText)editView.findViewById(R.id.holiday);
                final EditText dayofweek=(EditText)editView.findViewById(R.id.dayofWeek);
                final TextView selectCountry=(TextView) editView.findViewById(R.id.selectCountry);
                final TextView Submit=(TextView) editView.findViewById(R.id.Submit);
                final TextView Cancel = (TextView) editView.findViewById(R.id.Cancel);
                Submit.setText("Submit");
                dialog.setView(editView);


                final AlertDialog alertDialog=dialog.create();
                alertDialog.show();
                selectCountry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        service.getService().getCountries(new Callback<Country>() {
                            @Override
                            public void success(Country country, Response response) {

                                countryList=country.getModel();
                                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                View countryLayout=inflater.inflate(R.layout.country_layout,null);
                                ListView countryListView=(ListView) countryLayout.findViewById(R.id.countryList);
                                builder.setView(countryLayout);
                                CountryAdapter adapter=new CountryAdapter(getActivity(),countryList);
                                countryListView.setAdapter(adapter);
                                final AlertDialog dialog1=builder.create();
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


                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MMM-dd", Locale.US);

                        SimpleDateFormat week=new SimpleDateFormat("EEEE",Locale.US);
                        dayofweek.setText(week.format(calendar.getTime()));
                        date.setText(format.format(calendar.getTime()));

                    }
                };
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar=Calendar.getInstance();

                        new DatePickerDialog(getActivity(),listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        if (date.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(getContext(),"Date field should not be empty",false);
                        }
                        else if (holiday.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(getContext(),"Holiday field should not be empty",false);

                        }
                        else if (dayofweek.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(getContext(),"Day Of Week field should not be empty",false);

                        }
                        else if (selectCountry.getText().toString().equalsIgnoreCase(""))
                        {
                            Utils.showAlertDialog(getContext(),"Country field should not be empty",false);

                        }
                        else {
                            pdialog.show();
                            HolidaysList holidaysList=new HolidaysList();
                            holidaysList.setDateOfHoliday(date.getText().toString());
                            holidaysList.setHoliday(holiday.getText().toString());
                            holidaysList.setDayOfWeeks(dayofweek.getText().toString());
                            holidaysList.setCountry(selectCountry.getText().toString());

                            service.getService().addHolidays(holidaysList, new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel result, Response response) {

                                    if (result.getMessage()!=null){

                                        Utils.showAlertDialog(getActivity(), result.getMessage(),false);
                                        LoadHolidays(selectCountry.getText().toString().trim());
                                        pdialog.dismiss();
                                    }else {
                                        Utils.showAlertDialog(getActivity(), result.getErrorMessage(),false);
                                        pdialog.dismiss();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Utils.showAlertDialog(getActivity(), error.getMessage(),false);
                                    pdialog.dismiss();
                                }
                            });

                            alertDialog.dismiss();
                        }
                    }
                });

            }
        });

        LoadHolidays(selectCountry.getText().toString().trim());



        return view;
    }

    private void LoadHolidays(final String selectedCountry) {

        service.getService().getHolidays(new Callback<Holidays>() {
            @Override
            public void success(Holidays holiday, Response response) {

                holidays.clear();
                if (selectedCountry.equalsIgnoreCase("Select Country")){
                    if (holiday.getMessage()!=null){
                        holidays.clear();
                        cuntrywiseHolodays.clear();
                        holidays=holiday.getModel();

                        for (int i=0; i<holidays.size(); i++)
                        {
                            if (CurrentCountry.equalsIgnoreCase(holidays.get(i).getCountry()))
                            {
                                HolidaysList list=new HolidaysList();
                                list.setDateOfHoliday(holidays.get(i).getDateOfHoliday());
                                list.setHoliday(holidays.get(i).getHoliday());
                                list.setDayOfWeeks(holidays.get(i).getDayOfWeeks());
                                list.setCountry(holidays.get(i).getCountry());
                                list.setId(holidays.get(i).getId());
                                cuntrywiseHolodays.add(list);
                                adapter=new HolidaysAdapter(getActivity(),cuntrywiseHolodays,HolidaysFragment.this);
                                holidaysList.setAdapter(adapter);
                                pdialog.dismiss();
                                selectCountry.setText(CurrentCountry);

                            }

                        }

                    }else {
                        pdialog.dismiss();
                        Utils.showAlertDialog(getContext(),"No Data Available",false);
                    }
                }else {

                    if (holiday.getMessage()!=null){

                        holidays.clear();
                        cuntrywiseHolodays.clear();
                        holidays=holiday.getModel();


                        for (int i=0; i<holidays.size(); i++)
                        {
                            if (selectedCountry.equalsIgnoreCase(holidays.get(i).getCountry()))
                            {
                                HolidaysList list=new HolidaysList();
                                list.setDateOfHoliday(holidays.get(i).getDateOfHoliday());
                                list.setHoliday(holidays.get(i).getHoliday());
                                list.setDayOfWeeks(holidays.get(i).getDayOfWeeks());
                                list.setCountry(holidays.get(i).getCountry());
                                list.setId(holidays.get(i).getId());
                                cuntrywiseHolodays.add(list);
                                adapter=new HolidaysAdapter(getActivity(),cuntrywiseHolodays,HolidaysFragment.this);
                                holidaysList.setAdapter(adapter);
                                pdialog.dismiss();
                                selectCountry.setText(selectedCountry);
                            }

                        }

                    }else {
                        pdialog.dismiss();
                        adapter=new HolidaysAdapter(getActivity(),cuntrywiseHolodays,HolidaysFragment.this);
                        holidaysList.setAdapter(adapter);
                        Utils.showAlertDialog(getContext(),"No Data Available",false);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pdialog.dismiss();
                Utils.showAlertDialog(getContext(), error.getMessage(),false);
            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Designation= PreferenceConnector.readString(getContext(), "Type","");
        if (Designation.equalsIgnoreCase("Admin")) {
            ((AdminSlideMenu) getActivity())
                    .setActionBarTitle("Holidays");

        }else {

            ((UserSlideMenu) getActivity())
                    .setActionBarTitle("Holidays");
        }
    }

    @Override
    public void onMethodCallback() {
        LoadHolidays(selectCountry.getText().toString().trim());
    }
}
