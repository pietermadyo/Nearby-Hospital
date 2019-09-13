package com.pam.pieter.tugas_besar_7923;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class daftar extends AppCompatActivity
{
    private EditText Username;
    private EditText Password;
    private Button Daftar;
    private AndroidOpenDbHelper aodh;
    private ArrayList<User> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        data = new ArrayList<User>();
        aodh=new AndroidOpenDbHelper(this);

        Username=(EditText)findViewById(R.id.eduser);
        Password=(EditText)findViewById(R.id.edPass);

        Daftar=(Button)findViewById(R.id.btnDaftar);
        setClickSaveButton();
    }

    private void setClickSaveButton(){
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = Username.getText().toString();
                String p = Password.getText().toString();

                User user=new User();
                user.setUsername(u);
                user.setPassword(p);

                data.add(user);
                aodh.insert(user);
                finish();
                Toast.makeText(getApplicationContext(),"Data Terinput",Toast.LENGTH_LONG).show();
                Intent i=new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
