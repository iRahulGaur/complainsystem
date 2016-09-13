package com.rahul.complainsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ViewActivity extends AppCompatActivity {
    Button add, view, review;
    String newResolve;
    EditText cno;
    String resolve2;
    ListView lv;
    ArrayList<complain> al;
    CustomAdapter adp;
    String record = "";
    String num;
    String imei;
    String resolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        view = (Button)findViewById(R.id.button);
        add = (Button)findViewById(R.id.button2);
        review = (Button)findViewById(R.id.resolveButton);
        cno = (EditText)findViewById(R.id.editTextCno);
        lv = (ListView)findViewById(R.id.listView);

        //newResolve = al.get(7).toString();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ViewActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newResolve.contains(" ")){
                    Intent i = new Intent();
                    i.setClass(ViewActivity.this, ReviewActivity.class);
                    i.putExtra("1", num);
                    i.putExtra("2",resolve);
                    startActivity(i);
            }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                num = cno.getText().toString();
                System.out.println("This is newResolve "+newResolve+" and this is resolve2 "+resolve2);
                if(num.equals("")){
                    Toast.makeText(ViewActivity.this,"Please write customer number",
                            Toast.LENGTH_LONG).show();
                }else{
                    al = new ArrayList<complain>();
                    new MyAsynk1().execute();
                    System.out.println("This is newResolve " + newResolve + " and this is resolve2 " + resolve2);
                }
            }
        });
    }
    class MyAsynk1 extends AsyncTask<Void,Void,Void>{
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ViewActivity.this);
            pd.setMessage("Getting Data, Please wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            adp = new CustomAdapter(ViewActivity.this,R.layout.custom, al);
            //resolve = String.valueOf(adp.getItem(8));
          //  System.out.println("This is resolve date "+resolve);
            lv.setAdapter(adp);
        }

        @Override
        protected Void doInBackground(Void... params) {
            getvalues();
            return null;
        }
    }
    void getvalues(){
        String record1="";
        InputStream is = null;
        //http://localhost/mydata/customer.php?cno=1&imei=1234567890
        try {
            URLConnection conn = new URL(getResources().getString(R.string.url) + "customer.php?cno="+ URLEncoder.encode(num,"utf-8")+"&imei="+imei)
                    .openConnection();
            is = conn.getInputStream();
        } catch (Exception e) {
            Log.i("Error ", e.toString());
        }
        try {
            InputStreamReader isr = new InputStreamReader(is, "iso-8859-1");
            BufferedReader reader = new BufferedReader(isr, 8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            record1 = sb.toString();
            System.out.println(sb);

        } catch (Exception e) {
            Log.d("Error ", "Error converting result " + e.toString());
            record = record + "Error converting data " + e.toString();
        }
        try {
            JSONArray jarr = new JSONArray(record1);
            for (int i = 0; i < jarr.length(); i++) {
                JSONObject jobj = jarr.getJSONObject(i);
                complain c = new complain(
                        jobj.getString("cno"),
                        jobj.getString("name"),
                        jobj.getString("block"),
                        jobj.getString("house"),
                        jobj.getString("type"),
                        jobj.getString("detail"),
                        jobj.getString("date"),
                        jobj.getString("resolve"),
                        jobj.getString("phone"),
                        jobj.getString("imei"));
                complainReview c2 = new complainReview(
                        jobj.getString("resolve")
                );
                newResolve = c2.toString();
                resolve2 = c2.resolve.toString();
                al.add(c);
                System.out.println("size of al "+al.size());
            }


        } catch (JSONException e) {
            Log.d("Error ", "Error parsing data " + e.toString());
            record = record + "Error parsing data " + e.toString();

        }
    }
}