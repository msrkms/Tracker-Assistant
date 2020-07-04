package com.example.vehicles_booking;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class EditUserProfileActivity extends AppCompatActivity {

    ImageView UserPhoto;
    static int PReqCode=1;
    static int REQUESCODE=1;
    Uri pickedImage;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ViewGroup FullView,PasswordLayout;
    private  Button Upload,dateOfBrith;
    private TextView pass,userType;

    DatePickerDialog.OnDateSetListener dateSetListener2;
    private AlertDialog.Builder alertDialogBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        mAuth= FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        UserPhoto=findViewById(R.id.EditUserPhoto);
        dateOfBrith=findViewById(R.id.buttonEditDateOfBrith);
        userType=findViewById(R.id.UserType);
        String Type=DataHolder.UserType;
        userType.setText(Type);

        getImage();
        dateOfBirth();


        //this button for choose image from gallery
        UserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();

                }
                else{
                    openGallery();
                }
            }
        });

            //this button for upload data
        Upload=findViewById(R.id.buttonUpload);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for upload data
                //uploadData();

                //for upload image
                uploadImage();


            }
        });

        PasswordLayout=findViewById(R.id.passwordLayout);
        FullView=findViewById(R.id.Fullview);
        pass=findViewById(R.id.passwordButton);
        pass.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(FullView);
                visible=!visible;
                PasswordLayout.setVisibility(visible ? View.VISIBLE: View.GONE);



            }
        });




    }



    private void openGallery() {
        //open gallery intent and wait for user to pick an image
        Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);

    }



    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(EditUserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(EditUserProfileActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(EditUserProfileActivity.this,"Please Accept for required permission",Toast.LENGTH_SHORT).show();

            }
            else {

                ActivityCompat.requestPermissions(EditUserProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);

            }
        }
        else {
            openGallery();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==REQUESCODE && data !=null){
            //The user  has sux=ccessfully picked an image
            //we need to save its reference to a uri Variable
            pickedImage=data.getData();
            UserPhoto.setImageURI(pickedImage);

          /*  try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImage);
                userPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }
    }

    private void uploadData(){

    }
    private void uploadImage() {

        if(pickedImage !=null){
            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            StorageReference reference =storageReference.child("image/"+ UUID.randomUUID().toString());
            reference.putFile(pickedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditUserProfileActivity.this,"Image Upload",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progres =(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progres+"%");
                }
            });

        }
    }

    //this method for Read image from Firebase
    private void getImage(){

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReferenceFromUrl("gs://vehiclesbooking-163b2.appspot.com/image/").child("091a98fa-1980-482b-abc1-d8dcee0fd1ed.JPG");

        try{
            final  File file= File.createTempFile("image","jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    UserPhoto.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditUserProfileActivity.this,"Image Falied to load",Toast.LENGTH_SHORT).show();

                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public void dateOfBirth(){

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        dateOfBrith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditUserProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener2,year,month,day);

                //      datePickerDialog.getWindow();
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        dateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {

                Month = Month+1;
                String date= Day+"/"+ Month +"/"+Year;
                dateOfBrith.setText(date);
            }
        };
    }



}

