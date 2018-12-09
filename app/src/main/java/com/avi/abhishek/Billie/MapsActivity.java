package com.avi.abhishek.Billie;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseUser firebaseUser;
    String Username="";
    DatabaseReference ref;
    Marker marker;

   LatLng latLng=new LatLng(21.2,34.5);

    double lat,longtitude;
    double prevLat=21.2,prevLong=34.5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        retriveLocation();
    }


    public void retriveLocation(){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Username=firebaseUser.getUid();
        ref=FirebaseDatabase.getInstance().getReference();
        ref.child("Location of "+Username+" "+Global_Class.global_name).child("Latitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Double.class)!=null) {
                    lat = dataSnapshot.getValue(Double.class);

                    latLng = new LatLng(lat, prevLong);
                    prevLat = lat;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.child("Location of "+Username+" "+Global_Class.global_name).child("Longitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Double.class) != null) {

                    longtitude = dataSnapshot.getValue(Double.class);
                    latLng = new LatLng(prevLat, longtitude);
                    if (marker != null) {
                        marker.remove();
                    }

                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(Global_Class.global_name));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0f));
                    prevLong = longtitude;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
