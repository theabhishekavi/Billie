package com.avi.abhishek.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class Third_Slide extends AppCompatActivity {

    String s="",concat="";
    ArrayList<String> name;
    ArrayList<Integer> money;
    int size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_slide);
        if (getIntent() != null) {

            name = getIntent().getStringArrayListExtra("key");
            money = getIntent().getIntegerArrayListExtra("int");
            size = money.size();
        }

        ExpandableTextView textView = (ExpandableTextView)findViewById(R.id.expand_text_view);


        //    double[] arr=new double[5];

        double sum = 0.0, equal = 0.0;
        int i = 0, j = 0;
        //  String s0=t1.getText().toString();
        //  arr[0]=Double.parseDouble(s0);
        //  String s1=t2.getText().toString();
        //  arr[1]=Double.parseDouble(s1);
        //  String s2=t1.getText().toString();
        //  arr[2]=Double.parseDouble(s2);
//        String s3=t1.getText().toString();
//        arr[3]=Double.parseDouble(s3);
//        String s4=t1.getText().toString();
//        arr[4]=Double.parseDouble(s4);

        for (int k = 0; k < size; k++) {
//        sum=arr[0]+arr[1]+arr[2]+arr[3]+arr[4];

            sum = sum + money.get(k);
        }
        equal = sum / size;

        concat="Per Person has to pay Rs:-"+String.valueOf(equal)+"\n \n";
        double[] rec = new double[size];
        double[] give = new double[size];
        for (i = 0; i < size; i++) {
            if (money.get(i) > equal) {
                rec[i] = money.get(i) - equal;
                give[i] = 0.0;
            } else {
                give[i] = equal - money.get(i);
                rec[i] = 0.0;
            }
        }
        for (i = 0; i < size; i++) {
            if (rec[i] == 0.0) {
                continue;
            }
            for (j = 0; j < size; j++) {
                if (give[j] == 0.0 || rec[i] == 0.0) {
                    continue;
                }
                if (give[j] < rec[i]) {
                    s=("\n"+name.get(i) + " will receive Rs." + give[j] + " from " + name.get(j)+"\n");
                    concat= String.format("%s%s   ", concat, s);

                   Log.e("Tag", name.get(i) + " will receive Rs." + give[j] + " from " + name.get(j)+"\n");

                    rec[i] = rec[i] - give[j];
                    give[j] = 0.0;
                    continue;
                }
                if (rec[i] < give[j]) {
                    s=("\n"+name.get(i) + " will receive Rs. " + rec[i] + " from " + name.get(j)+"\n");
                    concat= String.format("%s%s   ", concat, s);
                    Log.e("Tag1", name.get(i) + " will receive Rs. " + rec[i] + " from " + name.get(j));
                    give[j] = give[j] - rec[i];
                    rec[i] = 0.0;
                    break;
                }
                if (give[j] == rec[i]) {
                    s=("\n"+name.get(i) + " will receive Rs." + rec[i] + " from " + name.get(j)+"\n");
                    concat= String.format("%s%s   ", concat, s);
                   Log.e("Tag2", name.get(i) + "will receive Rs." + rec[i] + " from " + name.get(j));
                    rec[i] = 0.0;
                    give[j] = 0.0;
                    break;
                }
            }
        }
        textView.setText(concat);

    }
}

