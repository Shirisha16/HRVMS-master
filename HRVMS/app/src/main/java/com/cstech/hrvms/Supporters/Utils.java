package com.cstech.hrvms.Supporters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.cstech.hrvms.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utils {

    public static final String ALERT_NETWORK_NOT_FOUND = "Please check your connection and try again.";
    public static Bitmap BitMap;
    public static String userProfile="";
    public static String userName="";
    public static List<Integer> skillId=new ArrayList<>();
    public static String UserRole;
    public static String SelectedEmplyeId,LoginType;
    public static String AddChildMedicationResult="";
    public static String AddImmunization="";
    public static String SuccessMessage="";
    private static ProgressDialog progressDialog;


    public static long convertLiveDateToMillis(String time) {
        //String format = "dd-MMM-yyyy HH:mm";
        String format = "dd-MMM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();


    }

    public static Date convertLiveDateToMillis1(String time) {
        String format = "MMM-dd-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;

    }

    public static Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            String value = object.getString(key);

            map.put(key, value);
        }
        return map;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /* Shared preference methods */
    public static void putString(Activity activity, String key, String value, int type) {
        SharedPreferences sp = activity.getSharedPreferences("ITecy", Context.MODE_PRIVATE);

        switch (type) {
            case 0:
                sp.edit().putInt(key, Integer.parseInt(value)).commit();
                break;

            case 1:
                sp.edit().putString(key, value).commit();
                break;

            case 2:
                sp.edit().putBoolean(key, Boolean.parseBoolean(value)).commit();
                break;

            default:
                break;

        }
    }

    public static String getString(Activity activity, String key, int type) {
        SharedPreferences sp = activity.getSharedPreferences("ITecy", Context.MODE_PRIVATE);
        String value = "";
        switch (type) {
            case 0:
                value = String.valueOf(sp.getInt(key, 0));
                break;

            case 1:
                value = String.valueOf(sp.getString(key, ""));
                break;

            case 2:
                value = String.valueOf(sp.getBoolean(key, false));
                break;

            default:
                break;


        }
        return value;
    }

    public static void navigateToActivity(Activity sourceActivity, Class destinationActivity){
        Intent intent = new Intent(sourceActivity,destinationActivity);
        sourceActivity.startActivity(intent);

    }

    @SuppressWarnings("deprecation")
    public static void showAlertDialog(Context context, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dl, int which) {
                dl.dismiss();

            }
        });
        alertDialog.show();
    }



    public static void navigationFragmentReplace(AppCompatActivity context, Fragment fragment, String TAG, Bundle bundle)
    {
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if (bundle!=null)
        {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.frameLayout,fragment,TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public static void navigationFragmentAdd(FragmentActivity context, Fragment fragment, String TAG, Bundle bundle)
    {
        FragmentManager fragmentManager=context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if (bundle!=null)
        {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.frameLayout,fragment,TAG);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();

    }


    public static void showProgressDialog(Context context){

        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public static void dismissProgressDialog(){

        progressDialog.dismiss();

    }



    public  interface KEYS
    {
        String PASSWORD="PASSWORD";
        String OPINION_POLL_ID="OPINION_POLL_ID";
        String IS_ANSWERED="IS_ANSWERED";
    }

    public static String stringTocantinsCase(String s)
    {
        String string=s.toString().toLowerCase();
        StringBuilder totalString = new StringBuilder();

        String inputString[]=string.split(" ");

        for (String s1: inputString)
        {
            if (s1.length()>1)
            {
                String word = s1.substring(0, 1).toUpperCase() + s1.substring(1).toLowerCase();
                totalString.append(word+" ");

            }
            else {
                totalString.append(s1+" ");
            }

        }
        return totalString.toString();
    }
}
