package com.techhive.smartdrive.Speed;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunain on 3/14/2018.
 */

public class Accelerationlist {
    Context context;
    List prespeed,acc,postspeed;
    boolean alert,isMessage;
    String message;
    public Accelerationlist(Context context)
    {
        this.context=context;
        prespeed=new ArrayList<Integer>();
        postspeed=new ArrayList<Integer>();
        acc=new ArrayList<Integer>();
        isMessage=false;
        message="";
        alert=false;
    }
    public void adddata(double oldspeed, double newspeed, double acceleration)
    {
        message="";
        int ospeed=(int)oldspeed;
        int nspeed=(int)newspeed;
        int a=(int)acceleration;
        if(acceleration>0)
        {
            acc.clear();
            postspeed.clear();
            prespeed.clear();
            alertingsystem(0);
            return;
        }
        if(acceleration<=0)
        {
           acc.add(a);
           prespeed.add(ospeed);
           postspeed.add(nspeed);
           checkingfunc();
        }
    }
    public boolean ismessagechange()
    {
        return isMessage;
    }
    public String getMessage()
    {
        isMessage=false;
        return message;
    }
    private void checkingfunc()
    {
        if(acc.size()<3){return;}
        for(int i=0;i<acc.size();i++)
        {
            if((int)acc.get(i)<=-5)
            {
                if(postspeed.contains(0))
                {
                    alertingsystem(1);
                }
            }
        }
    }
    private void alertingsystem(int i)
    {
        if(i==0)
        {
            if(alert==true)
            {
                alert=false;
                //sorry wrong alert
                message="0";
                isMessage=true;
            }
        }
        if(i==1)
        {
            if(alert==false)
            {
                alert=true;
                //alert accident
                message="1";
                isMessage=true;
            }
        }
    }
}
