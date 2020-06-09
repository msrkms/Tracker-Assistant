package com.example.vehicles_booking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

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


         getImage();


        editBtn=findViewById(R.id.EditBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(UserProfileActivity.this,EditUserProfileActivity.class);
                  startActivity(intent);

            }
        });

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

