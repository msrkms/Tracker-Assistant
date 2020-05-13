package com.example.vehicles_booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBconnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="v.db";
    private static final int VERSION_NUMBER=6;

    //clm name for patient database
    private static final String TABLE_NAME="patient";//table name
    private static final String PNAME="pname";
    private static final String PID="_pid";
    private static final String PPHONE_NUMBER="pphone_number";
    private static final String PAGE="page";//patient_age
    private static final String PGENDER="pgender";
    private static final String PADDRESS="paddress";
    private static final String PBLOOD_GROUP="pblood_group";
    private static final String PPASSWORD="password";
    private static final String PBP="pbp";
    private static final String PHEIGHT="pheight";
    private static final String PWEIGHT="pweight";
    private static final String PBMR="pbmr";
    private static final String PBMI="pbmi";
    private static final  String PEMARGENCY_NUMBER_1="EmargencyNumber_1";
    private static final  String PEMARGENCY_NUMBER_2="EmargencyNumber_2";




    //another table for vehicles
    private static final String VEHICLES_INFO="vehicles";
    private static final String VID="vid";
    private static final String VEHICLES_DETAILS="vehicles_details";
    private static final String VEHICLES_NUMBER="vehicles_number";
    private static final String BOOKING_START_TIME="booking_start_time";
    private static final String BOOKING_END_TIME="booking_end_time";
    private static final String VEHICLES_AVAILABILITY="vehicles_availability";
    private static final String VUID="uid";



    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ "("+PID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+PNAME+"  VARCHAR(100),"+PPHONE_NUMBER+" VARCHAR(11),"+PADDRESS+" VARCHAR(500),"+PAGE+" VARCHAR(10),"+PBLOOD_GROUP+ " VARCHAR(5),"+PGENDER+" VARCHAR(10),"+PWEIGHT+" VARCHAR(5),"+PHEIGHT+" VARCHAR(5),"+PPASSWORD+" VARCHAR(50), "+PBMI+" VARCHAR(5),"+PBMR+" VARCHAR(5),"+PBP+" VARCHAR(5) ,"+PEMARGENCY_NUMBER_1+" VARCHAR(11),"+PEMARGENCY_NUMBER_2+" VARCHAR(11))" ;
    private static final String VEHICLES_CREATE_TABLE = "CREATE TABLE "+VEHICLES_INFO+ "("+VID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+VEHICLES_DETAILS+"  VARCHAR(500),"+VEHICLES_NUMBER+" VARCHAR(500),"+BOOKING_START_TIME+" VARCHAR(100),"+BOOKING_END_TIME+" VARCHAR(100),"+VEHICLES_AVAILABILITY+" VARCHAR(10),"+VUID+" VARCHAR(10) )";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static  final String SELECT_ALL="SELECT * FROM "+TABLE_NAME;
    private static final String PLM_DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static  final String PLM_SELECT_ALL="SELECT * FROM "+TABLE_NAME;
    private Context context;

    public DBconnection( Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {

            sqLiteDatabase.execSQL(CREATE_TABLE);
            sqLiteDatabase.execSQL(VEHICLES_CREATE_TABLE);
            //onCreate(sqLiteDatabase);
            Toast.makeText(context,"OnCREATE Is called",Toast.LENGTH_LONG).show();

        }catch ( Exception e){

            Toast.makeText(context,"Exception:"+e,Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {

            sqLiteDatabase.execSQL(DROP_TABLE);
            sqLiteDatabase.execSQL(PLM_DROP_TABLE);

            onCreate(sqLiteDatabase);
            Toast.makeText(context,"OnCREATE Is called",Toast.LENGTH_LONG).show();

        }catch ( Exception e){

            Toast.makeText(context,"Exception:"+e,Toast.LENGTH_LONG).show();
        }


    }






    public long inserdata( UserRegistrationDetails userRegistrationDetails){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        contentValues.put(PNAME,userRegistrationDetails.getPname());
        contentValues.put(PPHONE_NUMBER,userRegistrationDetails.getpMobileNumber());
        contentValues.put(PAGE,userRegistrationDetails.getpAge());
        contentValues.put(PGENDER,userRegistrationDetails.getPgender());
        contentValues.put(PBLOOD_GROUP,userRegistrationDetails.getpBloodGroup());
        contentValues.put(PADDRESS,userRegistrationDetails.getpAddress());
        contentValues.put(PPASSWORD,userRegistrationDetails.getpPassword());
        contentValues.put(PHEIGHT,userRegistrationDetails.getpHight());
        contentValues.put(PWEIGHT,userRegistrationDetails.getpWeight());
        contentValues.put(PBMI,userRegistrationDetails.getpBmi());
        contentValues.put(PBMR,userRegistrationDetails.getpBmr());
        contentValues.put(PBP,userRegistrationDetails.getpBP());
        contentValues.put(PEMARGENCY_NUMBER_1,userRegistrationDetails.getpEmargencyNumber1());
        contentValues.put(PEMARGENCY_NUMBER_2,userRegistrationDetails.getpEmargencyNumber2());

        long rowid = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return rowid;
    }

    public  Boolean findPassword ( String uname, String upassword){

        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();

        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        Boolean result=false;

        if (cursor.getCount()==0)
        {

            Toast.makeText(context,"No data is found",Toast.LENGTH_LONG);
        }
        else {

            while (cursor.moveToNext()){


                String username= cursor.getString(2) ;
                String password= cursor.getString(9) ;


                if(username.equals(uname) && password.equals(upassword)){

                    result =true;
                    break;
                }
            }

        }
        return result;
    }


    /*

    public UserRegistrationDetails displayAllData(String uname,String pass){
        UserRegistrationDetails userRegistrationDetails=null;
       try{
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        //Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME+" Where PPHONE_NUMBER =? and PPASSWORD =? ",new String[]{uname,pass});

           String viewdata="SELECT * FROM "+ TABLE_NAME+" Where _id=1 ";

           Cursor cursor =sqLiteDatabase.rawQuery(viewdata,null);

           if (cursor.moveToNext()) {

            userRegistrationDetails = new UserRegistrationDetails();

            userRegistrationDetails.setPname(cursor.getString(1));
            userRegistrationDetails.setpMobileNumber(cursor.getString(2));
            userRegistrationDetails.setpAddress(cursor.getString(3));
            userRegistrationDetails.setpAge(cursor.getString(4));
            userRegistrationDetails.setpBloodGroup(cursor.getString(5));
            userRegistrationDetails.setPgender(cursor.getString(6));
            userRegistrationDetails.setpWeight(cursor.getString(7));
            userRegistrationDetails.setpHight(cursor.getString(8));
            userRegistrationDetails.setpPassword(cursor.getString(9));
            userRegistrationDetails.setpBmi(cursor.getString(10));
            userRegistrationDetails.setpBmr(cursor.getString(11));
            userRegistrationDetails.setpBP(cursor.getString(12));

        }}
        catch(Exception e){

           userRegistrationDetails=null;
        }




        return userRegistrationDetails;
    }




        */

    public  boolean updateData(String bp,String weight,String hight,String bmi, String bmr,String phoneNumber){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        contentValues.put(PPHONE_NUMBER,phoneNumber);
        contentValues.put(PHEIGHT,hight);
        contentValues.put(PWEIGHT,weight);
        contentValues.put(PBMI,bmi);
        contentValues.put(PBMR,bmr);
        contentValues.put(PBP,bp);
        sqLiteDatabase.update(TABLE_NAME,contentValues,PPHONE_NUMBER+"=?",new String[]{phoneNumber});
        return true;

    }


    public  boolean updateDataForProfile(String name,String phone,String EmargencyNumber_1,String EmargencyNumber_2, String address,String phoneNumber){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        contentValues.put(PNAME,name);
        contentValues.put(PPHONE_NUMBER,phone);
        contentValues.put(PEMARGENCY_NUMBER_1,EmargencyNumber_1);
        contentValues.put(PEMARGENCY_NUMBER_2,EmargencyNumber_2);
        contentValues.put(PADDRESS,address);

        sqLiteDatabase.update(TABLE_NAME,contentValues,PPHONE_NUMBER+"=?",new String[]{phoneNumber});
        return true;

    }

}

