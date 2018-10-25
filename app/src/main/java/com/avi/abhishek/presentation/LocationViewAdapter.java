package com.avi.abhishek.presentation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LocationViewAdapter extends BaseAdapter {


   private ArrayList<String> details;


    public LocationViewAdapter(ArrayList<String> details) {
        this.details = details;
    }


    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public String getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {



        LayoutInflater li= LayoutInflater.from(parent.getContext());

        View inflatedView;
        if (convertView==null){
        inflatedView=li.inflate(R.layout.location_design,parent,false);}
        else{
            inflatedView=convertView;}

        String currentDetail=getItem(position);

        TextView textView;
        Button btnShow,btnDisable;
        textView=inflatedView.findViewById(R.id.textView);
        btnShow=inflatedView.findViewById(R.id.btnShow);
        btnDisable=inflatedView.findViewById(R.id.btnDisable);
        textView.setText(currentDetail);
        notifyDataSetChanged();

       btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(),MapsActivity.class);
                parent.getContext().startActivity(intent);
                }
        });

        btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });




        return inflatedView;
    }
}
