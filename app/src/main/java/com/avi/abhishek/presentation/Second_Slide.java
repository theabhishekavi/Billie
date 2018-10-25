package com.avi.abhishek.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.avi.abhishek.presentation.R.id.etName;

public class Second_Slide extends AppCompatActivity {

    ArrayList<Details> details = new ArrayList<>();

    ArrayList<String> x=new ArrayList<>();
    ArrayList<Integer> y=new ArrayList<>();

   DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
   FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    String tripname="";
    String Username="";

    EditText etName, etAmount;
    int number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_slide);

        Username = firebaseUser.getDisplayName();

        if(Username == null){
            Username=firebaseUser.getPhoneNumber();

        }
        final EditText etName, etAmount;

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);

       if(getIntent()!=null){

           tripname = (getIntent().getStringExtra("name"));
           String s = (getIntent().getStringExtra("keyy"));
        number=Integer.valueOf(s);
       }



        ListView listView;
        listView = findViewById(R.id.listView);

        final DetailsAdapter detailsAdapter = new DetailsAdapter(details);
        listView.setAdapter(detailsAdapter);

      final Button btnAdd = findViewById(R.id.btnAdd);

          btnAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String name, amount;
                  name = etName.getText().toString();
                  amount = etAmount.getText().toString();
                  etName.setText("");
                  etAmount.setText("");
                  Details detail = new Details(name, Integer.valueOf(amount));
                  if (Username != null)
                      dbref.child("Bill "+Username + " " + tripname).push().setValue(detail);


              }
          });

          dbref.child("Bill "+Username + " " + tripname).addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  Details data = dataSnapshot.getValue(Details.class);
                  details.add(data);
                  detailsAdapter.notifyDataSetChanged();
                  String name = data.getName();
                  int i = data.getMoney();
                  Log.e("tag111", "name is " + name);
                  x.add(name);
                  y.add(i);

               /*  x.add(data.getName());
                y.add(Integer.valueOf(data.getMoney().toString()));
*/
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


        Button btnCalculate =findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inew=new Intent(Second_Slide.this,Third_Slide.class);
                inew.putStringArrayListExtra("key",x);
                inew.putIntegerArrayListExtra("int",y);
                startActivity(inew);
            }
        });
    }

}
