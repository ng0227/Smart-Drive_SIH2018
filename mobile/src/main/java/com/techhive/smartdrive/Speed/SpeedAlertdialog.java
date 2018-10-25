package com.techhive.smartdrive.Speed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.techhive.smartdrive.R;

/**
 * Created by Sunain Mittal on 1/1/2018.
 */
public class SpeedAlertdialog extends Dialog {
    public Activity c;
    public Dialog d;
    protected boolean _active = true;
    protected int _splashTime = 2000;

    public SpeedAlertdialog(Context c)
    {
        super(c);

    }
    public SpeedAlertdialog(Activity a)
    {
        super(a);
        this.c = a;
    }
    public SpeedAlertdialog(Activity a, int img, String txt, int y)
    {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.speed_dailog);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    dismiss();
                }
            };
        };
        splashTread.start();
    }

}
