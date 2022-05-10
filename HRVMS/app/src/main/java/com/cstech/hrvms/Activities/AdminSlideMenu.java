package com.cstech.hrvms.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.cstech.hrvms.AdminFragments.AddClientFragment;
import com.cstech.hrvms.AdminFragments.AddVendorFragment;
import com.cstech.hrvms.AdminFragments.AdminHomeScreenFragment;
import com.cstech.hrvms.AdminFragments.ClientInvoiceFragment;
import com.cstech.hrvms.AdminFragments.EmployeeClientDetailsFragment;
import com.cstech.hrvms.AdminFragments.EmployeeTimesheetFragment;
import com.cstech.hrvms.AdminFragments.FindEmployeeFragment;
import com.cstech.hrvms.Fragments.ChangePasswordFragment;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Supporters.ConnectionDetector;
import com.cstech.hrvms.Supporters.Session;
import com.cstech.hrvms.Supporters.Utils;
import com.cstech.hrvms.UserFragments.HolidaysFragment;
import com.cstech.hrvms.UserFragments.NewsFragments;
import com.google.android.material.navigation.NavigationView;

public class AdminSlideMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ConnectionDetector detector;
    Session session;
    public static boolean ExitApp=false;
    LinearLayout Home,news,CreateInvoice,ClientInvoice,FindEmployee,holidays,EmployeeClientDetails,AddVendor,AddClient,Logout,ChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_slide_menu);

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
        CreateInvoice = header.findViewById(R.id.CreateInvoice);
        ClientInvoice = header.findViewById(R.id.ClientInvoice);
        FindEmployee = header.findViewById(R.id.FindEmployee);
        holidays = header.findViewById(R.id.holidays);
        EmployeeClientDetails = header.findViewById(R.id.EmployeeClientDetails);
        AddVendor = header.findViewById(R.id.AddVendor);
        AddClient = header.findViewById(R.id.AddClient);
        Logout = header.findViewById(R.id.Logout);
        ChangePassword = header.findViewById(R.id.ChangePassword);
        news = header.findViewById(R.id.news);

        Utils.navigationFragmentReplace(AdminSlideMenu.this, new AdminHomeScreenFragment(), "Admin", null);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new AdminHomeScreenFragment(), "Admin", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

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

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new HolidaysFragment(), "Holidays", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        CreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new EmployeeTimesheetFragment(), "Create Invoice", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        ClientInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new ClientInvoiceFragment(), "Client Invoice", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        FindEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new FindEmployeeFragment(), "Find Employee", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        EmployeeClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new EmployeeClientDetailsFragment(), "Employee Client", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        AddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new AddClientFragment(), "Add Client", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

                }
            }
        });

        AddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
                if (detector.isConnectingToInternet()){

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new AddVendorFragment(), "Add Vendor", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

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

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new ChangePasswordFragment(), "Change Password", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

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

                    Utils.navigationFragmentReplace(AdminSlideMenu.this, new NewsFragments(), "News", null);

                }else {
                    Utils.showAlertDialog(AdminSlideMenu.this, "Please Check your Internet Connection", false);

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


                    AdminHomeScreenFragment dashboard = (AdminHomeScreenFragment) getSupportFragmentManager().findFragmentByTag("Admin");
                    setTitle("Admin");


                    if (dashboard != null && !dashboard.isVisible()) {
                        // adding your code here
                        Utils.navigationFragmentReplace(AdminSlideMenu.this, new AdminHomeScreenFragment(), "Admin", null);


                    } else {
                        Toast.makeText(getApplicationContext(), "Tap again to Exit", Toast.LENGTH_LONG).show();
                        ExitApp = true;
                    }

            } else {

                Intent i = new Intent(getApplicationContext(), ExitActivity.class);
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
                Intent intent=new Intent(AdminSlideMenu.this, LoginActivity.class);
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
