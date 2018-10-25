package com.avi.abhishek.presentation;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button btnBillSplit,btnsignout,btnLocation;
    FirebaseUser firebaseUser;
    private static final int RC_SIGN_IN=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnBillSplit = findViewById(R.id.btnBillSplit);

        btnsignout=findViewById(R.id.btnsignout);

        btnLocation=findViewById(R.id.btnLocation);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            if(firebaseUser.getDisplayName()!= null)
            Toast.makeText(MainActivity.this,""+firebaseUser.getDisplayName()+"  signed In!",Toast.LENGTH_SHORT).show();
             else
                Toast.makeText(MainActivity.this,""+firebaseUser.getPhoneNumber()+"  signed In!",Toast.LENGTH_SHORT).show();
            Log.e("Tag",""+firebaseUser.getDisplayName());
            addListeners();
            //user logged in
        } else {
            //user logged out
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    new AuthUI.IdpConfig.AnonymousBuilder().build()))
                            .build(),
                    RC_SIGN_IN);



        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                addListeners();

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button

                    finish();

                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    return;
                }
            }
        }
    }

    public void addListeners(){

        btnBillSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(MainActivity.this,First_Slide.class);
                startActivity(it);
            }
        });


        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnsignout) {
                    AuthUI.getInstance()
                            .signOut(MainActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this,"User signed out",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(MainActivity.this,Location_View.class);
                startActivity(it);
            }
        });

    }



}
