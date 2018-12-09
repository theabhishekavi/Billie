package com.avi.abhishek.Billie;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LocationViewAdapter extends BaseAdapter {


   private ArrayList<String> details;
    TextView textView;
    DatabaseReference ref;
    FirebaseUser firebaseUser;
    String Username="";



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
    public View getView(final int position, final View convertView, final ViewGroup parent) {



        LayoutInflater li= LayoutInflater.from(parent.getContext());

        final View inflatedView;
        if (convertView==null){
        inflatedView=li.inflate(R.layout.location_design,parent,false);}
        else{
            inflatedView=convertView;}

        final String currentDetail=getItem(position);
        ref=FirebaseDatabase.getInstance().getReference();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Username=firebaseUser.getUid();


        Button btnShow,btnDisable;
        textView=inflatedView.findViewById(R.id.textView);
        btnShow=inflatedView.findViewById(R.id.btnShow);
        btnDisable=inflatedView.findViewById(R.id.btnDisable);


        textView.setText(currentDetail);
        notifyDataSetChanged();

       btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Global_Class.global_name=currentDetail;
               Log.e("yummy",Global_Class.global_name);
                Intent intent=new Intent(parent.getContext(),MapsActivity.class);
                parent.getContext().startActivity(intent);

                }
        });

        btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global_Class.global_name=currentDetail;
              /*  LocationManager lm = (LocationManager)parent.getContext().getSystemService(parent.getContext().LOCATION_SERVICE);
                if(lm!=null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    lm.removeTestProvider(LocationManager.GPS_PROVIDER);}
*/
//              if(((LocationManager)parent.getContext().getSystemService(parent.getContext().LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER))
//                  ((LocationManager)parent.getContext().getSystemService(parent.getContext().LOCATION_SERVICE)).removeTestProvider(LocationManager.GPS_PROVIDER);
//                Intent i=new Intent(parent.getContext(),TrackingService.class);
//                parent.getContext().stopService(i);
                details.remove(position);
                notifyDataSetChanged();
                ref.child("Location of "+Username+" "+Global_Class.global_name).removeValue();
                ref.child("Track " + Username +" ").child(currentDetail).removeValue();



            }
        });




        return inflatedView;
    }
}
