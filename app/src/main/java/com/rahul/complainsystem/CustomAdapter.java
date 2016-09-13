package com.rahul.complainsystem;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LENOVO on 07-05-2016.
 */
public class CustomAdapter extends ArrayAdapter<complain>{
    Context c;
    int layout;
    ArrayList<complain> all;
    public CustomAdapter(Context mainActivity, int custom,
                         ArrayList<complain> al) {
        super(mainActivity,custom, al);
        c  =  mainActivity;
        all = al;
        layout = custom;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lif = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lif.inflate(layout, null);
        TextView tv1= (TextView)convertView.findViewById(R.id.textView1);
        TextView tv2= (TextView)convertView.findViewById(R.id.textView2);
        TextView tv3= (TextView)convertView.findViewById(R.id.textView3);
        TextView tv4= (TextView)convertView.findViewById(R.id.textView4);
        TextView tv5= (TextView)convertView.findViewById(R.id.textView5);
        TextView tv6= (TextView)convertView.findViewById(R.id.textView6);
        TextView tv7= (TextView)convertView.findViewById(R.id.textView7);
        TextView tv8= (TextView)convertView.findViewById(R.id.textView8);
        TextView tv9= (TextView)convertView.findViewById(R.id.textView9);
        tv1.setText("Complain No: "+all.get(position).cno);
        tv2.setText("Name: "+all.get(position).name);
        tv3.setText("Block No: "+all.get(position).block);
        tv4.setText("House No:  "+all.get(position).house);
        tv5.setText("Type of Complain:  "+all.get(position).type);
        tv6.setText("Detail of Complain:  "+all.get(position).detail);
        tv7.setText("Date of Complain:  "+all.get(position).date);
        tv8.setText("Resolve on:  "+all.get(position).resolve);
        tv9.setText("Phone No.:  "+all.get(position).phone);

        return convertView;
    }
}
