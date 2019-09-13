package com.pam.pieter.tugas_besar_7923;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button go,daftar;
    EditText username,password;
    private AndroidOpenDbHelper aodh;
    private ArrayList<User> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new ArrayList<User>();
        aodh = new AndroidOpenDbHelper(this);
        username = (EditText) findViewById(R.id.eduser);
        password = (EditText) findViewById(R.id.edPass);



            go = (Button) findViewById(R.id.btnSearch);
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (username.getText().toString().equals("") || password.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Username & password anda salah !", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(aodh.showUsernameInList().isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "Username & password anda tidak terdaftar !", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent i = new Intent(v.getContext(), MapsActivity.class);
                            startActivity(i);
                        }
                    }
                }
            });

            daftar = (Button) findViewById(R.id.btnDaftar);
            daftar.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), daftar.class);
                    startActivity(i);
                }

            });

        }
}

