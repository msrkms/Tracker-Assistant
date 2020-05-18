package com.example.vehicles_booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CarBookingEnableActivity extends AppCompatActivity  implements View.OnClickListener {

    DBconnection dBconnection;
    Button choose_button,textdata;
    ImageButton SubmitionButton;
    EditText EditMileage;
    ArrayAdapter<String>  adapter;
    private AlertDialog.Builder alertDialogBuilder;
    TextView vehiclesNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking_enable);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Vehicle Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SubmitionButton=findViewById(R.id.SubmitButton);
         textdata=findViewById(R.id.ChooseButton);

        SubmitionButton.setOnClickListener(this);

        viewdataForList();
    }

    public void onClick(View v){

        Intent intent;

        switch(v.getId()) {

            case R.id.SubmitButton: {
                popup();
                updatedata();
                intent = new Intent(this, VehiclesBookingActivity.class);
                startActivity(intent);
                break;
            }

        }

        }



    public void viewdataForList(){
        dBconnection = new DBconnection(this);
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();

        final String TABLE_NAME="patient";//table name

        //Add data to listview
        ListView listView=new ListView(this) ;
        //Add data to Array list
        List<String> data=new ArrayList<>();
       //Database Query for data all data selection
        Cursor databaseData =sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (databaseData.getCount()==0)
        {
            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, " found", Toast.LENGTH_LONG);
            while  (databaseData.moveToNext()){
                data.add(databaseData.getString(1));//Data read for 1st clm
                //create Array Adapter
                 adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
            }

        }

               // Add list view to alert Box
                AlertDialog.Builder builder = new AlertDialog.Builder(CarBookingEnableActivity.this);
                builder.setCancelable(true);
                builder.setView(listView);
                final AlertDialog dialog =builder.create();
                // Button action for view
              //  final Button textdata=(Button) findViewById(R.id.ChooseButton);
                textdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                    }
                });
                // For add data from alert box
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        textdata.setText(adapter.getItem(i));
                        dialog.dismiss();
                    }
                });


                        }

    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        String Disable="143";
        EditMileage=findViewById(R.id.editMileage);
        choose_button=findViewById(R.id.ChooseButton);

        if (EditMileage.equals("")||choose_button.equals("")){


            Toast.makeText(this,"Must input Favourite Number and Favourite Person",Toast.LENGTH_SHORT).show();

        }
        else {
            //update qury
            sqLiteDB.execSQL("UPDATE  patient SET pheight='"+Disable+"' WHERE pphone_number='"+phonenumber+"'");

        }
    }





    public void popup() {
        alertDialogBuilder = new AlertDialog.Builder(CarBookingEnableActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("Info");

        //for setting the message
        String status= textdata.getText().toString();
        alertDialogBuilder.setMessage("You Select "+status+", Vehicle .");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              //For close popup
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}

