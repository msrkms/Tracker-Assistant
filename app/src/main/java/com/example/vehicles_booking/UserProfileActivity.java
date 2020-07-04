package com.example.vehicles_booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vehicles_booking.BackEnd.AllUrls;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    ImageView userPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImage;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    ImageView editBtn;
    String dateOfBirth;

    private TextView textViewName, textViewPhoneNumber, textViewAddress, textViewDOB, textViewRank, textViewDesignation, textViewBloodGroup, textViewDesignationTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("DataFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String token = sharedPreferences.getString("Token", "NotFound");

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        getImage();

        textViewPhoneNumber = findViewById(R.id.tvPhone);
        textViewName = findViewById(R.id.UserName);
        textViewAddress = findViewById(R.id.tdAddress);
        textViewDesignation = findViewById(R.id.tvDesignation);
        textViewDesignationTitle = findViewById(R.id.tvDesignationTitle);
        textViewDOB = findViewById(R.id.tvDOB);
        textViewRank = findViewById(R.id.tvRank);
        textViewBloodGroup = findViewById(R.id.tvBloodGroup);

        editBtn = findViewById(R.id.EditBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
                startActivity(intent);

            }
        });

        new getProfileValue().execute();


    }


    //this method for Read image from Firebase
    private void getImage() {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://vehiclesbooking-163b2.appspot.com/image/").child("091a98fa-1980-482b-abc1-d8dcee0fd1ed.JPG");

        try {
            final File file = File.createTempFile("image", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    userPhoto.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserProfileActivity.this, "Image Falied to load", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class getProfileValue extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            getVolley();
            return null;
        }

        private void getVolley() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, AllUrls.GetProfile, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    parseData(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String tok = getSharedPreferences("DataFile", Context.MODE_PRIVATE).getString("Token", null);
                    System.out.println(tok);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + tok);

                    return headers;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(UserProfileActivity.this);
            requestQueue.add(stringRequest);
        }

        public void parseData(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String PhoneNumber = jsonObject.getString("phoneNumber");
                String Name = jsonObject.getString("name");
                String Address = jsonObject.getString("address");
                String DOB = jsonObject.getString("dob");
                String Rank = jsonObject.getString("rank");
                String Designation = jsonObject.getString("designation");
                String BloodGroup = jsonObject.getString("bloodGroup");
                String ProfilePictureURL = jsonObject.getString("profilePictureURL");

                SharedPreferences sharedPreferences = getSharedPreferences("DataFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phoneNumber", PhoneNumber);
                editor.putString("name", Name);
                editor.putString("address", Address);
                editor.putString("dob", DOB);
                editor.putString("rank", Rank);
                editor.putString("designation", Designation);
                editor.putString("bloodGroup", BloodGroup);
                editor.putString("profilePictureURL", ProfilePictureURL);
                editor.commit();
                editor.apply();
                showData();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private void showData() {
        SharedPreferences sharedPreferences = getSharedPreferences("DataFile", Context.MODE_PRIVATE);
        String DOB=sharedPreferences.getString("dob","NotFound");

        

        if (sharedPreferences.contains("phoneNumber")) {
            textViewName.setText(sharedPreferences.getString("name", "NotFound"));
            textViewDesignationTitle.setText(sharedPreferences.getString("designation","NotFound"));
            textViewPhoneNumber.setText(sharedPreferences.getString("phoneNumber", "NotFound"));
            textViewAddress.setText(sharedPreferences.getString("address","NotFound"));
            textViewRank.setText(sharedPreferences.getString("rank", "NotFound"));
            textViewDesignation.setText(sharedPreferences.getString("designation","NotFound"));
            textViewBloodGroup.setText(sharedPreferences.getString("bloodGroup", "NotFound"));
            textViewDOB.setText(DOB);

        }


    }
}

