package com.example.vehicles_booking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class VehiclesBookingActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView car,bike,wireless;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_booking);


        car=findViewById(R.id.optionCar);
        bike=findViewById(R.id.optionBike);
        wireless=findViewById(R.id.optionWireless);

        car.setOnClickListener(this);
        bike.setOnClickListener(this);
        wireless.setOnClickListener(this);

    }


    public void onClick(View v){

        Intent intent;

        switch(v.getId()){

            case R.id.optionCar:{

                intent=new Intent(this,CarBookingEnableActivity.class);
                startActivity(intent);
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

