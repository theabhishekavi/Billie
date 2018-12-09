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
        String myText="An app to store bill related information that we share among our friends or collegues";
        textView.setText(myText);

    }
}
