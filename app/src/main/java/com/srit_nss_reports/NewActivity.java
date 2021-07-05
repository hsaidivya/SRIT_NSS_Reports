package com.srit_nss_reports;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class NewActivity extends AppCompatActivity {

    private Button btnSelect, btnUpload;
    FirebaseStorage storage;
    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    EditText textTitle, textDes, textDate;
    TextView display, create;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        textTitle = findViewById(R.id.title);
        textDes = findViewById(R.id.des);
        textDate = findViewById(R.id.date);

        display = findViewById(R.id.tvDisplay);


        //


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(
                Color.parseColor("#0F9D58"));
        actionBar.setBackgroundDrawable(colorDrawable);

        // initialise views
        btnSelect = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);

        // get the Firebase storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textTitle.getText().toString().isEmpty()){
                    Toast.makeText(NewActivity.this, "please enter event details", Toast.LENGTH_SHORT).show();
                }
                else {
                    SelectImage();
                }
            }
        });

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });
    }

    // Select Image method
    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
                if (filePath != null) {

                    // Code for showing progressDialog while uploading
                    ProgressDialog progressDialog
                            = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    // Defining the child of storageReference
                    StorageReference ref
                            = storageReference
                            .child(
                                    "images/" + textTitle.getText().toString() + "/" + "eventimage"+String.valueOf(count));
                        count+=1;
                    // adding listeners on upload
                    // or failure of image
                    ref.putFile(filePath)
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {


                                        @Override
                                        public void onSuccess(
                                                UploadTask.TaskSnapshot taskSnapshot) {

                                            // Image uploaded successfully
                                            // Dismiss dialog

                                            progressDialog.dismiss();
                                            Toast
                                                    .makeText(NewActivity.this,
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(NewActivity.this,
                                                    "Failed " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot) {
                                            double progress
                                                    = (100.0
                                                    * taskSnapshot.getBytesTransferred()
                                                    / taskSnapshot.getTotalByteCount());
                                            progressDialog.setMessage(
                                                    "Uploaded "
                                                            + (int) progress + "%");
                                        }
                                    });
                }

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method



    private void InsertData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events");



        if (textTitle.getText().toString().isEmpty() || textDes.getText().toString().isEmpty() || textDate.getText().toString().isEmpty()) {
            Toast.makeText(NewActivity.this, "Empty Feilds", Toast.LENGTH_SHORT).show();
        } else {
            String title1 = textTitle.getText().toString();
            String date1 = textDate.getText().toString();
            String des1 = textDes.getText().toString();
            display.setText(title1 + "\n" + date1 + "\n\n" + des1);
            String EventName = textTitle.getText().toString();
            String Date = textDate.getText().toString();
            String Description = textDes.getText().toString();

            String[] dataparts = Date.split("/");
            String timestamp = dataparts[2] + dataparts[1] + dataparts[0];
            int ts = Integer.parseInt(timestamp);

            Data data = new Data(EventName, Date, Description, ts);

            databaseReference.child(EventName).setValue(data);
            Toast.makeText(NewActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();


        }
    }
}
























                //info---------------












