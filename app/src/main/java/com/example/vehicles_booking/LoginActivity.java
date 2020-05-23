package com.example.vehicles_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    DBconnection dBconnection;
    private EditText umobile,upassword;
    private Spinner spinner;
    ImageButton SignIn;
    TextView forgot,createNewUser;
    String CurrentAddress,CurrentLocation,CurrentCounty;
    int record;
    FusedLocationProviderClient  fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dBconnection=new DBconnection(this);


        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        umobile=findViewById(R.id.inputMobile2);
        upassword=findViewById(R.id.inputPassword2);
        SignIn=findViewById(R.id.buttonSignIn);

        forgot=findViewById(R.id.textForgetpassword);
        createNewUser=findViewById(R.id.textNewAccount);
        forgot.setOnClickListener(this);
        createNewUser.setOnClickListener(this);
        SignIn.setOnClickListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

      //  CurrentLocation();
        Dropdownlist();
    }








    public void Dropdownlist(){
        String[] VehiclesList={"Normal","Admin"};
        spinner = findViewById(R.id.spinnerUserType);

      //  for view data in spinner
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,VehiclesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

      // this method for GET data from spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //use for Postion value
                switch (position){
                    case 0:{
                        record=0;
                        break;
                    }
                    case 1:{
                        record=1;
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }





    public void onClick(View v) {
        String usermobile =umobile.getText().toString();
        String userpassword =upassword.getText().toString();
        int data=0;
        Intent intent;

        switch (v.getId()) {

            case R.id.textForgetpassword: {

                intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textNewAccount: {

                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonSignIn: {
                 //this condision for cheak Normal User Or Admin
                if(data==record){

                    //data Pass for cheake(USER)
                    Boolean result = dBconnection.findPassword(usermobile,userpassword);

                    // this condition for cheak USER NAME  and PASSWOED
                    if(result==true){

                        DataHolder.Phone=usermobile;
                      //  updatedata();//updata data
                         intent=new Intent(this,HomeActivity.class);
                          startActivity(intent);

                    } else{

                        Toast.makeText(this,"User Name OR Password Wrong",Toast.LENGTH_SHORT).show();
                    }
                }

                //for admin
                else {
                    //for admin
                    Boolean result2 = dBconnection.findPassword2(usermobile,userpassword);
                    // this condition for cheak USER NAME  and PASSWOED
                    if(result2==true){
                        DataHolder.Phone=usermobile;
                        intent=new Intent(this,AdminHomeActivity.class);
                        startActivity(intent);
                    } else{

                        Toast.makeText(this,"User Name OR Password Wrong",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            }


        }
    }
/*
    public void updatedata(){
        // String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        umobile=findViewById(R.id.inputMobile2);
        String us=umobile.toString();
        String CA=CurrentAddress.toString();
        String CL=CurrentLocation.toString();
        String CC=CurrentCounty.toString();

        if (umobile.equals("")){


            Toast.makeText(this,"Must input Favourite Number and Favourite Person",Toast.LENGTH_SHORT).show();

        }
        else {
            //update qury
            sqLiteDB.execSQL("UPDATE  patient SET pbmi='"+ CA +"',pbmr='"+ CL +"',pbp='"+ CC +"' WHERE pphone_number='"+us+"'");

        }
    }

 */


   //for location track
/*
    public void CurrentLocation(){

        //Check Permission
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            //when permission granted
            getLocation();
        }
        else{

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

        }


    }

     */

  //  get Location from GOOGLE GPS
    private void getLocation(){

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location =task.getResult();
                if (location!=null){

                    //Initialize geoCoder
                    Geocoder geocoder= new Geocoder(LoginActivity.this, Locale.getDefault());
                    //Initialize  address list
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //Set data
                      /*CurrentCounty=addresses.get(0).getCountryName().toString();
                        CurrentLocation=addresses.get(0).getLocality().toString();
                       CurrentAddress=addresses.get(0).getAddressLine(0).toString();

                       */

                        // DataHolder.CurrentCounty=country.toString();
                        // DataHolder.CurrentLocation=addresses.get(0).getLocality().toString();
                        //DataHolder.CurrentAddress=addresses.get(0).getAddressLine(0).toString();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }






}
