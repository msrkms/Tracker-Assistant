package com.example.vehicles_booking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VehiclesBookingActivity extends AppCompatActivity implements View.OnClickListener {
    DBconnection dBconnection;
    private CardView car,bike,wireless;

    TextView name,phone;
    String VA;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_booking);


        car=findViewById(R.id.optionCar);
        bike=findViewById(R.id.optionBike);
        wireless=findViewById(R.id.optionWireless);

        car.setOnClickListener(this);
        bike.setOnClickListener(this);
        wireless.setOnClickListener(this);

        name=findViewById(R.id.ViewpName);
        phone=findViewById(R.id.ViewId);

        viewdata();
    }


    public void viewdata(){
        dBconnection = new DBconnection(this);

        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            name.setText(cursor.getString(1));
            phone.setText(cursor.getString(2));
            // UID.setText(cursor.getString(0));
            VA=cursor.getString(8);


        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }




    public void onClick(View v){
       int va2=Integer.parseInt(VA);
       int va3=0;
        Intent intent;

        switch(v.getId()){

            case R.id.optionCar:{

                if(va3==va2){

                    intent=new Intent(this,CarBookingEnableActivity.class);
                    startActivity(intent);
                }
                else {
                    intent=new Intent(this,CarBookingDisableActivity.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.optionBike:{

                intent=new Intent(this,CarBookingEnableActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.optionWireless:{

                intent=new Intent(this,CarBookingEnableActivity.class);
                startActivity(intent);
                break;
            }



        }

    }







}

