package com.example.vehicles_booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity {

    ImageView userPhoto;
    static int PReqCode=1;
    static int REQUESCODE=1;
    Uri pickedImage;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    ImageView editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

         mAuth= FirebaseAuth.getInstance();
         storage=FirebaseStorage.getInstance();
         storageReference=storage.getReference();
         userPhoto=findViewById(R.id.UserPhoto);

         getImage();


        userPhoto.setOnClickListener(new View.OnClickListener() {
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

        editBtn=findViewById(R.id.EditBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadImage();
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
        if (ContextCompat.checkSelfPermission(UserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfileActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(UserProfileActivity.this,"Please Accept for required permission",Toast.LENGTH_SHORT).show();

            }
            else {

                ActivityCompat.requestPermissions(UserProfileActivity.this,
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
            userPhoto.setImageURI(pickedImage);

          /*  try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImage);
                userPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }
    }

    private void uploadImage()
    {

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
                            Toast.makeText(UserProfileActivity.this,"Image Upload",Toast.LENGTH_SHORT).show();
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
             userPhoto.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserProfileActivity.this,"Image Falied to load",Toast.LENGTH_SHORT).show();

                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    }

