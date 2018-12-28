package com.avi.abhishek.Billie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class First_Slide extends AppCompatActivity {

    EditText name;
    ListView lvGroupname;

    String username;
    Button btnDone;
    ArrayList<String> arrayname=new ArrayList<>();

    DatabaseReference dbref=FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_slide);
        username=firebaseUser.getUid();

        name = findViewById(R.id.etTripName);
        btnDone=findViewById(R.id.btnDone);
        lvGroupname=(ListView)findViewById(R.id.lvGroupname);

        final GroupNameAdapter groupNameAdapter=new GroupNameAdapter(arrayname);
        lvGroupname.setAdapter(groupNameAdapter);
        groupNameAdapter.notifyDataSetChanged();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=name.getText().toString();
                dbref.child(username+" Group_Names").child(name.getText().toString()).setValue(name.getText().toString());
                name.setText("");
                groupNameAdapter.notifyDataSetChanged();
                }
        });

        dbref.child(username+" Group_Names").child(name.getText().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.getValue(String.class);
                arrayname.add(data);
                groupNameAdapter.notifyDataSetChanged();
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



