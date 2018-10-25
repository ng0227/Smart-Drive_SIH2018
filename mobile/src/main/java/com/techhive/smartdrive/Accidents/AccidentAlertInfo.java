package com.techhive.smartdrive.Accidents;

import com.techhive.smartdrive.Utilities.SharedPrefManager;

/**
 * Created by Sunain on 3/27/2018.
 */

public class AccidentAlertInfo
{
    double longitude,latitude;
    String email,phonenumber;
    public SharedPrefManager sharedPrefManager;

    public AccidentAlertInfo(){}
    public AccidentAlertInfo(String email,String phonenumber,double longi,double lati)
    {
        this.latitude=lati;
        this.longitude=longi;
        this.email=email;
        this.phonenumber=phonenumber;
    }
    public String getEmail(){return email;}
    public String getPhonenumber(){return phonenumber;}
    public double getLongitude(){return longitude;}
    public double getLatitude(){return latitude;}

}
