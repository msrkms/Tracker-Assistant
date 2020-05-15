package com.example.vehicles_booking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener {
    DBconnection dBconnection;
 RelativeLayout Booking,Settings,History,Member_info;
TextView Uname,Uphone,UID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Booking=findViewById(R.id.OptionBooking);
        Settings=findViewById(R.id.OptionSettings);
        History=findViewById(R.id.OptionHistory);
        Member_info=findViewById(R.id.OptionMemberInfo);

        Booking.setOnClickListener(this);
        Settings.setOnClickListener(this);
        History.setOnClickListener(this);
        Member_info.setOnClickListener(this);

        Uname=findViewById(R.id.ViewName);
        Uphone=findViewById(R.id.ViewPhone);
        UID=findViewById(R.id.ViewId);

        viewdata();

    }


    public void viewdata(){
        dBconnection = new DBconnection(this);

        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            Uname.setText(cursor.getString(1));
            Uphone.setText(cursor.getString(2));
           // UID.setText(cursor.getString(0));


        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }

    public void onClick(View v){

        Intent intent;

        switch(v.getId()){

            case R.id.OptionBooking:{

                intent=new Intent(this,VehiclesBookingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionSettings:{

                intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionHistory:{

                intent=new Intent(this,RegistrationActivity.class);
                startActivity(intent);
                break;
            }



        }

    }

}

