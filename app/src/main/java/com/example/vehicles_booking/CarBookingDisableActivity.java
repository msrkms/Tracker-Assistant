package com.example.vehicles_booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CarBookingDisableActivity extends AppCompatActivity implements View.OnClickListener {


    DBconnection dBconnection;
    private AlertDialog.Builder alertDialogBuilder;
    EditText EndMileage;
     TextView name,id;
     ImageButton BookingEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking_disable);
        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Vehicle Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.ViewName);
        id=findViewById(R.id.ViewId);
        BookingEnd=findViewById(R.id.buttonBookingEnd);
        BookingEnd.setOnClickListener(this);

        viewdata();
        popup();
    }


    public void onClick(View v){

        Intent intent;

        switch(v.getId()) {

            case R.id.buttonBookingEnd: {

                updatedata();
                intent = new Intent(this, CarBookingEnableActivity.class);
                startActivity(intent);
                break;
            }

        }

    }



    public void viewdata(){
        dBconnection = new DBconnection(this);

        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            name.setText(cursor.getString(1));
            id.setText(cursor.getString(2));
            // UID.setText(cursor.getString(0));



        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }

    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        String Enable="0";
        EndMileage=findViewById(R.id.InputEndMileage);


        if (EndMileage.equals("")){


            Toast.makeText(this,"Must input End mileage",Toast.LENGTH_SHORT).show();

        }
        else {
            //update qury
            sqLiteDB.execSQL("UPDATE  patient SET pheight='"+Enable+"' WHERE pphone_number='"+phonenumber+"'");

        }
    }





    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(CarBookingDisableActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("Info");

        //for setting the message
        String pName= name.getText().toString();
        alertDialogBuilder.setMessage("Hi "+pName+",You Already booked one.So first return this one. Thanks  ");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit from popup
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}
