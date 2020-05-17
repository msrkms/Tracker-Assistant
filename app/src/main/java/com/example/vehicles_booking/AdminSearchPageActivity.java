package com.example.vehicles_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminSearchPageActivity extends AppCompatActivity implements View.OnClickListener {

    DBconnection dBconnection;
    Spinner spinner,sp;
    ImageButton submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_page);
        //sp=findViewById(R.id.spinnerSearchType);
        submit=findViewById(R.id.SubmitButton);

        submit.setOnClickListener(this);
        Dropdownlist();
        DataViewForList();

    }

    public void Dropdownlist(){
        String[] VehiclesList={"User","Vehicle"};
        spinner = findViewById(R.id.spinnerSearchType);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,VehiclesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void onClick(View v){

        Intent intent;

        switch(v.getId()){

            case R.id.SubmitButton:{

                intent=new Intent(this,VehiclesBookingActivity.class);
                startActivity(intent);




                break;


            }

        }

    }








    public void DataViewForList(){
        dBconnection = new DBconnection(this);
        final String TABLE_NAME="patient";//table name
        int CountLength = 0;
        ArrayList<String> theList=new ArrayList<>();//For array
        ArrayList<String> ListVehiclenumber=new ArrayList<>();


        ListView listView=(ListView) findViewById(R.id.slist) ;
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
                ListVehiclenumber.add(data.getString(2));
                CountLength=CountLength+1;

                //send data CustomerListAdapter Class
                CustomListAdapter customListAdapter =new CustomListAdapter(this,theList,ListVehiclenumber,CountLength);
                listView.setAdapter(customListAdapter);
            }

        }
    }


}
