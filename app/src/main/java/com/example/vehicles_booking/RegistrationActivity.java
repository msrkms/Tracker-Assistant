package com.example.vehicles_booking;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Calendar;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText userName, userPhoneNumber, userRank,userDesignation, userAddress, userBloodGroup, userPassword;
    private Button registration,dateOfBirth;
    UserRegistrationDetails userRegistrationDetails;


    DatePickerDialog.OnDateSetListener dateSetListener2;
    private AlertDialog.Builder alertDialogBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName = findViewById(R.id.inputName2);
        userDesignation=findViewById(R.id.inputDesignation2);
        userBloodGroup = findViewById(R.id.inputBloodGroup2);
        userAddress = findViewById(R.id.inputAddress2);
        userPassword = findViewById(R.id.inputPassword2);
        userRank=findViewById(R.id.inputRank2);
        registration = findViewById(R.id.buttonSignUp);
        dateOfBirth=findViewById(R.id.buttonDateOfBrith);


        userRegistrationDetails = new UserRegistrationDetails();
        registration.setOnClickListener(this);


        dateOfBirth();

    }



    public void dateOfBirth(){

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        RegistrationActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener2,year,month,day);

          //      datePickerDialog.getWindow();
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        dateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {

                Month = Month+1;
                String m= Integer.toString(Month);
                String d= Integer.toString(Day);

                String date= Year+"-"+ Month +"-"+Day;


                if (m.length()<2 && d.length()<2){
                    String Bdate= Year+"-0"+ Month +"-0"+Day;
                    dateOfBirth.setText(Bdate);
                }
                 else if(m.length()<2 && d.length()==2){
                    String B1date= Year+"-0"+ Month +"-"+Day;
                    dateOfBirth.setText(B1date);

                }
                else if(m.length()==2 && d.length()<2){
                    String B2date= Year+"-"+ Month +"-0"+Day;
                    dateOfBirth.setText(B2date);

                }
                else {
                    dateOfBirth.setText(date);
                }


            }
        };
    }




    public void onClick(View view) {

        Intent intent;
        String Name= userName.getText().toString().trim();
        String DateOfBirth= dateOfBirth.getText().toString();
        String Address= userAddress.getText().toString();
        String BloodGroup= userBloodGroup.getText().toString();
        String Password= userPassword.getText().toString();
        String Rank= userRank.getText().toString();
        String Designation= userDesignation.getText().toString();
        if (Name.isEmpty() ||DateOfBirth.isEmpty() ||  Address.isEmpty() || BloodGroup.isEmpty() || Password.isEmpty()  || Rank.isEmpty() || Designation.isEmpty()  ) {

            Toast.makeText(getApplicationContext(), "NO DATA INSERT , MAYBE SOME  REQUIRED FILLED IS NOT FILLUP", Toast.LENGTH_SHORT).show();
            //Name.setError("Must Input");
            //Name.requestFocus();
            //return;
            userPassword.setText("");




        } else {

            ApiDataSend();

           //popup window
            popup();

            // for clear input fill
            userPassword.setText("");
            userDesignation.setText("");
            userRank.setText("");
            userAddress.setText("");
            userBloodGroup.setText("");
            userName.setText("");
            dateOfBirth.setText("");

            // intent = new Intent(this, RegistrationActivity.class);
            //   startActivity(intent);
        }
    }



    //this function for API data send
    private void ApiDataSend() {

        String Phone= DataHolder.Phone;

        String MobileNumber= Phone.toString();

        //converting all value to string
        String Name= userName.getText().toString();
        String DateOfBirth= dateOfBirth.getText().toString();
        String Address= userAddress.getText().toString();
        String BloodGroup= userBloodGroup.getText().toString();
        String Password= userPassword.getText().toString();
        String Rank= userRank.getText().toString();
        String Designation= userDesignation.getText().toString();

        //String dateOfBrith=ageFromdate.toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://trackerassistant.herokuapp.com/api/User/Registration";



        final JSONObject prams = new JSONObject();
        try {
            prams.put("name",Name);
            prams.put("phoneNumber",MobileNumber);
            prams.put("address",Address);
            prams.put("dob",DateOfBirth);
            prams.put("rank",Rank);
            prams.put("designation",Designation);
            prams.put("bloodGroup",BloodGroup);
            prams.put("password",Password);


        } catch (Exception e) {

        }

        //method
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, prams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Done"+response);
                }catch (Exception ex){
                    System.out.println("Data not send"+ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println( "ERROR"+error+prams.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }





    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("Info");

        //for setting the message
        String Name= userName.getText().toString();
        alertDialogBuilder.setMessage("Hi "+Name+",Your SignUP request has been sent to ADMIN. Please Wait for confirmation OR contact to ADMIN. Thanks ");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}


