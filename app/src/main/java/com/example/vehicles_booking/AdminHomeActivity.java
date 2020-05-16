package com.example.vehicles_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {
    DBconnection dBconnection;
    RelativeLayout add,costView,setting,search;
    TextView moreSate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        barchart();

        costView=findViewById(R.id.OptionCostView);
        add=findViewById(R.id.OptionAddVehicles);
        search=findViewById(R.id.OptionSearch);
        setting=findViewById(R.id.OptionSettings);
        moreSate=findViewById(R.id.OptionChart);

        costView.setOnClickListener(this);
        add.setOnClickListener(this);
        search.setOnClickListener(this);
        setting.setOnClickListener(this);
        moreSate.setOnClickListener(this);

    }

    public void onClick(View v){

        Intent intent;

        switch(v.getId()){

            case R.id.OptionCostView:{

                intent=new Intent(this,VehiclesBookingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionAddVehicles:{

                intent=new Intent(this,AddVehiclesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionSearch:{

                intent=new Intent(this,AdminHomeActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.OptionSettings:{

                intent=new Intent(this,AdminHomeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.OptionChart:{

                intent=new Intent(this,AdminChartActivity.class);
                startActivity(intent);
                break;
            }


        }

    }



    public void barchart(){

        //add barChart
        BarChart barChart=findViewById(R.id.barChart);
        ArrayList<BarEntry> visitors=new ArrayList<>();

        // commant for  visitors.add(new BarEntry(DAY,USER));
        visitors.add(new BarEntry(1,20));
        visitors.add(new BarEntry(2,49));
        visitors.add(new BarEntry(3,20));
        visitors.add(new BarEntry(4,4));
        visitors.add(new BarEntry(5,20));
        visitors.add(new BarEntry(6,49));
        visitors.add(new BarEntry(7,20));

        BarDataSet barDataSet=new BarDataSet(visitors,"        Last 7 Day User");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(android.R.color.black);
        barDataSet.setValueTextSize(16f);

        BarData barData= new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Last 7 Day User");
        barChart.animateY(2500);



    }
}
