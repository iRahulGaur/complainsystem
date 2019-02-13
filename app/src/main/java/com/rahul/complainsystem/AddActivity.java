package com.rahul.complainsystem;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    EditText nametf, blocktf, housetf, detailtf, phonetf;
    TextView cnotf;
    Button clear, add;
    ProgressDialog pd;
    Spinner typeSp;
    ArrayList<complain> complainnum;
    String cno, name, block, house, type, detail, phone, imei;
    String result = "";
    String resolve = "Not Resolved";
    String date;
    String ar[] = {"water", "furniture", "security", "electricity", "other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        typeSp = new Spinner(AddActivity.this);

        nametf = (EditText) findViewById(R.id.nameET);
        blocktf = (EditText) findViewById(R.id.blockET);
        housetf = (EditText) findViewById(R.id.houseET);
        detailtf = (EditText) findViewById(R.id.detailET);
        phonetf = (EditText) findViewById(R.id.mobileET);
        cnotf = (TextView) findViewById(R.id.cnoView);

        clear = (Button) findViewById(R.id.clearButton);
        add = (Button) findViewById(R.id.addComplain);

        typeSp = (Spinner) findViewById(R.id.spinnerType);

        add.setEnabled(true);
        nametf.setText(null);
        cnotf.setText(null);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        date = formatter.format(now) + "";

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nametf.setText(null);
                blocktf.setText(null);
                housetf.setText(null);
                detailtf.setText(null);
                phonetf.setText(null);
                add.setEnabled(true);
                cnotf.setText(null);
            }
        });
        ArrayAdapter<String> adp = new ArrayAdapter<String>(AddActivity.this
                , android.R.layout.simple_list_item_1, ar);
        typeSp.setAdapter(adp);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                String s = (String) parent.getItemAtPosition(pos);
                type = s;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeSp.setSelection(4);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                name = nametf.getText().toString();
                block = blocktf.getText().toString();
                house = housetf.getText().toString();
                phone = phonetf.getText().toString();
                detail = detailtf.getText().toString();
                Calendar c = Calendar.getInstance();

                int d = c.get(Calendar.DAY_OF_MONTH);
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);
                int s = c.get(Calendar.SECOND);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                System.out.println(" d " + d + " h " + h + " ");
                String[] nameArr = name.split(" ");
                //date = d+"/"+month+"/"+year;
                System.out.println(date + " date ");
                cno = "" + nameArr[0] + d + m + s;
                System.out.println(" cno " + cno);
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    imei = telephonyManager.getDeviceId();
                    System.out.println(" imei "+imei);
                    return;
                }
                System.out.println("Name : "+name+" Block : "+block+" House : "+house+" Phone : "+phone+" Detaill : "+detail);

                complainnum = new ArrayList<complain>();

                if(name.equals("")){
                    System.out.println("empty field 1");
                    Toast.makeText(AddActivity.this, "Please fill Name field", Toast.LENGTH_LONG).show();
                }
                    else if(house.equals("")){
                    System.out.println("empty field 3");
                    Toast.makeText(AddActivity.this, "Please fill House field", Toast.LENGTH_LONG).show();
                }
                else if (block.equals("")){
                    System.out.println("empty field 2");
                    Toast.makeText(AddActivity.this, "Please fill Block field", Toast.LENGTH_LONG).show();
                }
                else if (phone.equals("")) {
                    System.out.println("empty field phone");
                    Toast.makeText(AddActivity.this, "Please fill Phone field", Toast.LENGTH_LONG).show();
                }
                else if (detail.equals("")) {
                    System.out.println("empty field detail");
                    Toast.makeText(AddActivity.this, "Please fill Detail field", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddActivity.this, "staring.....", Toast.LENGTH_SHORT).show();
                    System.out.println("no empty field");
                    new MyAsynk().execute();
                    add.setEnabled(false);
                }
            }
        });
    }

    class MyAsynk extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AddActivity.this);
            pd.setMessage("Please Wait.. Complain is Registering..");
            System.out.println("complain registering ");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (result.contains("Error in submission")) {
                Toast.makeText(AddActivity.this, result, Toast.LENGTH_SHORT).show();
                System.out.println("Error in submission");
            } else {
                Toast.makeText(AddActivity.this, "Complain Added.. ", Toast.LENGTH_SHORT).show();
                System.out.println("complain added");
                saveFile();
                cnotf.setText("Your Complain Number is " + cno);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAddress();
            return null;
        }
    }
    void saveFile(){
        try{
            File complainFolder = new File("/sdcard/complain/");
            if(!complainFolder.exists()){
                complainFolder.mkdirs();
                File myFile = new File("/sdcard/complain/Complain_Number.txt");
                if(!myFile.exists()) {
                    myFile.createNewFile();
                    FileWriter writer = new FileWriter(myFile,true);
                    writer.append(cno.toString() + " on " + date + " this complain was created"+"\n\n");
                    writer.flush();
                    writer.close();
                    System.out.println("Folder and file created 1");
                }else{
                    FileWriter writer = new FileWriter(myFile,true);
                    writer.append(" "+cno.toString() + " This complain was created on " + date +"\n\n");
                    writer.flush();
                    writer.close();
                    System.out.println("Folder and file created 2");
                }
            }
            else {
                File myFile = new File("/sdcard/complain/Complain_Number.txt");
                if (!myFile.exists()) {
                    myFile.createNewFile();
                    FileWriter writer = new FileWriter(myFile, true);
                    writer.append(cno.toString() + " on " + date + " this complain was created" + "\n\n");
                    writer.flush();
                    writer.close();
                    System.out.println("File created 1");
                }else{
                    FileWriter writer = new FileWriter(myFile,true);
                    writer.append(cno.toString() + " on " + date + " this complain was created"+"\n\n");
                    writer.flush();
                    writer.close();
                    System.out.println("file created 2");
                }
            }
        } catch(IOException e) {
            Log.e("Error: 4", "Error in writing file" + e.toString());
        }
    }

    void getAddress() {
        InputStream is = null;
        try {
            URLConnection conn = new URL(getResources().getString(R.string.url)+
                    "new.php?cno=" +URLEncoder.encode(cno,"utf-8")+
                    "&name=" +URLEncoder.encode(name,"utf-8")+
                    "&block=" +URLEncoder.encode(block,"utf-8")+
                    "&house=" +URLEncoder.encode(house,"utf-8")+
                    "&type=" +URLEncoder.encode(type,"utf-8")+
                    "&detail=" +URLEncoder.encode(detail,"utf-8")+
                    "&date=" +URLEncoder.encode(String.valueOf(date),"utf-8")+
                    "&resolve=" +URLEncoder.encode(resolve,"utf-8")+
                    "&phone=" +URLEncoder.encode(phone,"utf-8")+
                    "&imei="+URLEncoder.encode(imei,"utf-8")).openConnection();
            is = conn.getInputStream();

        } catch (MalformedURLException e2) {
            Log.e("Error: ","Error 2"+e2.toString());
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