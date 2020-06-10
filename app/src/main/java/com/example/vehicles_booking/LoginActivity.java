package com.example.vehicles_booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vehicles_booking.BackEnd.AllUrls;
import com.example.vehicles_booking.BackEnd.Decode;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    ProgressDialog progressDialog;
    private EditText umobile,upassword;
    ImageButton SignIn;
    TextView forgot,createNewUser;
    FusedLocationProviderClient  fusedLocationProviderClient;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        umobile=findViewById(R.id.inputMobile2);
        upassword=findViewById(R.id.inputPassword2);
        SignIn=findViewById(R.id.buttonSignIn);

        forgot=findViewById(R.id.textForgetpassword);
        createNewUser=findViewById(R.id.textNewAccount);
        forgot.setOnClickListener(this);
        createNewUser.setOnClickListener(this);
        SignIn.setOnClickListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }






    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.textForgetpassword: {

                intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textNewAccount: {

                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonSignIn: {
                getCredentials();
                break;
            }


        }
    }

    private void getCredentials(){
        String email=umobile.getText().toString();
        String pass=upassword.getText().toString();

        if(!email.isEmpty() && !pass.isEmpty()){
            Login(email,pass);
        }
        else{
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Wanring")
                    .setMessage("Field can't be empty")
                    .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressDialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void Login(final String email, String password){
        showDialog();

        JSONObject login = new JSONObject();

        try{
            login.put("phoneNumber",email);
            login.put("password",password);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AllUrls.UserLogin, login, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String token = jsonObject.getString("token");
                    SharedPreferences sharedPreferences = getSharedPreferences("DataFile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Token",token);
                    editor.commit();

                    JSONObject tokenDecodeObj=new JSONObject(Decode.decoded(token));
                    role=tokenDecodeObj.getString("role");
                    editor.putString("Role",role);
                    editor.commit();
                    //String decode= Decode.decoded(token);
                    System.out.println(role);
                    if(role.equals("Customer")){
                        DataHolder.UserType="Normal User";
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,UserProfileActivity.class);
                        intent.putExtra("token",token);
                       startActivity(intent);
                       LoginActivity.this.finish();
                    }
                    else if(role.equals("Admin")){
                        DataHolder.UserType="ADMIN";
                        progressDialog.dismiss();
                       // startActivity(new Intent(LoginActivity.this,AdminDashboardActivity.class));
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Wanring")
                                .setMessage("Admin")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }else{
                        progressDialog.dismiss();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Warning")
                                .setMessage("Super Admin feature not available here")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }

                    /*
                     */

                }catch (JSONException e){
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String message="";
                try {
                    String badreq = new String(error.networkResponse.data, "utf-8");
                    System.out.println(badreq);
                    JSONObject data = new JSONObject(badreq);
                     message = data.getString("message");
                } catch (JSONException e) {
                } catch (UnsupportedEncodingException errorr) {
                }

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Wanring")
                        .setMessage(message)
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        RequestQueue requestQueue =  Volley.newRequestQueue(LoginActivity.this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    private  void showDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Verifying User...");
        progressDialog.show();
    }




}

