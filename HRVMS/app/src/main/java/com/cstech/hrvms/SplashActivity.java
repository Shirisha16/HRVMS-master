package com.cstech.hrvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Activities.LoginActivity;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Session;

public class SplashActivity extends AppCompatActivity {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session=new Session(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (session.getLoggedIn()){
                    String Type = PreferenceConnector.readString(getApplicationContext(),"Type","");

                    if (Type.equalsIgnoreCase("Employee")){
                        Intent intent=new Intent(getApplicationContext(), UserSlideMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else if (Type.equalsIgnoreCase("Admin")){
                        Intent intent=new Intent(getApplicationContext(), AdminSlideMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }    else {
                        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }


                }
                else {
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        },1000);
    }
}
