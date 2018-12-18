package com.avi.abhishek.Billie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Second_Slide extends AppCompatActivity {

    ArrayList<Details> details = new ArrayList<>();

    ArrayList<String> x=new ArrayList<>();
    ArrayList<Integer> y=new ArrayList<>();
    Details detail;
    Details data;


   DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
   FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    String tripname="";
    String Username;
    String dbname, dbamount;
    EditText etName, etAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_slide);
        Username=firebaseUser.getUid();

        final EditText etName, etAmount;

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);

       if(getIntent()!=null){
           tripname = (getIntent().getStringExtra("name"));
           Global_Class.billTripname=tripname;
        }



        ListView listView;
        listView = findViewById(R.id.listView);

        final DetailsAdapter detailsAdapter = new DetailsAdapter(details);
        listView.setAdapter(detailsAdapter);

      final Button btnAdd = findViewById(R.id.btnAdd);

      etAmount.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
              String st=etAmount.getText().toString().trim();
              for (int i=0;i<st.length();i++){
                  int x=(int)st.charAt(i);
                  if(x>=48&&x<=57){
                      btnAdd.setEnabled(true);
                  }
                  else {
                      btnAdd.setEnabled(false);
                      break;
                      }

              }
          }

          @Override
          public void afterTextChanged(Editable s) {

              }
      });




          btnAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                      dbname = etName.getText().toString();
                      dbamount = etAmount.getText().toString();

                  detail = new Details(dbname,Integer.valueOf(dbamount));

                  dbref.child("Bill Details "+Username + " "+Global_Class.billTripname).child(""+etName.getText().toString()+""+etAmount.getText().toString()).push()
                              .setValue(detail);

                              }
          });





        dbref.child("Bill Details "+Username + " "+Global_Class.billTripname).child(""+etName.getText().toString()+""+etAmount.getText().toString())
                .addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       data = new Details(ds.child("name").getValue(String.class), ds.child("money").getValue(Integer.class));}
//                  Details data = dataSnapshot.getValue(Details.class);
                      details.add(data);
                     detailsAdapter.notifyDataSetChanged();
                      String name = data.getName();
                      int i = data.getMoney();
                      x.add(name);
                      y.add(i);
                      etName.setText("");
                      etAmount.setText("");


              }

              @Override
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       data = new Details(ds.child("name").getValue(String.class),ds.child("money").getValue(Integer.class));}
//                  Details data = dataSnapshot.getValue(Details.class);
                      details.add(data);
                    detailsAdapter.notifyDataSetChanged();
                      String name = data.getName();
                      int i = data.getMoney();
                      x.add(name);
                      y.add(i);
                      etName.setText("");
                      etAmount.setText("");


              }

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                   data=dataSnapshot.getValue(Details.class);
                      data = new Details(ds.child("name").getValue(String.class), ds.child("money").getValue(Integer.class));}
                  String name=data.getName();
                  int i=data.getMoney();

                  x.remove(name);
                  y.remove(Integer.valueOf(i));


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
