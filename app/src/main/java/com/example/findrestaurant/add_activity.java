package com.example.findrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ComponentCaller;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class add_activity extends AppCompatActivity {

    EditText item, price;
    private  Uri imageUri;
    private static final int PICK_IMAGE = 1;
    ImageView food;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button img, add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        item = findViewById(R.id.item);
        price = findViewById(R.id.price);
        food = findViewById(R.id.food);
        img = findViewById(R.id.img);
        add_btn = findViewById(R.id.add_btn);



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(item.getText().toString().isEmpty()||price.getText().toString().isEmpty()){
                    Toast.makeText(add_activity.this, "Empty", Toast.LENGTH_SHORT).show();
                }else {
                    upload(imageUri);

//                String Item = item.getText().toString();
//                String Price = price.getText().toString();
//
//                Menu menu=new Menu(Item,Price);
//                database.getReference().child("restaurant").child(auth.getCurrentUser().getUid()).child("food").push().setValue(menu);
//
                    Intent inext = new Intent(add_activity.this, MainActivity.class);
                    startActivity(inext);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
             imageUri = data.getData();

            try {
                // Convert the URI to a Bitmap and display in the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                food.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upload(Uri imageUri) {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("images"+System.currentTimeMillis()+".jpg");

        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                 String imageUrl=uri.toString();
                 savedItemtoFirebase(imageUrl);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Image Upload failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void savedItemtoFirebase(String imageUrl) {
        String Item = item.getText().toString();
        String Price = price.getText().toString();

        Menu menu=new Menu(Item,Price,imageUrl);
        database.getReference().child("restaurant").child(auth.getCurrentUser().getUid()).child("food").push().setValue(menu);

        Intent inext = new Intent(add_activity.this, MainActivity.class);
        startActivity(inext);
    }

}