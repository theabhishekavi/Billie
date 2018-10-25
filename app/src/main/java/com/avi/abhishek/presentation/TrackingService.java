package com.avi.abhishek.presentation;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackingService extends Service  {
    String trackerName="";
    FirebaseUser firebaseUser;


    private static final String Tag = TrackingService.class.getSimpleName();
    @Override
    public IBinder onBind(Intent intent) {
        trackerName=intent.getStringExtra("tracker");
        Log.e("nameee",trackerName);
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        loginfirebase();
    }



    private void buildNotification() {
        String stop="stop";
        registerReceiver(broadcastReceiver,new IntentFilter(stop));
        PendingIntent broadcastIntent=PendingIntent.getBroadcast(this,0,new Intent(stop),PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder=new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Tracking is currently Enabled")
                .setSmallIcon(R.drawable.ic_launcher_background);
           startForeground(1,builder.build());

    }


    protected BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             unregisterReceiver(broadcastReceiver);
             stopSelf();
        }
    };


    public void loginfirebase(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
            requestLocationUpdates();
        }



    public void requestLocationUpdates(){
        LocationRequest request=new LocationRequest();
        request.setInterval(10000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client=LocationServices.getFusedLocationProviderClient(this);
        int permission=ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission== PackageManager.PERMISSION_GRANTED){
            client.requestLocationUpdates(request,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                     Location location= locationResult.getLastLocation();
                     String s=Global_Class.global_name;
                     if(location!=null){
                         ref.child("Location of "+Global_Class.global_name).child("Latitude").setValue(location.getLatitude());
                         ref.child("Location of "+Global_Class.global_name).child("Longitude").setValue(location.getLongitude());
                     }
                }
            },null);
        }
    }

}
