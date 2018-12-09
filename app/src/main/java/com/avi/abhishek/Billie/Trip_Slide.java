/*package com.avi.abhishek.presentation;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class Trip_Slide extends AppCompatActivity {
     String trackerName="";
    private static final int PERMISSIONS_REQUEST = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish();
        }
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
            finish();
            }
    }

    private void startTrackerService() {

        if(getIntent()!=null)
            trackerName=getIntent().getStringExtra("tracker");
        Global_Class.global_name=trackerName;
        Toast.makeText(this,"GPS tracking Enabled for "+trackerName +" ! ",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Trip_Slide.this,TrackingService.class);

          i.putExtra("tracker",trackerName);
        startService(i);
        finish();


    }
}
*/