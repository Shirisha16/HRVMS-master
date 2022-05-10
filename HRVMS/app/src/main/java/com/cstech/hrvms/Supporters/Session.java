package com.cstech.hrvms.Supporters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Session(Context context) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean setLoging(boolean status){
        editor=preferences.edit();
        editor.putBoolean("loggedIn",status);
        editor.apply();
        return  true;
    }

    public boolean getLoggedIn(){

        return preferences.getBoolean("loggedIn",false);
    }
}
