package com.example.vehicles_booking;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        viewdata();

    }

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
    public void popup() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VehiclesListActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");
      int r=3;
        //for setting the message
        alertDialogBuilder.setMessage("Hi "+r+",আপনার রেজিস্ট্রেশন সম্পূর্ণ হয়েছে,আপনাকে যাচাই করার জন্য একটি OTP পাঠানো হচ্ছে । ধন্যবাদ  ");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit from popup
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}
