package com.example.vehicles_booking;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VehiclesListActivity extends AppCompatActivity {

    DBconnection dBconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_list);
        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Vehicle Cost List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataViewForList();
    }


    public void DataViewForList(){
        dBconnection = new DBconnection(this);
        final String TABLE_NAME="patient";//table name
        int CountLength = 0;
        ArrayList<String> theList=new ArrayList<>();//For array
        ArrayList<String> ListVehiclenumber=new ArrayList<>();


        ListView listView=(ListView) findViewById(R.id.list) ;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor data =sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (data.getCount()==0)
        {
            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, " found", Toast.LENGTH_LONG);
            //While loop for data read END of data
            while  (data.moveToNext()){

                //data add in array from database
                theList.add(data.getString(1));
                ListVehiclenumber.add(data.getString(10));
                  CountLength=CountLength+1;

                  //send data CustomerListAdapter Class
                CustomListAdapter customListAdapter =new CustomListAdapter(this,theList,ListVehiclenumber,CountLength);
                listView.setAdapter(customListAdapter);
            }

        }
    }


/*
    //Another Method for ARRAY LIST (Show single Value ) from DataBase
    public void viewdata(){
        dBconnection = new DBconnection(this);
        final String TABLE_NAME="patient";//table name
        ArrayList<String> theList=new ArrayList<>();
        ListView listView=(ListView) findViewById(R.id.list) ;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor data =sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (data.getCount()==0)
        {
            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, " found", Toast.LENGTH_LONG);
            while  (data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }

        }
    }

 */




}
