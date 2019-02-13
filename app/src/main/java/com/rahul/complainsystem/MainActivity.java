package com.rahul.complainsystem;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button add, view, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button)findViewById(R.id.Addbutton);
        view = (Button)findViewById(R.id.Viewbutton);
        help = (Button)findViewById(R.id.helpButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                System.out.println("This is Token "+token);
                //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                System.out.println("This is Token "+token);
                Intent i = new Intent();
                i.setClass(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
              //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                System.out.println("This is Token "+token);
                Intent i = new Intent();
                i.setClass(MainActivity.this, helpActivity.class);
                startActivity(i);
            }
        });
    }
}
