package com.techhive.smartdrive.Utilities;

/**
 * Created by Sunain Mittal on 1/1/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    SharedPreferences sharedPreferences;
    Context mContext;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "sessionPref";
    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    public void saveIsLoggedIn(Context context, Boolean isLoggedIn){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean ("IS_LOGGED_IN", isLoggedIn);
        editor.commit();

    }

    public boolean getISLogged_IN() {
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }

    public void saveToken(Context context, String toke){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID_TOKEN", toke);
        editor.commit();
    }

    public void saveuid(Context context, String uid){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_ID", uid);
        editor.commit();
    }
    public String getuid(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("USER_ID", "");
    }
    public String getUserToken(){
        //mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("ID_TOKEN", "");
    }

    public void saveEmail(Context context, String email){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL", email);
        editor.commit();
    }
//    public void saveValidity(Context context, int a){
//        mContext = context;
//        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("VALID", a);
//        editor.commit();
//    }
    public String getUserEmail(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("EMAIL", null);
    }


    public void saveName(Context context, String name){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME", name);
        editor.commit();
    }

    public String getName(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("NAME", null);
    }
//    public int getValid(){
//        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        return sharedPreferences.getInt("VALID",0);
//    }

    public void saveLongitude(Context context, String longitude){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Longitude", longitude);
        editor.commit();
    }
    public String getLongitude(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("Longitude", null);
    }
    public void saveLatitude(Context context, String latitude){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Latitude", latitude);
        editor.commit();
    }
    public String getLatitude(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("Latitude", null);
    }
    public void savePhoto(Context context, String photo){
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PHOTO", photo);
        editor.commit();
    }

    public String getPhoto(){
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("PHOTO", null);
    }

    public void clear(){
        editor.clear();
        editor.apply();
    }

    public void savePhone(Context context, String phoneno) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PHONE", phoneno);
        editor.commit();
    }
    public String getPhone()
    {
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getString("PHONE", null);
    }
}