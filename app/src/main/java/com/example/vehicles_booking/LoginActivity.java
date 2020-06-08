package com.example.vehicles_booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    DBconnection dBconnection;
    private EditText umobile,upassword;
    private Spinner spinner;
    ImageButton SignIn;
    TextView forgot,createNewUser;
    String CurrentAddress,CurrentLocation,CurrentCounty;
    int record;
    FusedLocationProviderClient  fusedLocationProviderClient;
    private AlertDialog.Builder alertDialogBuilder;


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

                intent = new Intent(this, RegisterActivity.class);
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

}

