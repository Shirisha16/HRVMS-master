package com.cstech.hrvms.AdminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.Utils;
import com.cstech.hrvms.UserFragments.HolidaysFragment;
import com.cstech.hrvms.UserFragments.NewsFragments;

public class AdminHomeScreenFragment extends Fragment {

    LinearLayout holidays,news,EmployeeTimesheetLinearLayout,ClientInvoiceLinearLayout,FindEmployeeLinearLayout,EmployeeClientDetailsLinearLayout,AddVendorLinearLayout,AddClientLinearLayout,AddEmployeeLinearLayout;

    public AdminHomeScreenFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AdminSlideMenu) getActivity())
                .setActionBarTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.admin_home_activity, container, false);

        EmployeeTimesheetLinearLayout = view.findViewById(R.id.EmployeeTimesheetLinearLayout);
        ClientInvoiceLinearLayout = view.findViewById(R.id.ClientInvoiceLinearLayout);
        FindEmployeeLinearLayout = view.findViewById(R.id.FindEmployeeLinearLayout);
        EmployeeClientDetailsLinearLayout = view.findViewById(R.id.EmployeeClientDetailsLinearLayout);
        AddVendorLinearLayout = view.findViewById(R.id.AddVendorLinearLayout);
        AddClientLinearLayout = view.findViewById(R.id.AddClientLinearLayout);
        AddEmployeeLinearLayout = view.findViewById(R.id.AddEmployeeLinearLayout);
        holidays = view.findViewById(R.id.holidays);
        news = view.findViewById(R.id.news);

        AddEmployeeLinearLayout.setVisibility(View.GONE);



        EmployeeTimesheetLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new EmployeeTimesheetFragment(), "Employee Timesheet", null);
            }
        });


        ClientInvoiceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new ClientInvoiceFragment(), "Client Invoice", null);
            }
        });

        FindEmployeeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new FindEmployeeFragment(), " Find Employee", null);
            }
        });


        EmployeeClientDetailsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new EmployeeClientDetailsFragment(), "Employee Client Details", null);
            }
        });

        AddVendorLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new AddVendorFragment(), "Add Vendor", null);
            }
        });

        AddClientLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new AddClientFragment(), "Add Client", null);
            }
        });
        AddEmployeeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new AddEmployeeFragment(), "Add Client", null);
            }
        });

        holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new HolidaysFragment(), "Holidays", null);

            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigationFragmentAdd(getActivity(), new NewsFragments(), "News", null);

            }
        });


        return view;
    }
}
