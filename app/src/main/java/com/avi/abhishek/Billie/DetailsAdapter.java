package com.avi.abhishek.Billie;

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

public class DetailsAdapter extends BaseAdapter {

    public DetailsAdapter(ArrayList<Details> details) {
        this.details = details;
    }

    ArrayList<Details> details;
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference();
    String username="";


    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Details getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());

        View inflatedView;
        if (convertView==null)
         inflatedView=li.inflate(R.layout.data_design,parent,false);
        else
            inflatedView=convertView;

        final Details currentDetail = getItem(position);

        TextView etNametrv,etAmounttrv;
        etNametrv=inflatedView.findViewById(R.id.etNametrv);
        etAmounttrv=inflatedView.findViewById(R.id.etAmounttrv);
        Button btnDelete = inflatedView.findViewById(R.id.btnDelete);
        username=firebaseUser.getUid();


        etNametrv.setText(currentDetail.getName());
        String n="Rs."+String.valueOf(currentDetail.getMoney());

        etAmounttrv.setText(n);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.remove(position);
                notifyDataSetChanged();
               dbref.child("Bill Details "+username + " "+Global_Class.billTripname+"").child(""+currentDetail.getName()+""+currentDetail.getMoney()).removeValue();
            }
        });

        return inflatedView;
    }


}
