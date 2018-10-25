package com.avi.abhishek.presentation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailsAdapter extends BaseAdapter {

    public DetailsAdapter(ArrayList<Details> details) {
        this.details = details;
    }

    ArrayList<Details> details;


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

        Details currentDetail = getItem(position);

        TextView etNametrv,etAmounttrv;
        etNametrv=inflatedView.findViewById(R.id.etNametrv);
        etAmounttrv=inflatedView.findViewById(R.id.etAmounttrv);
        Button btnDelete = inflatedView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.remove(position);
                notifyDataSetChanged();
            }
        });

        etNametrv.setText(currentDetail.getName());

        etAmounttrv.setText(String.valueOf(currentDetail.getMoney()));

        return inflatedView;
    }


}
