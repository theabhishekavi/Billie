package com.avi.abhishek.Billie;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Location_View extends AppCompatActivity {

//    String trackerName="";
        private static final int PERMISSIONS_REQUEST = 100;
        ArrayList<String> details = new ArrayList<>();
        ImageButton imageButton;
        EditText etTrackerName;
        ListView listView;
        String Username = "";
        String name;
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_tracking);
        TrackingService.myActivity=this;

        imageButton = findViewById(R.id.imageButton);
        etTrackerName = findViewById(R.id.etTrackerName);
        listView = findViewById(R.id.listView);

        Username = firebaseUser.getUid();

//        if (Username == null)
//            Username = firebaseUser.getPhoneNumber();
//        Global_Class.global_name=etTrackerName.getText().toString();


        final LocationViewAdapter locationViewAdapter = new LocationViewAdapter(details);
//        Global_Class.global_name=etTrackerName.getText().toString();
        listView.setAdapter(locationViewAdapter);


            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageButton.setEnabled(false);
                 //   Global_Class.global_name=etTrackerName.getText().toString();
                    LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);


                    if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        finish();
                    }
                    int permission = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        startTrackerService();
                    } else {
                        ActivityCompat.requestPermissions(Location_View.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST);
                    }

//                    Intent i = new Intent(Location_View.this, Trip_Slide.class);
//                    i.putExtra("tracker", etTrackerName.getText().toString());
//                    startActivity(i);
//                    name = etTrackerName.getText().toString();
//                    dbref.child("Track " + Username +" ").push().setValue(name);
//                    etTrackerName.setText("");
//                   details.add(name);
//                    locationViewAdapter.notifyDataSetChanged();
                }
            });

        dbref.child("Track " + Username +" ").child(etTrackerName.getText().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.getValue(String.class);
               // Global_Class.global_name=data;
                details.add(data);
                locationViewAdapter.notifyDataSetChanged();
                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
           // finish();
        }
    }

    private void startTrackerService() {
        name = etTrackerName.getText().toString();
        dbref.child("Track " + Username +" ").child(name).setValue(name);
        etTrackerName.setText("");
//        if(getIntent()!=null)
//            trackerName=getIntent().getStringExtra("tracker");
//        Global_Class.global_name=trackerName;
        Toast.makeText(this,"GPS tracking Enabled for "+name +" ! ",Toast.LENGTH_SHORT).show();
        Global_Class.global_name=name;
        Intent i=new Intent(Location_View.this,TrackingService.class);
//        i.putExtra("tracker",name);
        startService(i);
//        finish();


    }
    }

