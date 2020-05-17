package com.example.vehicles_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    TextView forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        forgot=findViewById(R.id.textForgetpassword);
        forgot.setOnClickListener(this);

        Dropdownlist();
    }

    public void Dropdownlist(){
        String[] VehiclesList={"Normal","Admin"};
        spinner = findViewById(R.id.spinnerUserType);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,VehiclesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.textForgetpassword: {

                intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionMemberInfo: {

                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            }


        }
    }
    }
