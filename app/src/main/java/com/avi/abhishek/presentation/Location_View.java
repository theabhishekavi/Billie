package com.avi.abhishek.presentation;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

        imageButton = findViewById(R.id.imageButton);
        etTrackerName = findViewById(R.id.etTrackerName);
        listView = findViewById(R.id.listView);

        Username = firebaseUser.getDisplayName();

        if (Username == null)
            Username = firebaseUser.getPhoneNumber();

            final LocationViewAdapter locationViewAdapter = new LocationViewAdapter(details);
            listView.setAdapter(locationViewAdapter);


            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i = new Intent(Location_View.this, Trip_Slide.class);
                    i.putExtra("tracker", etTrackerName.getText().toString());
                    startActivity(i);

                    name = etTrackerName.getText().toString();
                    dbref.child("Track " + Username +" ").push().setValue(name);
                    etTrackerName.setText("");
//                    details.add(name);
//                    locationViewAdapter.notifyDataSetChanged();



                }
            });

        dbref.child("Track " + Username +" ").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.getValue(String.class);
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


    }

