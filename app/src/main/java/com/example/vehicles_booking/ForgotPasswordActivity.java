package com.example.vehicles_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText txt_maessage,Favourite_number,Favourite_person;
    private String massage;
    //private String testNumber="01675977369";
    private AlertDialog.Builder alertDialogBuilder;
    String codeStore;
    DBconnection dBconnection;
    String Phone;
    Random code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        dBconnection = new DBconnection(this);


        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //this is the filed view emergency Number  from database
        // data view for profile
        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
           // Emargency_nnumber1.setText(cursor.getString(13));
           // Emargency_nnumber2.setText(cursor.getString(14));
            Phone=(cursor.getString(2)).toString();
        }
        else {

            Toast.makeText(this,"No data is found",Toast.LENGTH_LONG);


        }

    }



//of course chack your XML file , without onClike=btn_sender code not work
    public  void btn_sender(View view){
        int permissionChek=ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
     if(permissionChek== PackageManager.PERMISSION_GRANTED) {

         MyMessage();
     }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},0);

        }
    }



    private void MyMessage() {

        String Mobile=DataHolder.Phone;//from Data Holder Class
        Mobile.toString().trim();

       //Random code generate for forgot password
        code =new Random();
        int max,min,sendCode;
        String Hige,Low;
        Hige="20000";
        Low="10000";
        max=Integer.parseInt(Hige);
        min=Integer.parseInt(Low);
        sendCode=code.nextInt((max-min)+1)+min;
        codeStore="BAF"+sendCode;

        //For SMS
        massage= " Your reset passcode is "+codeStore;

        Favourite_number=findViewById(R.id.FavouriteNumber);

        // This condition for checke Favourite_number,whhen Favourite_number is not null that time this code work
        if(!Favourite_number.getText().toString().equals("")){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(Mobile,null,massage,null,null);
            //for Toast
            Toast.makeText(this,"SMS Send",Toast.LENGTH_SHORT).show();

           updatedata();
            popup();

        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:
                if(grantResults.length >= 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    MyMessage();
                    updatedata();
                }
                else {

                    Toast.makeText(this,"You Don't have permission to send SMS",Toast.LENGTH_SHORT).show();
                }
        }
    }


    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        Favourite_number=findViewById(R.id.FavouritePerson);
        Favourite_person=findViewById(R.id.FavouritePerson);

        if (Favourite_number.equals("")||Favourite_person.equals("")){


            Toast.makeText(this,"Must input Favourite Number and Favourite Person",Toast.LENGTH_SHORT).show();

        }
        else {
            //update qury
            sqLiteDB.execSQL("UPDATE  patient SET password='"+codeStore+"' WHERE pphone_number='"+phonenumber+"'");

        }
    }



    public void popup() {
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(ForgotPasswordActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("Info");

        //for setting the message
        alertDialogBuilder.setMessage("SMS send .");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //For close popup
                dialog.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}


