package com.cstech.hrvms.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.Utils;

public class UserHomeScreenFragment extends Fragment {

    LinearLayout CreateTimesheet,ViewTimesheet,ViewHolidays,ViewNewsEvents;

    public UserHomeScreenFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.user_home_activity, container, false);

        CreateTimesheet=view.findViewById(R.id.CreateTimeSheetLinearLayout);
        ViewTimesheet=view.findViewById(R.id.ViewTimeSheetLinearLayout);
        ViewHolidays=view.findViewById(R.id.ViewHolidays);
        ViewNewsEvents=view.findViewById(R.id.ViewNewsEvents);


        CreateTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.navigationFragmentAdd(getActivity(), new AddTimesheet(), "Create Timesheet", null);

            }
        });

        ViewTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.navigationFragmentAdd(getActivity(), new ViewTimeSheetFragment(), "View Timesheet", null);

            }
        });

        ViewHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.navigationFragmentAdd(getActivity(), new HolidaysFragment(), "Holidays", null);

            }
        });

        ViewNewsEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.navigationFragmentAdd(getActivity(), new NewsFragments(), "News & Events", null);

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((UserSlideMenu) getActivity())
                .setActionBarTitle("Home");
    }
}
