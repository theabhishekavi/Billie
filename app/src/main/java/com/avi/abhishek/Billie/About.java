package com.avi.abhishek.Billie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ExpandableTextView textView = (ExpandableTextView)findViewById(R.id.expand_text_view);
        String myText="Billie is a handy application which can split the bills quickly and accurately. We can create multiple groups and each group has its own independent entries. As all  data are stored on server we can access the bill info from any device just by a secure log in process." +
                "\n" +
                "Useful to store the bill info and calculate whenever we want!";
        textView.setText(myText);

    }
}
