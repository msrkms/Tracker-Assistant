package com.example.vehicles_booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminSearchPageActivity extends AppCompatActivity implements View.OnClickListener {

    DBconnection dBconnection;
    TextView StartDate,EndDate;
    ImageButton submit;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;


    //for recycel view
    RecyclerView recyclerView;
    ArrayList<RecyclerViewMainModel> RMainModels;
    CustomRecyclerAdapter customRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_page);
        //sp=findViewById(R.id.spinnerSearchType);
        submit=findViewById(R.id.SubmitButton);
        submit.setOnClickListener(this);
        StartDate=findViewById(R.id.startDate);
        EndDate=findViewById(R.id.endDate);

        RecycelView();
       // Dropdownlist();
        DataViewForList();
        Startdate();
        EndDate();


    }



    public void RecycelView(){

        //For Recycler View need 2 extra class(Here CustomRecyclerAdapter and recyclerMainModel)
        //Also needed 1 extra XML file (Here recycler_row_item)

        recyclerView=findViewById(R.id.recycler_view);

        //Store pic in Array
        Integer[] pic={R.drawable.user,R.drawable.vespa,R.drawable.setting,R.drawable.search,R.drawable.car,R.drawable.add};

        //this loop for all pic store in RecyclerView Main Model
        RMainModels=new ArrayList<>();
         for (int i=0; i<pic.length;i++){
             RecyclerViewMainModel mobel =new RecyclerViewMainModel(pic[i]);
             RMainModels.add(mobel);

                   }
         //For Design Horizontal Layout
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this,LinearLayoutManager.HORIZONTAL,false);
         recyclerView.setLayoutManager(linearLayoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());

         //Initialize CustomRecyclerAdapter
        customRecyclerAdapter=new CustomRecyclerAdapter(this,RMainModels);
        recyclerView.setAdapter(customRecyclerAdapter);
    }



   /* public void Dropdownlist(){
        String[] VehiclesList={"User","Vehicle"};
        spinner = findViewById(R.id.e);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,VehiclesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    */

    public void Startdate(){
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        AdminSearchPageActivity.this
                        ,dateSetListener1,year,month,day

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });

        dateSetListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {

                Month = Month+1;
                String date= Day+"/"+ Month +"/"+Year;
                StartDate.setText(date);
            }
        };


    }
    public void EndDate(){
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        AdminSearchPageActivity.this
                        ,dateSetListener2,year,month,day

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });

        dateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {

                Month = Month+1;
                String date= Day+"/"+ Month +"/"+Year;
                EndDate.setText(date);
            }
        };


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
