package com.example.vehicles_booking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener {

 RelativeLayout Booking,Settings,History,Member_info;
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
            case R.id.optionWireless:{

                intent=new Intent(this,RegistrationActivity.class);
                startActivity(intent);
                break;
            }



        }

    }

}

