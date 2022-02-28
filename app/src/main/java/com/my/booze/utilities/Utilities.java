package com.my.booze.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utilities {

    static ProgressDialog dialog;
    private Context context;

    public Utilities(Context context){
        this.context = context;
    }


    public static void showProgressDialog(Context ctx, String msg) {
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }

    }

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences("KiksClientSharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {

        SharedPreferences sharedPref = context.getSharedPreferences("KiksClientSharedStorage", Context.MODE_PRIVATE);
        int val=sharedPref.getInt(key,0);
        return val;

    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("KiksClientSharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static String getString(Context context, String key) {

        SharedPreferences sharedPref = context.getSharedPreferences("KiksClientSharedStorage", Context.MODE_PRIVATE);
        String val=sharedPref.getString(key,"");

        return val;

    }

    public static void clearSharedPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("KiksClientSharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static String changeDateFormat(String date, String from, String to){

        String finalDate= date;

//        SimpleDateFormat date_format = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat date_format = new SimpleDateFormat(from);

        Date datee = null;
        try {
            datee = date_format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
        SimpleDateFormat df = new SimpleDateFormat(to);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datee);
        finalDate = df.format(calendar.getTime());

        return finalDate;

    }

    public static String differenceBetweenDated(String dates_formate, String dateEnd, String dateStr){

        String days = "";

        SimpleDateFormat sdf = new SimpleDateFormat(dates_formate);

        try {
            Date dateS = sdf.parse(dateStr);
            Date dateE = sdf.parse(dateEnd);

            long diff = dateE.getTime() - dateS.getTime();


            days = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }



        return days;

    }

    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdFormat.format(calendar.getTime());
        return currentDate;
    }

    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("h : m a");
        String currentDate = mdFormat.format(calendar.getTime());
        return currentDate;
    }

    public static String getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);
        return tomorrowAsString;
    }

    public static String formatTwoDecimal(Context context, int number){


        if (number == 0){
            return "RS 0.00";
        }
        else {

            String qa = String.valueOf(number);
            String noComma = qa.replace(",", "");
            Float f = Float.parseFloat(noComma);
            String.format("%.2f",f);
            DecimalFormat formatter = new DecimalFormat("#,###,###.00#");
            String yourFormattedString = formatter.format(f);
            return "RS "+yourFormattedString;
        }

    }


    public static boolean locationEnabled (Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(context)
                    .setMessage( "GPS Enable" )
                    .setPositiveButton( "Settings" , new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick (DialogInterface paramDialogInterface , int paramInt) {
                                    context.startActivity( new Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS )) ;
                                }
                            })
                    .setNegativeButton( "Cancel" , null )
                    .show() ;
        }

        return gps_enabled;
    }

    public static Date convertStringToDate(String stringDate){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

//    public static long Daybetween(String date1,String date2,String pattern)
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
//        Date Date1 = null,Date2 = null;
//        try{
//            Date1 = sdf.parse(date1);
//            Date2 = sdf.parse(date2);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
//    }

    public static int getCountOfDays(String createdDateString, String expireDateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date createdConvertedDate = null;
        Date expireCovertedDate = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar start = new GregorianCalendar();
        start.setTime(createdConvertedDate);

        Calendar end = new GregorianCalendar();
        end.setTime(expireCovertedDate);

        long diff = end.getTimeInMillis() - start.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);


        return (int) (dayCount);
    }

    public static String addDaysToDate(Date date, int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);  // number of days to add
        String newDate = sdf.format(c.getTime());
        return newDate;
    }

    public static String capitizeString(String name){
        String captilizedString="";
        if(!name.trim().equals("")){
            captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        return captilizedString;
    }

//    public static String addMonths(Date date, int numMonths){
//        date = date.setMonth((date.getMonth() - 1 + numMonths) % 12 + 1);
//
//    }

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {
        String address = null;
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    void startAnim(AVLoadingIndicatorView avi){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(AVLoadingIndicatorView avi){
        avi.hide();
        // or avi.smoothToHide();
    }
}
