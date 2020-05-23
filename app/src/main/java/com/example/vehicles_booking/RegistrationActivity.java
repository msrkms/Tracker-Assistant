package com.example.vehicles_booking;

import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText userName, userPhoneNumber, userAge, userAddress, userBloodGroup, userPassword;
    private Button registration;
    UserRegistrationDetails userRegistrationDetails;
    int age;
    long ageFromdate;


    DBconnection dBconnection;
    private AlertDialog.Builder alertDialogBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName = findViewById(R.id.inputName2);
        userPhoneNumber = findViewById(R.id.inputMobile2);
        userAge = findViewById(R.id.inputAge2);
        userBloodGroup = findViewById(R.id.inputBloodGroup2);
        userAddress = findViewById(R.id.inputAddress2);
        userPassword = findViewById(R.id.inputPassword2);
        registration = findViewById(R.id.buttonSignUp);

        userRegistrationDetails = new UserRegistrationDetails();
        dBconnection = new DBconnection(this);

        registration.setOnClickListener(this);


    }


    public void onClick(View view) {

        Intent intent;

        localDataSave();


        long rowid = dBconnection.inserdata(userRegistrationDetails);

        if (rowid > 0) {
            // Toast.makeText(getApplicationContext(),"Row"+rowid+" is insert",Toast.LENGTH_SHORT).show();
            popup();//popup window

           // intent = new Intent(this, RegistrationActivity.class);
         //   startActivity(intent);


        } else {
            Toast.makeText(getApplicationContext(), "NO DATA INSERT", Toast.LENGTH_SHORT).show();

        }
    }




    private void localDataSave() {


        //converting all value to string
        String Name = userName.getText().toString();
        String MobileNumber = userPhoneNumber.getText().toString();
        String Age = userAge.getText().toString();
        String Address = userAddress.getText().toString();
        String BloodGroup = userBloodGroup.getText().toString();
        String Password = userPassword.getText().toString();
        String Weight = "0", Hight = "0", Bmr = "0", Bmi = "0", BP = "0", Emargency_Number1 = "0", Emargency_number2 = "0",Gender="0";

        //pass to data UserRagistrationDetails cls , Because Setter and getter is there
        userRegistrationDetails.setPname(Name);
        userRegistrationDetails.setpMobileNumber(MobileNumber);
        userRegistrationDetails.setpAge(Age);
        userRegistrationDetails.setPgender(Gender);
        userRegistrationDetails.setpAddress(Address);
        userRegistrationDetails.setpBloodGroup(BloodGroup);
        userRegistrationDetails.setpPassword(Password);
        userRegistrationDetails.setpWeight(Weight);
        userRegistrationDetails.setpHight(Hight);
        userRegistrationDetails.setpBmi(Bmi);
        userRegistrationDetails.setpBmr(Bmr);
        userRegistrationDetails.setpBP(BP);
        userRegistrationDetails.setpEmargencyNumber1(Emargency_Number1);
        userRegistrationDetails.setpEmargencyNumber2(Emargency_number2);
    }
    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
        String pName= userName.getText().toString();
        alertDialogBuilder.setMessage("Hi "+pName+",আপনার রেজিস্ট্রেশন সম্পূর্ণ হয়েছে,আপনাকে যাচাই করার জন্য একটি OTP পাঠানো হচ্ছে । ধন্যবাদ  ");

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


