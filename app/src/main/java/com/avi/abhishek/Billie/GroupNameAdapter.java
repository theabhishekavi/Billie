package com.avi.abhishek.Billie;

import android.content.Intent;
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

public class GroupNameAdapter extends BaseAdapter {
    private ArrayList<String> name;
    TextView textname;
    Button btnOpen,btnDelete;
    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    String username;


    public GroupNameAdapter(ArrayList<String> name) {
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public String getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater li=LayoutInflater.from(parent.getContext());
        final View inflatedView;
        if (convertView==null)
            inflatedView=li.inflate(R.layout.group_name_design,parent,false);
        else
            inflatedView=convertView;

        final String CurrentName=getItem(position);
        textname=inflatedView.findViewById(R.id.groupname);
        btnOpen=inflatedView.findViewById(R.id.btnOpen);
        btnDelete=inflatedView.findViewById(R.id.btnDelete);
       username=firebaseUser.getUid();


        textname.setText(CurrentName);
        notifyDataSetChanged();
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(parent.getContext(),Second_Slide.class);
                i.putExtra("name",CurrentName);
                parent.getContext().startActivity(i);

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.remove(position);
                notifyDataSetChanged();
                dbref.child(username+" Group_Names").child(CurrentName).removeValue();
                dbref.child("Bill Details "+username + " "+CurrentName).removeValue();
            }
        });





        return inflatedView;
    }
}
