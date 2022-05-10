package com.cstech.hrvms.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cstech.hrvms.Fragments.ChangePasswordFragment;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.UserFragments.AddTimesheet;
import com.cstech.hrvms.UserFragments.HolidaysFragment;
import com.cstech.hrvms.UserFragments.NewsFragments;
import com.cstech.hrvms.UserFragments.UserHomeScreenFragment;
import com.cstech.hrvms.UserFragments.ViewTimeSheetFragment;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.ConnectionDetector;
import com.cstech.hrvms.Supporters.Session;
import com.cstech.hrvms.Supporters.Utils;
import com.google.android.material.navigation.NavigationView;

public class UserSlideMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ConnectionDetector detector;
    Session session;
    public static boolean ExitApp=false;
    LinearLayout Home,CreateTimesheet,ViewTimesheet,Logout,ChangePassword,holidays,news;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_slide_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        detector=new ConnectionDetector(this);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        session=new Session(this);
        View header = navigationView.getHeaderView(0);
        Home = header.findViewById(R.id.Home);
        CreateTimesheet = header.findViewById(R.id.createTimesheet);
        ViewTimesheet = header.findViewById(R.id.viewTimesheet);
        holidays = header.findViewById(R.id.holidays);
        Logout = header.findViewById(R.id.Logout);
        userName = header.findViewById(R.id.userName);
        ChangePassword = header.findViewById(R.id.ChangePassword);
        news = header.findViewById(R.id.news);

        Utils.navigationFragmentReplace(UserSlideMenu.this, new UserHomeScreenFragment(), "Home", null);

        String User =PreferenceConnector.readString(getApplicationContext(),"UserName","");
        userName.setText("Logged in as " +User);


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){


                    Utils.navigationFragmentReplace(UserSlideMenu.this, new UserHomeScreenFragment(), "Home", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    setTitle("Holidays");
                    Utils.navigationFragmentReplace(UserSlideMenu.this, new HolidaysFragment(), "Holidays", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });
        CreateTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(UserSlideMenu.this, new AddTimesheet(), "Create Timesheet", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }            }
        });
        ViewTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(UserSlideMenu.this, new ViewTimeSheetFragment(), "View Timesheet", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(UserSlideMenu.this, new ChangePasswordFragment(), "Change Password", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(UserSlideMenu.this, new NewsFragments(), "News & Events", null);

                }else {
                    Utils.showAlertDialog(UserSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });




    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {


            FragmentManager fragmentManager = getSupportFragmentManager();

            if (!ExitApp) {

                UserHomeScreenFragment dashboard = (UserHomeScreenFragment) getSupportFragmentManager().findFragmentByTag("Home");
                setTitle("Home");


                if (dashboard != null && !dashboard.isVisible()) {
                    // adding your code here
                    Utils.navigationFragmentReplace(UserSlideMenu.this, new UserHomeScreenFragment(), "Home", null);


                } else {
                    Toast.makeText(getApplicationContext(), "Tap again to Exit", Toast.LENGTH_LONG).show();
                    ExitApp = true;
                }

            } else {

                    Intent i = new Intent(getApplicationContext(),ExitActivity.class);
                    startActivity(i);

            }
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                    ExitApp = false;
                    // finish();

                }
            }.start();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    public void logout(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                session.setLoging(false);
                Intent intent=new Intent(UserSlideMenu.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
