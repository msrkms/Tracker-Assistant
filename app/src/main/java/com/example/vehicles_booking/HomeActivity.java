package com.example.vehicles_booking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener {
    DBconnection dBconnection;
    RelativeLayout Booking,Settings,History,Member_info;
    TextView Uname,Uphone,UID;
    Random code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        UID=findViewById(R.id.ViewUId);

        viewdata();

        code =new Random();
        int max,min,sendCode;
        String codeStore;
        String Hige,Low;
        Hige="20000";
        Low="10000";
        max=Integer.parseInt(Hige);

        min=Integer.parseInt(Low);
        sendCode=code.nextInt((max-min)+1)+min;
        codeStore="BAF"+sendCode;
        UID.setText(codeStore);

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

                intent=new Intent(this,UserProfileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionHistory:{

             //   intent=new Intent(this,AdminHomeActivity.class);
               // startActivity(intent);
                break;
            }

            case R.id.OptionMemberInfo:{

             //   intent=new Intent(this,LoginActivity.class);
            //    startActivity(intent);
                break;
            }


        }

    }

}

