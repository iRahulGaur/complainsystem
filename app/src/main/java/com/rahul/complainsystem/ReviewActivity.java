package com.rahul.complainsystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {
    ImageView iv;
    EditText et;
    Button btn;
    String cap;
    ProgressDialog pd;
    ArrayList<complain> complainnum;
    String result = "";
    String cno = "";
    String resolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        iv = (ImageView)findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.captcha);

        btn = (Button)findViewById(R.id.button4);
        et = (EditText)findViewById(R.id.editText);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        resolve = formatter.format(now)+"";

        Bundle extras = getIntent().getExtras();
        cno = extras.getString("1");
        System.out.println("This is cno "+cno);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cap = et.getText().toString();
                System.out.println("this is resolve date "+resolve);
                if(cap.contains("RBSKW")){
                    new MyAsynk2().execute();
                }else if(cap.contains("rbskw")){
                    new MyAsynk2().execute();
                }else if(cap.contains("Rbskw")){
                    new MyAsynk2().execute();
                }else{
                    Toast.makeText(ReviewActivity.this, "Image and the code didn't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    class MyAsynk2 extends AsyncTask<Void, Void, Void>{

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ReviewActivity.this);
            pd.setMessage("Please Wait.. Complain is Resolving..");
            System.out.println("complain registering ");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (result.contains("Error in submission")) {
                Toast.makeText(ReviewActivity.this, result, Toast.LENGTH_SHORT).show();
                System.out.println("Error in submission");
            } else {
                Toast.makeText(ReviewActivity.this, "Complain Resolved ", Toast.LENGTH_SHORT).show();
                System.out.println("complain added");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAddress();
            return null;
        }
    }
    void getAddress() {
        InputStream is = null;
        try {
            URLConnection conn = new URL(getResources().getString(R.string.url)+
                    "resolve.php?cno=" + URLEncoder.encode(cno, "utf-8")+
                    "&resolve=" +URLEncoder.encode(resolve,"utf-8")).openConnection();
            is = conn.getInputStream();


        } catch (MalformedURLException e2) {
            Log.e("Error: ", "Error 2" + e2.toString());
        } catch (Exception e) {
            Log.i("Error 1 ", "Error 1 " + e.toString());
        }
        try{
            InputStreamReader isr = new InputStreamReader(is, "iso-8859-1");
            BufferedReader reader = new BufferedReader(isr,8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();
            System.out.println("This is result "+sb);
        }catch (Exception e){
            Log.e("Log_tag", "Error converting result " + e.toString());
        }
    }
}