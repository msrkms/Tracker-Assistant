package com.example.vehicles_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AdminChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chart);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("View Statistics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add pieChart
        PieChart pieChart=findViewById(R.id.pieChart);
        ArrayList<PieEntry> vehicles=new ArrayList<>();
        vehicles.add(new PieEntry(507,"Free"));
        vehicles.add(new PieEntry(707,"Booked"));

        PieDataSet pieDataSet=new PieDataSet(vehicles,"            (Vehicles Availabelity)");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(android.R.color.black);
        pieDataSet.setValueTextSize(16f);
        PieData piedata= new PieData(pieDataSet);
        pieChart.setData(piedata);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();

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
        barChart.animateY(2000);



    }
}
