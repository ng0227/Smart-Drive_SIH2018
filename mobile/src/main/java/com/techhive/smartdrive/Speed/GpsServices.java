package com.techhive.smartdrive.Speed;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techhive.smartdrive.Accidents.AccidentAlertInfo;
import com.techhive.smartdrive.Activities.NavActivity;
import com.techhive.smartdrive.Problems.ProblemInfo;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.Utilities.SharedPrefManager;

import java.util.Date;

public class GpsServices extends Service implements LocationListener, GpsStatus.Listener {
    private LocationManager mLocationManager;

    Location lastlocation = new Location("last");
    Data data;
    double oldspeed=0.0,oldtime=System.currentTimeMillis();
    Accelerationlist a;
    double currentLon=0 ;
    double currentLat=0 ;
    double lastLon = 0;
    double lastLat = 0;

    PendingIntent contentIntent;
    static NotificationManager notificationManager;
    private static CountDownTimer countDownTimer;
    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

        Intent notificationIntent = new Intent(this, NavActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        contentIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, 0);
        a=new Accelerationlist(getApplicationContext());
       updateNotification(false);
//        startCountDownTimer();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener( this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void onLocationChanged(Location location) {
        data = NavActivity.getData();
        if (data.isRunning()){
            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            if (data.isFirstTime()){
                lastLat = currentLat;
                lastLon = currentLon;
                //startCountDownTimer();
                data.setFirstTime(false);
            }

            lastlocation.setLatitude(lastLat);
            lastlocation.setLongitude(lastLon);
            double distance = lastlocation.distanceTo(location);

            if (location.getAccuracy() < distance){
                data.addDistance(distance);

                lastLat = currentLat;
                lastLon = currentLon;
            }

            if (location.hasSpeed()) {
                data.setCurSpeed(location.getSpeed()*3.6);
                getacceleration(location.getSpeed());
                //startCountDownTimer();
                if(location.getSpeed() == 0){
                    new isStillStopped().execute();
                }
            }
            data.update();
            updateNotification(true);
        }
    }

    public void getacceleration(double l)
    {
        double speeddiff=l-oldspeed;
        double timediff=(System.currentTimeMillis()-oldtime)/(1000);
        oldtime=System.currentTimeMillis();
        double acc=speeddiff/timediff;
        a.adddata(oldspeed,l,acc);
        oldspeed=l;
        if(a.ismessagechange())
        {
            String title = a.getMessage();
            if(title.equals("1"))
            {
                startCountDownTimer();
            }
//            if(title.equals("0"))
//            {
//                showMessageDialog("False Alert");
//            }

        }
    }

    private void startNotification(int i){
        Notification notification = new Notification(R.drawable.logo, null,
                System.currentTimeMillis());
        RemoteViews notificationView = new RemoteViews(getPackageName(),
                R.layout.mynotification);

        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(this, AlertActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;

        //this is the intent that is supposed to be called when the
        //button is clicked
        Intent switchIntent = new Intent(this, switchButtonListener.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0,
                switchIntent, 0);
        notificationView.setProgressBar(R.id.alertprogressBarCircle,20,i,false);
        notificationView.setTextViewText(R.id.alerttextview,"Sending accident alert in "+i+"s");
        notificationView.setOnClickPendingIntent(R.id.Alertcancelbutton,
                pendingSwitchIntent);
        notificationManager.notify(2, notification);
    }
    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Here", "I am here");
            Toast.makeText(context,"working button",Toast.LENGTH_LONG).show();
            cancelAlert();
        }
    }
    public static void cancelAlert()
    {

        stopCountDownTimer();
        //notificationManager.cancel(2);
    }
    public static void cancelAlert2()
    {
        notificationManager.cancel(2);
    }

    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                startNotification((int)(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Alert will be sent",Toast.LENGTH_LONG).show();
                AlertAcident ad=new AlertAcident(currentLat,currentLon);
                ad.Alertnotify();
                stopCountDownTimer();
                cancelAlert2();
            }

        }.start();
        countDownTimer.start();
    }

    private static void stopCountDownTimer() {
        countDownTimer.cancel();
        cancelAlert2();
    }
    public void updateNotification(boolean asData){
        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setContentTitle("Smart Drive Safety Service")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(contentIntent);

        if(asData){
            builder.setContentText(String.format("Your speed: "+ (int)data.getCurSpeed()+"km/h", data.getCurSpeed(), data.getDistance()));

        }else{
            builder.setContentText(String.format("Starting Please Wait......", "-", "-"));
        }
        Notification notification = builder.build();
        startForeground(R.string.noti_id, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }   
       
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
   
    /* Remove the locationlistener updates when Services is stopped */
    @Override
    public void onDestroy() {
        mLocationManager.removeUpdates(this);
        mLocationManager.removeGpsStatusListener(this);
        stopForeground(true);
    }

    @Override
    public void onGpsStatusChanged(int event) {}

    @Override
    public void onProviderDisabled(String provider) {}
   
    @Override
    public void onProviderEnabled(String provider) {}
   
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    class isStillStopped extends AsyncTask<Void, Integer, String> {
        int timer = 0;
        @Override
        protected String doInBackground(Void... unused) {
            try {
                while (data.getCurSpeed() == 0) {
                    Thread.sleep(1000);
                    timer++;
                }
            } catch (InterruptedException t) {
                return ("The sleep operation failed");
            }
            return ("return object when task is finished");
        }

        @Override
        protected void onPostExecute(String message) {
            data.setTimeStopped(timer);
        }
    }
    public class AlertAcident
    {
        public String Database_Path = "Accident_alert_notification",Database_Path1 = "Client_Problems",email,phonenumer;
        DatabaseReference databaseReference,databaseReference1;
        double longi,lati;
        public SharedPrefManager sharedPrefManager;
        public AlertAcident(double lati,double longi)
        {
            databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
            databaseReference1 = FirebaseDatabase.getInstance().getReference(Database_Path1);
            this.longi=longi;
            this.lati=lati;
            sharedPrefManager = new SharedPrefManager(getApplicationContext());
            email=sharedPrefManager.getUserEmail();
            phonenumer=sharedPrefManager.getPhone();
        }
        public void Alertnotify()
        {
            AccidentAlertInfo alert=new AccidentAlertInfo(email,phonenumer,longi,lati);
            String UploadId = databaseReference.push().getKey();
            databaseReference.child(UploadId).setValue(alert);
            reportproblem();
        }
        private void reportproblem()
        {
            String UploadId = databaseReference1.push().getKey();
            ProblemInfo UploadInfo = new ProblemInfo(new Date().toLocaleString(),UploadId,email,String.valueOf(longi),String.valueOf(lati),"0","3","6","Auto accident","");
            databaseReference1.child(UploadId).setValue(UploadInfo);
        }

    }
}
